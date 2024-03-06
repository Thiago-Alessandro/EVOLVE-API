package net.weg.taskmanager.service;
import lombok.RequiredArgsConstructor;
import net.weg.taskmanager.model.dto.post.PostProjectDTO;
import net.weg.taskmanager.model.Project;
import net.weg.taskmanager.model.Status;
import net.weg.taskmanager.model.property.Property;
import net.weg.taskmanager.repository.*;
import net.weg.taskmanager.service.processor.ProjectProcessor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
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
    
    public Project updateStatusList(Long id, Status status){
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


    public Project findById(Long id){
        Project project =  projectRepository.findById(id).get();

        ProjectProcessor.getInstance().resolveProject(project);

        return project;
    }

    public Collection<Project> findAll() {
        Collection<Project> projects =  projectRepository.findAll();

        projects.stream()
                        .forEach(project -> ProjectProcessor.getInstance().resolveProject(project));

        return projects;
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

        //Prepara os status para serem criados
        //(futuramente quando o front não mandar o id como 0 por padrao podera ser retirado)
        setNewStatusIdNull(project);

        //Atualiza o projeto adicionando sua referencia nas suas propriedades
        Project createdProject = projectRepository.save(project);
        ProjectProcessor.getInstance().resolveProject(createdProject);
//        System.out.println(createdProject);

        //Retorna o objeto sem stackOverflow
        return createdProject;
    }


    public void setNewStatusIdNull(Project project){
        for(Status status : project.getStatusList()){
            if(status.getId()!=null && status.getId().equals(0)){
                status.setId(null);
            }
        }
    }

    public Project update(Project project){
        updateProjectChat(project);
        setNewStatusIdNull(project);
        project.updateLastTimeEdited();

        return ProjectProcessor.getInstance().resolveProject(projectRepository.save(project));
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
