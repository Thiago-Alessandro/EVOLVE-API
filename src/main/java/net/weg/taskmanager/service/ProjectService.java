package net.weg.taskmanager.service;

import lombok.AllArgsConstructor;
import net.weg.taskmanager.model.Project;
import net.weg.taskmanager.model.Status;
import net.weg.taskmanager.model.Task;
import net.weg.taskmanager.model.property.TaskProjectProperty;
import net.weg.taskmanager.repository.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

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
        for(Task task : project.getTasks()){
            task.setProject(null);
        }


        return project;}

    public Collection<Project> findAll() {
        Collection<Project> projects =  projectRepository.findAll();
        for (Project project : projects) {
            for (Task task : project.getTasks()) {
                task.setProject(null);
            }
        }
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
            System.out.println("Deu erro lá manin");
            throw new RuntimeException(e);
        }

        projectRepository.deleteById(id);}

    public Project create(Project project){

        //Adiciona o projeto ao BD para que seja criado o seu Id
        projectRepository.save(project);

        //Seta os status padrões do projeto
        project.setStandardStatus();
        propertiesSetProject(project);

        //Atualiza o projeto adicionando sua referencia nas suas propriedades
        return update(project);
    }
    public Project update(Project project){

//        for(Status status : project.getStatusList()){
//            if(status.getId()==0){
//                status.setId(null);
//            }
//        }

//        System.out.println(project);
        return projectRepository.save(project);
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
