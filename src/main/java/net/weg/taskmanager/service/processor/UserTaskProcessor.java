package net.weg.taskmanager.service.processor;

import net.weg.taskmanager.model.Task;
import net.weg.taskmanager.model.User;
import net.weg.taskmanager.model.UserTask;
import net.weg.taskmanager.model.UserTaskId;

import java.util.ArrayList;

public class UserTaskProcessor {

    private static UserTask resolvingUserTask;
    private static ArrayList<String> resolvingCascade;
    private static String userTaskClassName = UserTask.class.getName();

    public static UserTask resolveUserTask(UserTask userTask, String objClassName, ArrayList<String> _resolvingCascade){

        resolvingUserTask = userTask;
        resolvingCascade = _resolvingCascade;
        resolvingCascade.add(objClassName);

        resolveUserTaskUser();
        resolveUserTaskTask();

        resolvingCascade.remove(objClassName);

        return resolvingUserTask;
    }

    public static UserTask resolveUserTask(UserTask userTask){
        return resolveUserTask(userTask, userTaskClassName, new ArrayList<>());
    }

    private static void resolveUserTaskUser(){
        if(resolvingCascade.contains(User.class.getSimpleName())){
            resolvingUserTask.setUser(null);
            return;
        }
        UserProcessor.resolveUser(resolvingUserTask.getUser(), resolvingUserTask.getClass().getSimpleName(), resolvingCascade);
    }

    private static void resolveUserTaskTask(){
        if(resolvingCascade.contains(Task.class.getSimpleName())){
            resolvingUserTask.setTask(null);
            return;
        }
        TaskProcessor.resolveTask(resolvingUserTask.getTask(), resolvingUserTask.getClass().getSimpleName(), resolvingCascade);
    }

}
