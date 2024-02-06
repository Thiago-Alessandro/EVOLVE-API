package net.weg.taskmanager.service.processor;

import net.weg.taskmanager.model.Message;
import net.weg.taskmanager.model.Project;
import net.weg.taskmanager.model.Task;

public class ProjectProcessor {

    private static String objClassName;
    private static Project resolvingProject;

    public static void resolveProject(Project project, String objectClassName){

        resolvingProject = project;
        objClassName = objectClassName;

        resolveProjectTasks();

    }

    private static void resolveProjectTasks(){
        if(resolvingProject.getTasks()!=null){
            if(objClassName.equals(Task.class.getSimpleName())){
                resolvingProject.setTasks(null);
                return;
            }
            for(Task task : resolvingProject.getTasks()){
                TaskProcessor.resolveTask(task, resolvingProject.getClass().getSimpleName());
            }
        }
    }

}
