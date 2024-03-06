package net.weg.taskmanager.service.processor;

import lombok.NoArgsConstructor;
import net.weg.taskmanager.model.Project;
import net.weg.taskmanager.model.Task;
import net.weg.taskmanager.model.User;

import java.sql.PreparedStatement;
import java.util.ArrayList;
@NoArgsConstructor
public class TaskProcessor {

    private ArrayList<String> resolvingCascade;
    private Task resolvingTask;
    private String taskClassName = Task.class.getSimpleName();

    public static TaskProcessor getInstance(){
        return new TaskProcessor();
    }

    public Task resolveTask(Task task, ArrayList<String> _resolvingCascade){

        resolvingTask = task;
        resolvingCascade = _resolvingCascade;
        resolvingCascade.add(taskClassName);

        resolveTaskProject();
        resolveTaskAssociates();
        resolveTaskCreator();

        resolvingCascade.remove(taskClassName);

        return task;
    }
    public Task resolveTask(Task task){
        return resolveTask(task, new ArrayList<>());
    }


    private void resolveTaskProject(){
        if(resolvingTask.getProject()!=null){
            if(resolvingCascade.contains(Project.class.getSimpleName())){
               resolvingTask.setProject(null);
                return;
            }
            ProjectProcessor.getInstance().resolveProject(resolvingTask.getProject(), resolvingCascade);
        }
    }

    private void resolveTaskAssociates(){
        if(resolvingTask.getAssociates()!=null){
            if(resolvingCascade.contains(User.class.getSimpleName())){
                resolvingTask.setAssociates(null);
                return;
            }
            for (User user : resolvingTask.getAssociates()){
                UserProcessor.getInstance().resolveUser(user, resolvingCascade);
            }
        }
    }
    private void resolveTaskCreator(){
        if(resolvingTask.getCreator()!=null){
            if(resolvingCascade.contains(User.class.getSimpleName())){
                resolvingTask.setCreator(null);
                return;
            }
            UserProcessor.getInstance().resolveUser(resolvingTask.getCreator(), resolvingCascade);
        }
    }

    private void resolveTaskProperties(){

    }

}
