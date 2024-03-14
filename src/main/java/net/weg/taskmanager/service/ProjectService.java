package net.weg.taskmanager.service;
import lombok.RequiredArgsConstructor;
import net.weg.taskmanager.model.Task;
import net.weg.taskmanager.model.dto.get.GetProjectDTO;
import net.weg.taskmanager.model.dto.get.GetTaskDTO;
import net.weg.taskmanager.model.dto.post.PostProjectDTO;
import net.weg.taskmanager.model.Project;
import net.weg.taskmanager.model.Status;
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
    
    public Project updateStatusList(Long id, Status status){
        Project project = projectRepository.findById(id).get();
        if(project.getStatusList()!=null){
            for(Status statusFor : project.getStatusList()){
                if(Objects.equals(status.getId(), statusFor.getId())){
                    BeanUtils.copyProperties(status, statusFor);
                    return project;
                }
            }
            project.getStatusList().add(status);
        } else {
            project.setStatusList(new ArrayList());
            project.getStatusList().add(status);
        }
        return treatAndSave(project);
    }


    public Project findById(Long id){
        Project project =  projectRepository.findById(id).get();

        ProjectProcessor.getInstance().resolveProject(project);

        return project;
    }

    public Collection<Project> findAll() {
        Collection<Project> projects =  projectRepository.findAll();

        projects.stream()
                        .forEach(project -> {
                            project.setTasks();transformTasksToGetTasks(project);
                            ProjectProcessor.getInstance().resolveProject(project)
                        });

        return projects;
    }

    private GetProjectDTO transformToProjectDTO(Project project){
        GetProjectDTO getProjectDTO = new GetProjectDTO();
        Collection<GetTaskDTO> getTaskDTOS = new HashSet<>();
        for (Task taskFor : project.getTasks()) {
            GetTaskDTO getTaskDTO = new GetTaskDTO();
            PriorityRecord priorityRecord = new PriorityRecord(taskFor.getPriority().name(), taskFor.getPriority().backgroundColor);
            BeanUtils.copyProperties(taskFor, getTaskDTO);
            getTaskDTO.setPriority(priorityRecord);
            getTaskDTOS.add(getTaskDTO);
        }
        getProjectDTO.setTasks(getTaskDTOS);
        return getProjectDTO;
    }

    public void delete(Long id){
        Project project = findById(id);
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

        projectRepository.deleteById(id);}

    public Project create(PostProjectDTO projectDTO){

        Project project = new Project();
        BeanUtils.copyProperties(projectDTO, project);

        updateProjectChat(project);

        //Adiciona o projeto ao BD para que seja criado o seu Id
        projectRepository.save(project);

        //Referencia o projeto nas suas propriedades
        propertiesSetProject(project);

        return treatAndSave(project);
    }

    

    public Project update(PutProjectDTO projectDTO){

        Project project = projectRepository.findById(projectDTO.getId()).get();
        modelMapper.map(projectDTO, project);

        updateProjectChat(project);

        return treatAndSave(project);
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

    public Project treatAndSave(Project project){
        project.updateLastTimeEdited();
        Project savedProject = projectRepository.save(project);
        ProjectProcessor.getInstance().resolveProject(savedProject);
        return savedProject;
    }

}
