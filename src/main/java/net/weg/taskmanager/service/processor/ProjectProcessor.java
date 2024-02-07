package net.weg.taskmanager.service.processor;

import net.weg.taskmanager.model.*;

public class ProjectProcessor {

    private static String objClassName;
    private static Project resolvingProject;
    private static String projectClassName;

    public static void resolveProject(Project project, String objectClassName){

        resolvingProject = project;
        objClassName = objectClassName;
        projectClassName = resolvingProject.getClass().getSimpleName();

        resolveProjectTasks();
        resolveProjectCreator();
        resolveProjectMembers();
        resolveProjectAdministrators();
        resolveProjectTeam();
        reolveProjectChat();

    }

    private static void resolveProjectTasks(){
        if(resolvingProject.getTasks()!=null){
            if(objClassName.equals(Task.class.getSimpleName())){
                resolvingProject.setTasks(null);
                return;
            }
            resolvingProject.getTasks().stream()
                    .forEach(task -> TaskProcessor.resolveTask(task, projectClassName));
        }
    }

    private static void resolveProjectCreator(){
        if(resolvingProject.getCreator()!=null){
            if(objClassName.equals(User.class.getSimpleName())){
                resolvingProject.setCreator(null);
                return;
            }
            UserProcessor.resolveUser(resolvingProject.getCreator(), projectClassName);
        }
    }

    private static void resolveProjectMembers(){
        if(resolvingProject.getMembers()!=null){
            if(objClassName.equals(User.class.getSimpleName())){
                resolvingProject.setMembers(null);
                return;
            }
            resolvingProject.getMembers().stream()
                    .forEach(user -> UserProcessor.resolveUser(user, projectClassName));
        }
    }

    private static void resolveProjectAdministrators(){
        if(resolvingProject.getAdministrators()!=null){
            if(objClassName.equals(User.class.getSimpleName())){
                resolvingProject.setAdministrators(null);
                return;
            }
            resolvingProject.getAdministrators().stream()
                    .forEach(user -> UserProcessor.resolveUser(user, projectClassName));
        }
    }

    private static void resolveProjectProperties(){

    }

    private static void resolveProjectTeam(){
        if(resolvingProject.getTeam()!=null){
            if(objClassName.equals(Team.class.getSimpleName())){
                resolvingProject.setTeam(null);
                return;
            }
            TeamProcessor.resolveTeam(resolvingProject.getTeam(), projectClassName);
        }
    }

    private static void reolveProjectChat(){
        if(resolvingProject.getChat()!=null){
            if(objClassName.equals(TeamChat.class.getSimpleName())){
                resolvingProject.setChat(null);
                return;
            }
            ChatProcessor.resolveChat(resolvingProject.getChat(), projectClassName);
        }
    }


}
