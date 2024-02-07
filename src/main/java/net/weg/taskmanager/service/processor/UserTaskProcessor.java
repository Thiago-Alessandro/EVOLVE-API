package net.weg.taskmanager.service.processor;

import net.weg.taskmanager.model.Task;
import net.weg.taskmanager.model.User;
import net.weg.taskmanager.model.UserTask;
import net.weg.taskmanager.model.UserTaskId;

public class UserTaskProcessor {

    private static UserTask resolvingUserTask;
    private static String objClassName;

    public static UserTask resolveUserTask(UserTask userTask, String objectClassName){

        resolvingUserTask = userTask;
        objClassName = objectClassName;

        resolveUserTaskUser();
        resolveUserTaskTask();

        return resolvingUserTask;
    }
    private static void resolveUserTaskUser(){
        if(objClassName.equals(User.class.getSimpleName())){
            resolvingUserTask.setUser(null);
            return;
        }
        UserProcessor.resolveUser(resolvingUserTask.getUser(), resolvingUserTask.getClass().getSimpleName());
    }

    private static void resolveUserTaskTask(){
        if(objClassName.equals(Task.class.getSimpleName())){
            resolvingUserTask.setTask(null);
            return;
        }
        TaskProcessor.resolveTask(resolvingUserTask.getTask(), resolvingUserTask.getClass().getSimpleName());
    }

}
