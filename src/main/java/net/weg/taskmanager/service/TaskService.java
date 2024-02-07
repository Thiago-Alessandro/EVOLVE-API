package net.weg.taskmanager.service;

import jdk.swing.interop.SwingInterOpUtils;
import lombok.AllArgsConstructor;
import net.weg.taskmanager.model.User;
import net.weg.taskmanager.model.UserTask;
import net.weg.taskmanager.model.UserTaskId;
import net.weg.taskmanager.repository.UserTaskRepository;
import net.weg.taskmanager.service.processor.ResolveStackOverflow;
import net.weg.taskmanager.model.Task;
import net.weg.taskmanager.model.property.TaskProjectProperty;
import net.weg.taskmanager.repository.StatusRepository;
import net.weg.taskmanager.repository.TaskProjectPropertyRepository;
import net.weg.taskmanager.repository.TaskRepository;
import net.weg.taskmanager.service.processor.UserTaskProcessor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final TaskProjectPropertyRepository taskProjectPropertyRepository;
    private final UserTaskRepository userTaskRepository;

    public UserTask setWorkedTime(UserTask userTask){

        UserTask changingUserTask = userTaskRepository.findByUserIdAndTaskId(userTask.getUserId(), userTask.getTaskId());

        if(changingUserTask!=null){
            changingUserTask.setWorkedHours(userTask.getWorkedHours());
            changingUserTask.setWorkedMinutes(userTask.getWorkedMinutes());
            changingUserTask.setWorkedSeconds(userTask.getWorkedSeconds());
        }

        return changingUserTask;
    }

    public UserTask getUserTask(Integer userId, Integer taskId){
        return userTaskRepository.findByUserIdAndTaskId(userId, taskId);
    }

    public Task findById(Integer id) {
        Task task = taskRepository.findById(id).get();
        ResolveStackOverflow.getObjectWithoutStackOverflow(task);
        return task;
    }

    public Collection<Task> findAll() {
        Collection<Task> tasks = taskRepository.findAll();

        for(Task task : tasks){
            ResolveStackOverflow.getObjectWithoutStackOverflow(task);
        }
        return tasks;
    }

    public void delete(Integer id) {
        taskRepository.deleteById(id);
    }

    public Task create(Task task) {
        if(task.getCurrentStatus().getId()==0){
            task.getCurrentStatus().setId(null);

        }
//        System.out.println(task);
//        setStatusListIndex(task);
        return update(taskRepository.save(task));
    }

    public Task update(Task task) {

        setStatusListIndex(task);
        propriedadeSetTarefa(task);

        Task updatedTask = taskRepository.save(task);

        syncUserTaskTable(task);

        return ResolveStackOverflow.getObjectWithoutStackOverflow(updatedTask);
    }

    private void syncUserTaskTable(Task task){
        if(task.getAssociates()!=null){

            for(User user : task.getAssociates()){
                if(!userTaskRepository.existsById(new UserTaskId(user.getId(), task.getId()))){
                    UserTask userTask = new UserTask();
                    userTask.setUserId(user.getId());
                    userTask.setTaskId(task.getId());
                    System.out.println(userTask);
                    userTaskRepository.save(userTask);
                }
            }
//            userTasks.forEach(userTask -> UserTaskProcessor.resolveUserTask(userTask, UserTask.class.getSimpleName()));

            userTaskRepository.findAll().stream()
                    .filter(userTask -> Objects.equals(userTask.getTaskId(), task.getId()))
                    .filter(userTask -> !task.getAssociates().contains(userTask.getUser()))
                    .forEach(userTask -> userTaskRepository.delete(userTask));
        }

    }

    private void setStatusListIndex(Task task){
        Integer defaultIndex = -1;
        Integer firstIndex = 0;
        if(task.getCurrentStatus()!=null && task.getStatusListIndex() != null){
            if(task.getStatusListIndex() == defaultIndex){
                Collection<Task> listaTasks = getTasksByStatus(task.getCurrentStatus().getId());
                if(listaTasks != null){
                    task.setStatusListIndex(listaTasks.size());
                }else {
                    task.setStatusListIndex(firstIndex);
                }
            }
        } else {
            task.setStatusListIndex(defaultIndex);
        }
    }

    private void propriedadeSetTarefa(Task task){
        //Verifica se há alguma propriedade na tarefa
        if(task.getProperties() != null && task.getProperties().size()>0){
            //Passa pela lista de propriedades da tarefa
            for(TaskProjectProperty propriedade : task.getProperties()) {
                //Adiciona a referencia da tarefa na propriedade
                propriedade.setTask(task);
                //Salva a propriedade atualizada com a referencia da tarefa
                taskProjectPropertyRepository.save(propriedade);
            }
        }
    }
    private final StatusRepository statusRepository;
    public Collection<Task> getTasksByStatus(Integer id){

        return taskRepository.getTaskByCurrentStatus(statusRepository.findById(id).get());
    }

}