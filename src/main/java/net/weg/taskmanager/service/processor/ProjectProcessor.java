package net.weg.taskmanager.service.processor;

import lombok.NoArgsConstructor;
import net.weg.taskmanager.model.entity.*;

import java.util.ArrayList;
@NoArgsConstructor
public class ProjectProcessor {

    private Project resolvingProject;
    private  String projectClassName = Project.class.getSimpleName();
    private  ArrayList<String> resolvingCascade;

    public static ProjectProcessor getInstance(){
        return new ProjectProcessor();
    }

    public Project resolveProject(Project project, ArrayList<String> _resolvingCascade){

        resolvingCascade = _resolvingCascade;
        resolvingCascade.add(projectClassName);

        resolvingProject = project;

        resolveProjectTasks();
        resolveProjectCreator();
        resolveProjectMembers();
        resolveProjectAdministrators();
        resolveProjectTeam();
        reolveProjectChat();

        resolvingCascade.remove(projectClassName);

        return resolvingProject;
    }

    public Project resolveProject(Project project){
        return resolveProject(project, new ArrayList<>());
    }

    private void resolveProjectTasks(){
        if(resolvingProject.getTasks()!=null){
            if(resolvingCascade.contains(Task.class.getSimpleName())){
                resolvingProject.setTasks(null);
                return;
            }
            resolvingProject.getTasks().stream()
                    .forEach(task -> TaskProcessor.getInstance().resolveTask(task, resolvingCascade));
        }
    }

    private void resolveProjectCreator(){
        if(resolvingProject.getCreator()!=null){
            if(resolvingCascade.contains(User.class.getSimpleName())){
                resolvingProject.setCreator(null);
                return;
            }
            UserProcessor.getInstance().resolveUser(resolvingProject.getCreator(), resolvingCascade);
        }
    }

    private void resolveProjectMembers(){
        if(resolvingProject.getMembers()!=null){
            if(resolvingCascade.contains(User.class.getSimpleName())){
                resolvingProject.setMembers(null);
                return;
            }
            resolvingProject.getMembers().stream()
                    .forEach(user -> UserProcessor.getInstance().resolveUser(user, resolvingCascade));
        }
    }

    private void resolveProjectAdministrators(){
        if(resolvingProject.getAdministrators()!=null){
            if(resolvingCascade.contains(User.class.getSimpleName())){
                resolvingProject.setAdministrators(null);
                return;
            }
            resolvingProject.getAdministrators().stream()
                    .forEach(user -> UserProcessor.getInstance().resolveUser(user, resolvingCascade));
        }
    }

    private void resolveProjectProperties(){

    }

    private void resolveProjectTeam(){
        if(resolvingProject.getTeam()!=null){
            if(resolvingCascade.contains(Team.class.getSimpleName())){
                resolvingProject.setTeam(null);
                return;
            }
            TeamProcessor.getInstance().resolveTeam(resolvingProject.getTeam(), resolvingCascade);
        }
    }

    private void reolveProjectChat(){
        if(resolvingProject.getChat()!=null){
            if(resolvingCascade.contains(TeamChat.class.getSimpleName())){
                resolvingProject.setChat(null);
                return;
            }
            ChatProcessor.getInstance().resolveChat(resolvingProject.getChat(), resolvingCascade);
        }
    }


}
