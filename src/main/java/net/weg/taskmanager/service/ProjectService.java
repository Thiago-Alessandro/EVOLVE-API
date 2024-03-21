package net.weg.taskmanager.service;
import lombok.RequiredArgsConstructor;
import net.weg.taskmanager.model.entity.User;

import net.weg.taskmanager.model.dto.get.GetProjectDTO;
import net.weg.taskmanager.model.dto.get.GetTaskDTO;
import net.weg.taskmanager.model.dto.post.PostProjectDTO;
import net.weg.taskmanager.model.entity.Project;
import net.weg.taskmanager.model.entity.Status;
import net.weg.taskmanager.model.dto.put.PutProjectDTO;
import net.weg.taskmanager.model.property.Property;
import net.weg.taskmanager.model.record.PriorityRecord;
import net.weg.taskmanager.repository.*;
import net.weg.taskmanager.service.processor.ProjectProcessor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;

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
    private final ProjectProcessor projectProcessor = new ProjectProcessor();
    
    public GetProjectDTO updateStatusList(Long id, Status status){
        Project project = projectRepository.findById(id).get();
        if(project.getStatusList()!=null){
            for(Status statusFor : project.getStatusList()){
                if(Objects.equals(status.getId(), statusFor.getId())){
                    BeanUtils.copyProperties(status, statusFor);
                    return transformToGetProjectDTO(treatAndSave(project));
                }
            }
            project.getStatusList().add(status);
        } else {
            project.setStatusList(new ArrayList());
            project.getStatusList().add(status);
        }
        return transformToGetProjectDTO(treatAndSave(project));
    }


    public GetProjectDTO findById(Long id){
        Project project =  projectRepository.findById(id).get();

        ProjectProcessor.getInstance().resolveProject(project);

        return transformToGetProjectDTO(project);
    }

    public Collection<GetProjectDTO> findAll() {
        Collection<Project> projects =  projectRepository.findAll();
        Collection<GetProjectDTO> getProjectDTOS = new HashSet<>();

        projects.stream()
                        .forEach(project -> {
//                            project.setTasks();
                            ProjectProcessor.getInstance().resolveProject(project);
                            GetProjectDTO getProjectDTO = transformToGetProjectDTO(project);
                            getProjectDTOS.add(getProjectDTO);
                        });

        return getProjectDTOS;
    }

    public GetProjectDTO create(PostProjectDTO projectDTO){

        Project project = new Project();
        BeanUtils.copyProperties(projectDTO, project);

        updateProjectChat(project);

        //Adiciona o projeto ao BD para que seja criado o seu Id
        projectRepository.save(project);

        //Referencia o projeto nas suas propriedades
        propertiesSetProject(project);

        return transformToGetProjectDTO(treatAndSave(project));
    }

    public GetProjectDTO update(PutProjectDTO projectDTO){

        Project project = projectRepository.findById(projectDTO.getId()).get();
        modelMapper.map(projectDTO, project);

        updateProjectChat(project);

        return transformToGetProjectDTO(treatAndSave(project));
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

        Collection<GetProjectDTO> getProjectDTOS = new HashSet<>();
        projects.forEach(project -> {
                    ProjectProcessor.getInstance().resolveProject(project);
                    GetProjectDTO getProjectDTO = transformToGetProjectDTO(project);
                    getProjectDTOS.add(getProjectDTO);
                });
        return getProjectDTOS;
    }


    private Project treatAndSave(Project project){
        project.updateLastTimeEdited();
        Project savedProject = projectRepository.save(project);
        ProjectProcessor.getInstance().resolveProject(savedProject);
        return savedProject;
    }
    private GetProjectDTO transformToGetProjectDTO(Project project){
        GetProjectDTO getProjectDTO = new GetProjectDTO();
        Collection<GetTaskDTO> getTaskDTOS = new HashSet<>();

        BeanUtils.copyProperties(project, getProjectDTO);

        if(project.getTasks()!=null) {
            project.getTasks().forEach((task -> {
                GetTaskDTO getTaskDTO = new GetTaskDTO();
                PriorityRecord priorityRecord = new PriorityRecord(task.getPriority().name(), task.getPriority().backgroundColor);
                BeanUtils.copyProperties(task, getTaskDTO);
                getTaskDTO.setPriority(priorityRecord);
                getTaskDTOS.add(getTaskDTO);
            }));
        }

        getProjectDTO.setTasks(getTaskDTOS);
        return getProjectDTO;
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



}
