package net.weg.taskmanager.service;

import lombok.AllArgsConstructor;
import net.weg.taskmanager.model.Project;
import net.weg.taskmanager.model.Status;
import net.weg.taskmanager.model.Task;
import net.weg.taskmanager.model.dto.post.PostProjectDTO;
import net.weg.taskmanager.model.property.TaskProjectProperty;
import net.weg.taskmanager.repository.*;
import net.weg.taskmanager.service.processor.ProjectProcessor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

@Service
@AllArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final TaskRepository taskRepository;
    private final StatusRepository statusRepository;
    private final TeamRepository teamRepository;
    private final TaskProjectPropertyRepository taskProjectPropertyRepository;

    public Project updateStatusList(Integer id, Status status){
        Project project = projectRepository.getById(id);
        if(project!=null){
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
        }
        return update(project);
    }


    public Project findById(Integer id){
        Project project =  projectRepository.findById(id).get();

        ProjectProcessor.resolveProject(project);

        return project;
    }

    public Collection<Project> findAll() {
        Collection<Project> projects =  projectRepository.findAll();

        projects.stream()
                        .forEach(project -> ProjectProcessor.resolveProject(project));

        return projects;
    }

    public void delete(Integer id){
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

        //Adiciona o projeto ao BD para que seja criado o seu Id
        projectRepository.save(project);

        //Seta os status padrões do projeto
        project.setStandardStatus();
        //Referencia o projeto nas suas propriedades
        propertiesSetProject(project);
        //Prepara os status para serem criados
        setNewStatusIdNull(project);

        //Atualiza o projeto adicionando sua referencia nas suas propriedades
        projectRepository.save(project);

        //Retorna o objeto sem stackOverflow
        return ProjectProcessor.resolveProject(project);
    }

    public void setNewStatusIdNull(Project project){
        for(Status status : project.getStatusList()){
            if(status.getId()!=null && status.getId().equals(0)){
                status.setId(null);
            }
        }
    }

    public Project update(Project project){
        setNewStatusIdNull(project);

        return ProjectProcessor.resolveProject(projectRepository.save(project));
    }

    private void propertiesSetProject(Project project){
        //Verifica se há alguma propriedade no projeto
        if(project.getProperties() != null && project.getProperties().size()>0){
            //Passa pela lista de propriedades do projeto
            for(TaskProjectProperty propriedade : project.getProperties()) {
                //Adiciona a referencia do projeto na propriedade
                propriedade.setProject(project);
                //Salva a propriedade atualizada com a referencia do projeto
                taskProjectPropertyRepository.save(propriedade);
            }
        }
    }



}
