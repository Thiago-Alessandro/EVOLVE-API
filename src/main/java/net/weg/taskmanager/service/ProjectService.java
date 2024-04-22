package net.weg.taskmanager.service;
import lombok.RequiredArgsConstructor;
import net.weg.taskmanager.model.dto.converter.Converter;
import net.weg.taskmanager.model.dto.converter.get.GetProjectConverter;
import net.weg.taskmanager.model.entity.Team;
import net.weg.taskmanager.model.entity.User;

import net.weg.taskmanager.model.dto.get.GetProjectDTO;
import net.weg.taskmanager.model.dto.post.PostProjectDTO;
import net.weg.taskmanager.model.entity.Project;
import net.weg.taskmanager.model.entity.Status;
import net.weg.taskmanager.model.dto.put.PutProjectDTO;
import net.weg.taskmanager.model.property.Property;
import net.weg.taskmanager.repository.*;
import net.weg.taskmanager.service.processor.ProjectProcessor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
//@AllArgsConstructor
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final TaskRepository taskRepository;
    private final StatusRepository statusRepository;
    private final TeamRepository teamRepository;
    private final PropertyRepository propertyRepository;
    private final ModelMapper modelMapper;
    private final ChartService chartService;
    private final ProjectProcessor projectProcessor = new ProjectProcessor();
    private final Converter<GetProjectDTO, Project> converter = new GetProjectConverter();
    
    public GetProjectDTO updateStatusList(Long id, Status status){
        Project project = projectRepository.findById(id).get();
        if(project.getStatusList()!=null){
            for(Status statusFor : project.getStatusList()){
                if(Objects.equals(status.getId(), statusFor.getId())){
                    BeanUtils.copyProperties(status, statusFor);
                    return converter.convertOne(treatAndSave(project));
                }
            }
            project.getStatusList().add(status);
        } else {
            project.setStatusList(new ArrayList<>());
            project.getStatusList().add(status);
        }
        return converter.convertOne(treatAndSave(project));
    }


    public GetProjectDTO findById(Long id){
        Project project =  projectRepository.findById(id).get();

     //   projectProcessor.resolveProject(project);
        if(!project.getDashboards().isEmpty()){
            chartService.getChartsValues(project);
        }
        return new GetProjectDTO(project);
    }

    public Collection<GetProjectDTO> findAll() {
        Collection<Project> projects =  projectRepository.findAll();
        projects.forEach(projectProcessor::resolveProject);

        return converter.convertAll(projects);
    }

    public GetProjectDTO create(PostProjectDTO projectDTO){

        Project project = new Project();
        BeanUtils.copyProperties(projectDTO, project);

        updateProjectChat(project);

        //Adiciona o projeto ao BD para que seja criado o seu Id
        projectRepository.save(project);

        //Referencia o projeto nas suas propriedades
        propertiesSetProject(project);

        return converter.convertOne(treatAndSave(project));
    }

    public GetProjectDTO update(PutProjectDTO projectDTO){

        Project project = projectRepository.findById(projectDTO.getId()).get();
        modelMapper.map(projectDTO, project);

        updateProjectChat(project);

        return converter.convertOne(treatAndSave(project));
    }

    public void delete(Long id){
        Project project = projectRepository.findById(id).get();
        taskRepository.deleteAll(project.getTasks());
        statusRepository.deleteAll(project.getStatusList());

        try {
            if(teamRepository.findTeamByProjectsContaining(project)!=null){
                //seria bom ter o atributo equipe no proprio projeto para não ter que pegar na service
                teamRepository.findTeamByProjectsContaining(project).getProjects().remove(project);
            }
        } catch (Exception e) {
            throw new RuntimeException(e + "Deu erro lá na projectService manin");
        }
        projectRepository.deleteById(id);
    }

    private final UserRepository userRepository;

    public Collection<GetProjectDTO> getProjectsByUserId(Long id){
        User user = userRepository.findById(id).get();
        Collection<Project> projects = projectRepository.findProjectsByMembersContaining(user);

        projects.forEach(projectProcessor::resolveProject);
        return converter.convertAll(projects);
    }

    public Collection<GetProjectDTO> getProjectsByTeam(Long teamId, Long userId){

        Team team = teamRepository.findById(teamId).get();

        if(team.getParticipants().contains(userRepository.findById(userId).get())){
            Collection<Project> projects = projectRepository.findAllByTeam_Id(teamId);
            projects.forEach(projectProcessor::resolveProject);
            return converter.convertAll(projects);
        }

        throw new RuntimeException("Usuario não encontrado no time");
    }


    private Project treatAndSave(Project project){
        project.updateLastTimeEdited();
        Project savedProject = projectRepository.save(project);
        projectProcessor.resolveProject(savedProject);
        return savedProject;
    }

    private void propertiesSetProject(Project project){
        //Verifica se há alguma propriedade no projeto
        if(project.getProperties() != null && project.getProperties().size()>0){
            //Passa pela lista de propriedades do projeto
            for(Property propriedade : project.getProperties()) {
                //Adiciona a referencia do projeto na propriedade
                propriedade.setProject(project);
                //Salva a propriedade atualizada com a referencia do projeto
                propertyRepository.save(propriedade);
            }
        }
    }

    private void updateProjectChat(Project project){
        project.getChat().setUsers(project.getMembers());
    }

    public GetProjectDTO deleteUserFromProject(Long idProject, Collection<User> usersToRemove) {
        Project project = projectRepository.findById(idProject).get();

        Long creatorId = project.getCreator().getId();

        Set<Long> userIdsToRemove = usersToRemove.stream()
                .filter(user -> !user.getId().equals(creatorId))
                .map(User::getId)
                .collect(Collectors.toSet());

        Set<User> updatedMembers = project.getMembers().stream()
                .filter(user -> !userIdsToRemove.contains(user.getId()))
                .collect(Collectors.toSet());

        project.setMembers(updatedMembers);
        return converter.convertOne(treatAndSave(project));
    }


    public GetProjectDTO patchImage(Long id, MultipartFile image) {
        Project project = projectRepository.findById(id).get();
        project.setImage(image);
        return new GetProjectDTO(treatAndSave(project));
    }
}
