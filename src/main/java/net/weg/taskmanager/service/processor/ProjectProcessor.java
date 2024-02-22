package net.weg.taskmanager.service.processor;

import net.weg.taskmanager.model.*;

import java.util.ArrayList;

public class ProjectProcessor {

    private static Project resolvingProject;
    private static String projectClassName = Project.class.getSimpleName();
    private static ArrayList<String> resolvingCascade;

    public static Project resolveProject(Project project, String objClassName, ArrayList<String> _resolvingCascade){

        resolvingCascade = _resolvingCascade;
        resolvingCascade.add(objClassName);

        resolvingProject = project;

        resolveProjectTasks();
        resolveProjectCreator();
        resolveProjectMembers();
        resolveProjectAdministrators();
        resolveProjectTeam();
        reolveProjectChat();

        resolvingCascade.remove(objClassName);

        return resolvingProject;
    }

    public static Project resolveProject(Project project){
        return resolveProject(project, projectClassName, new ArrayList<>());
    }

    private static void resolveProjectTasks(){
        if(resolvingProject.getTasks()!=null){
            if(resolvingCascade.contains(Task.class.getSimpleName())){
                resolvingProject.setTasks(null);
                return;
            }
            resolvingProject.getTasks().stream()
                    .forEach(task -> TaskProcessor.resolveTask(task, projectClassName, resolvingCascade));
        }
    }

    private static void resolveProjectCreator(){
        if(resolvingProject.getCreator()!=null){
            if(resolvingCascade.contains(User.class.getSimpleName())){
                resolvingProject.setCreator(null);
                return;
            }
            UserProcessor.resolveUser(resolvingProject.getCreator(), projectClassName, resolvingCascade);
        }
    }

    private static void resolveProjectMembers(){
        if(resolvingProject.getMembers()!=null){
            if(resolvingCascade.contains(User.class.getSimpleName())){
                resolvingProject.setMembers(null);
                return;
            }
            resolvingProject.getMembers().stream()
                    .forEach(user -> UserProcessor.resolveUser(user, projectClassName, resolvingCascade));
        }
    }

    private static void resolveProjectAdministrators(){
        if(resolvingProject.getAdministrators()!=null){
            if(resolvingCascade.contains(User.class.getSimpleName())){
                resolvingProject.setAdministrators(null);
                return;
            }
            resolvingProject.getAdministrators().stream()
                    .forEach(user -> UserProcessor.resolveUser(user, projectClassName, resolvingCascade));
        }
    }

    private static void resolveProjectProperties(){

    }

    private static void resolveProjectTeam(){
        if(resolvingProject.getTeam()!=null){
            if(resolvingCascade.contains(Team.class.getSimpleName())){
                resolvingProject.setTeam(null);
                return;
            }
            TeamProcessor.resolveTeam(resolvingProject.getTeam(), projectClassName, resolvingCascade);
        }
    }

    private static void reolveProjectChat(){
        if(resolvingProject.getChat()!=null){
            if(resolvingCascade.contains(TeamChat.class.getSimpleName())){
                resolvingProject.setChat(null);
                return;
            }
            ChatProcessor.resolveChat(resolvingProject.getChat(), projectClassName, resolvingCascade);
        }
    }


}
