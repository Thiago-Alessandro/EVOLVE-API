package net.weg.taskmanager.service.processor;

import lombok.NoArgsConstructor;
import net.weg.taskmanager.model.Task;
import net.weg.taskmanager.model.User;
import net.weg.taskmanager.model.UserTask;
import net.weg.taskmanager.model.UserTaskId;

import java.util.ArrayList;

@NoArgsConstructor
public class UserTaskProcessor {

    private UserTask resolvingUserTask;
    private ArrayList<String> resolvingCascade;
    private String userTaskClassName = UserTask.class.getName();

    public static UserTaskProcessor getInstance(){
        return new UserTaskProcessor();
    }

    public UserTask resolveUserTask(UserTask userTask, ArrayList<String> _resolvingCascade){

        resolvingUserTask = userTask;
        resolvingCascade = _resolvingCascade;
        resolvingCascade.add(userTaskClassName);

        resolveUserTaskUser();
        resolveUserTaskTask();

        resolvingCascade.remove(userTaskClassName);

        return resolvingUserTask;
    }

    public UserTask resolveUserTask(UserTask userTask){
        return resolveUserTask(userTask, new ArrayList<>());
    }

    private void resolveUserTaskUser(){
        if(resolvingCascade.contains(User.class.getSimpleName())){
            resolvingUserTask.setUser(null);
            return;
        }
        UserProcessor.getInstance().resolveUser(resolvingUserTask.getUser(), resolvingCascade);
    }

    private void resolveUserTaskTask(){
        if(resolvingCascade.contains(Task.class.getSimpleName())){
            resolvingUserTask.setTask(null);
            return;
        }
        TaskProcessor.getInstance().resolveTask(resolvingUserTask.getTask(), resolvingCascade);
    }

}
