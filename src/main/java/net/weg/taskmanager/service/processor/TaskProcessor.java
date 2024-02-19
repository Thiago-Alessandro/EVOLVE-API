package net.weg.taskmanager.service.processor;

import net.weg.taskmanager.model.Project;
import net.weg.taskmanager.model.Task;
import net.weg.taskmanager.model.User;

import java.sql.PreparedStatement;
import java.util.ArrayList;

public class TaskProcessor {

    private static ArrayList<String> resolvingCascade;
    private static Task resolvingTask;
    private static String taskClassName = Task.class.getSimpleName();

    public static Task resolveTask(Task task, String objClassName, ArrayList<String> _resolvingCascade){

        resolvingTask = task;
        resolvingCascade = _resolvingCascade;
        resolvingCascade.add(objClassName);

        resolveTaskProject();
        resolveTaskAssociates();
        resolveTaskCreator();
        resolvingCascade = null;
        return task;
    }
    public static Task resolveTask(Task task){
        return resolveTask(task, taskClassName, new ArrayList<>());
    }


    private static void resolveTaskProject(){
        if(resolvingTask.getProject()!=null){
            if(resolvingCascade.contains(Project.class.getSimpleName())){
                resolvingTask.setProject(null);
                return;
            }
            ProjectProcessor.resolveProject(resolvingTask.getProject(), taskClassName, resolvingCascade);
        }
    }

    private static void resolveTaskAssociates(){
        if(resolvingTask.getAssociates()!=null){
            if(resolvingCascade.contains(User.class.getSimpleName())){
                resolvingTask.setAssociates(null);
                return;
            }
            for (User user : resolvingTask.getAssociates()){
                UserProcessor.resolveUser(user, taskClassName, resolvingCascade);
            }
        }
    }
    private static void resolveTaskCreator(){
        if(resolvingTask.getCreator()!=null){
            if(resolvingCascade.contains(User.class.getSimpleName())){
                resolvingTask.setCreator(null);
                return;
            }
            UserProcessor.resolveUser(resolvingTask.getCreator(), taskClassName, resolvingCascade);
        }
    }

    private static void resolveTaskProperties(){

    }

}
