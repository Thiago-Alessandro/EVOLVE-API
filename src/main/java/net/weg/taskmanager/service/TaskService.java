package net.weg.taskmanager.service;

import lombok.AllArgsConstructor;
import net.weg.taskmanager.service.processor.ResolveStackOverflow;
import net.weg.taskmanager.model.Task;
import net.weg.taskmanager.model.property.TaskProjectProperty;
import net.weg.taskmanager.repository.StatusRepository;
import net.weg.taskmanager.repository.TaskProjectPropertyRepository;
import net.weg.taskmanager.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@AllArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final TaskProjectPropertyRepository taskProjectPropertyRepository;

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
        taskRepository.save(task);
        setStatusListIndex(task);
        return update(task);
    }

    public Task update(Task task) {

        setStatusListIndex(task);
        propriedadeSetTarefa(task);

        Task updatedTask = taskRepository.save(task);
        return ResolveStackOverflow.getObjectWithoutStackOverflow(updatedTask);
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