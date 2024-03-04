package net.weg.taskmanager.service;

import lombok.AllArgsConstructor;
import net.weg.taskmanager.model.Priority;
import net.weg.taskmanager.model.UserTask;
import net.weg.taskmanager.model.UserTaskId;
import net.weg.taskmanager.model.dto.TaskDTO;
import net.weg.taskmanager.model.record.PriorityRecord;
import net.weg.taskmanager.repository.*;
import net.weg.taskmanager.service.processor.ResolveStackOverflow;
import net.weg.taskmanager.model.Task;
import net.weg.taskmanager.model.property.Property;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
@AllArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final TaskProjectPropertyRepository taskProjectPropertyRepository;
    private final UserTaskRepository userTaskRepository;
    private final PropertiesRepository propertiesRepository;

    public UserTask setWorkedTime(UserTask userTask){

        UserTask changingUserTask = userTaskRepository.findByUserIdAndTaskId(userTask.getUserId(), userTask.getTaskId());

        if(changingUserTask!=null){
            changingUserTask.setWorkedHours(userTask.getWorkedHours());
            changingUserTask.setWorkedMinutes(userTask.getWorkedMinutes());
            changingUserTask.setWorkedSeconds(userTask.getWorkedSeconds());
        }

        return changingUserTask;
    }

    public Property putPropertyValue(Integer id, Property propertyValue) {
        System.out.println(propertyValue.getPropertyValues().getClass());
        Property neededProperty = taskProjectPropertyRepository.findById(id).get();
        System.out.println(propertyValue);
        neededProperty.setPropertyValues(propertyValue.getPropertyValues());

        return taskProjectPropertyRepository.save(neededProperty);
    }

    public UserTask getUserTask(Integer userId, Integer taskId){
        UserTaskId userTaskId = new UserTaskId();
        userTaskId.setUserId(userId);
        userTaskId.setTaskId(taskId);

//        return userTaskRepository.findByUserIdAndTaskId(userId, taskId);
        return userTaskRepository.findById(userTaskId).get();
    }

        public Task patchProperty(Property property, Integer taskId) {
        Task task = taskRepository.findById(taskId).get();
        if(property.getId()==0){
            System.out.println("entrei2");
            property.setId(null);
        }

        property = this.propertiesRepository.save(property);
        task.getProperties().add(property);

        TaskDTO taskDTO = new TaskDTO();
        PriorityRecord priorityRecord = new PriorityRecord(task.getPriority().name(),task.getPriority().backgroundColor);
        BeanUtils.copyProperties(task,taskDTO);
        taskDTO.setPriority(priorityRecord);

        return update(taskDTO);
    }

    public TaskDTO findById(Integer id) {
        Task task = taskRepository.findById(id).get();
        TaskDTO taskDTO = new TaskDTO();
        PriorityRecord priorityRecord = new PriorityRecord(task.getPriority().name(),task.getPriority().backgroundColor);
        BeanUtils.copyProperties(task,taskDTO);
        taskDTO.setPriority(priorityRecord);
        ResolveStackOverflow.getObjectWithoutStackOverflow(task);
        return taskDTO;
    }

    public Collection<TaskDTO> findAll() {
        Collection<Task> tasks = taskRepository.findAll();
        Collection<TaskDTO> taskDTOS = new ArrayList<>();

        for(Task task : tasks){
            ResolveStackOverflow.getObjectWithoutStackOverflow(task);
        }

        for (Task taskFor: tasks) {
            TaskDTO taskDTO = new TaskDTO();
            PriorityRecord priorityRecord = new PriorityRecord(taskFor.getPriority().name(), taskFor.getPriority().backgroundColor);
            BeanUtils.copyProperties(taskFor,taskDTO);
            taskDTO.setPriority(priorityRecord);
            taskDTOS.add(taskDTO);
        }

        return taskDTOS;
    }

    public void delete(Integer id) {
        Collection<Property> properties = taskRepository.findById(id).get().getProperties();
        taskProjectPropertyRepository.deleteAll(properties);
        taskRepository.deleteById(id);
    }

    public Task create(TaskDTO taskDTO) {
        Task task = new Task();
        Priority prioritySaved = Priority.valueOf(taskDTO.getPriority().name());
        prioritySaved.backgroundColor = taskDTO.getPriority().backgroundColor();
        BeanUtils.copyProperties(taskDTO,task);
        task.setPriority(prioritySaved);
//        if(task.getCurrentStatus()==null){
//            task.getCurrentStatus().setId(null);
//        }

        setStatusListIndex(task);
//        propriedadeSetTarefa(task);
        Task task2 = taskRepository.save(task);
        return ResolveStackOverflow.getObjectWithoutStackOverflow(task2);
    }

    public Task update(TaskDTO taskDTO) {
        Task task = new Task();
        Priority prioritySaved = Priority.valueOf(taskDTO.getPriority().name());
        prioritySaved.backgroundColor = taskDTO.getPriority().backgroundColor();
        BeanUtils.copyProperties(taskDTO,task);
        task.setPriority(prioritySaved);

        setStatusListIndex(task);
//        propriedadeSetTarefa(task);

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

//    private void propriedadeSetTarefa(Task task){
//        //Verifica se há alguma propriedade na tarefa
//        if(task.getProperties() != null && task.getProperties().size()>0){
//            //Passa pela lista de propriedades da tarefa
//            for(Property propriedade : task.getProperties()) {
//                //Adiciona a referencia da tarefa na propriedade
//                propriedade.setTask(task);
//
//
//                //Salva a propriedade atualizada com a referencia da tarefa
//                taskProjectPropertyRepository.save(propriedade);
//            }
//        }
//    }
    private final StatusRepository statusRepository;
    public Collection<Task> getTasksByStatus(Integer id){

        return taskRepository.getTaskByCurrentStatus(statusRepository.findById(id).get());
    }

}