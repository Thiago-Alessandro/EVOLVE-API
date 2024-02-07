package net.weg.taskmanager.service.processor;

import net.weg.taskmanager.model.Project;
import net.weg.taskmanager.model.Task;
import net.weg.taskmanager.model.User;

import java.sql.PreparedStatement;

public class TaskProcessor {

    private static String objClassName;
    private static Task resolvingTask;

    public static void resolveTask(Task task, String objectClassName){

        resolvingTask = task;
        objClassName = objectClassName;

        resolveTaskProject();
        resolveTaskUser();

    }

    private static void resolveTaskProject(){
        if(resolvingTask.getProject()!=null){
            if(objClassName.equals(Project.class.getSimpleName())){
                resolvingTask.setProject(null);
                return;
            }
            ProjectProcessor.resolveProject(resolvingTask.getProject(), resolvingTask.getClass().getSimpleName());
        }
    }

    private static void resolveTaskUser(){
        if(resolvingTask.getAssociates()!=null){
            if(objClassName.equals(User.class.getSimpleName())){
                resolvingTask.setAssociates(null);
                return;
            }
            for (User user : resolvingTask.getAssociates()){
                UserProcessor.resolveUser(user, resolvingTask.getClass().getSimpleName());
            }
        }
    }

}
