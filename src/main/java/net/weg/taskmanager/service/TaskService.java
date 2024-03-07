package net.weg.taskmanager.service;

import lombok.AllArgsConstructor;

import net.weg.taskmanager.model.User;
import net.weg.taskmanager.model.UserTask;
import net.weg.taskmanager.model.UserTaskId;
import net.weg.taskmanager.model.Task;
import net.weg.taskmanager.model.dto.post.PostTaskDTO;
import net.weg.taskmanager.model.dto.put.PutTaskDTO;
import net.weg.taskmanager.service.processor.TaskProcessor;

import net.weg.taskmanager.model.Priority;
import net.weg.taskmanager.model.dto.get.GetTaskDTO;
import net.weg.taskmanager.model.property.values.PropertyValue;
import net.weg.taskmanager.model.record.PriorityRecord;
import net.weg.taskmanager.repository.*;
import net.weg.taskmanager.model.property.Property;
import org.springframework.beans.BeanUtils;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

@Service
@AllArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final PropertyRepository propertyRepository;
    private final UserTaskRepository userTaskRepository;
    //    private final ModelMapper modelMapper;
    private final PropertiesRepository propertiesRepository;
    private final PropertyValueRepository propertyValueRepository;

    public UserTask setWorkedTime(UserTask userTask) {

        UserTask changingUserTask = userTaskRepository.findByUserIdAndTaskId(userTask.getUserId(), userTask.getTaskId());

        if (changingUserTask != null) {
            changingUserTask.setWorkedHours(userTask.getWorkedHours());
            changingUserTask.setWorkedMinutes(userTask.getWorkedMinutes());
            changingUserTask.setWorkedSeconds(userTask.getWorkedSeconds());
        }

        return changingUserTask;
    }


    public PropertyValue putPropertyValue(PropertyValue propertyValue) {
        return this.propertyValueRepository.save(propertyValue);
    }

    public UserTask getUserTask(Long userId, Long taskId) {
        UserTaskId userTaskId = new UserTaskId();
        userTaskId.setUserId(userId);
        userTaskId.setTaskId(taskId);

        return userTaskRepository.findById(userTaskId).get();
    }

    public GetTaskDTO patchProperty(Property property, Long taskId) {
        Task task = taskRepository.findById(taskId).get();
        if (property.getId() == 0) {
            property.setId(null);
        }

        property = this.propertiesRepository.save(property);
        task.getProperties().add(property);

        GetTaskDTO getTaskDTO = new GetTaskDTO();
        PriorityRecord priorityRecord = new PriorityRecord(task.getPriority().name(), task.getPriority().backgroundColor);
        BeanUtils.copyProperties(task, getTaskDTO);
        getTaskDTO.setPriority(priorityRecord);

        taskRepository.save(task);

        return getTaskDTO;
    }

    public GetTaskDTO findById(Long id) {
        Task task = taskRepository.findById(id).get();
        GetTaskDTO getTaskDTO = new GetTaskDTO();
        PriorityRecord priorityRecord = new PriorityRecord(task.getPriority().name(), task.getPriority().backgroundColor);
        BeanUtils.copyProperties(task, getTaskDTO);
        getTaskDTO.setPriority(priorityRecord);
        TaskProcessor.getInstance().resolveTask(task);
        return getTaskDTO;
    }

    public Collection<GetTaskDTO> findAll() {
        Collection<Task> tasks = taskRepository.findAll();
        Collection<GetTaskDTO> getTaskDTOS = new ArrayList<>();

        for (Task task : tasks) {
            TaskProcessor.getInstance().resolveTask(task);
        }

        for (Task taskFor : tasks) {
            GetTaskDTO getTaskDTO = new GetTaskDTO();
            PriorityRecord priorityRecord = new PriorityRecord(taskFor.getPriority().name(), taskFor.getPriority().backgroundColor);
            BeanUtils.copyProperties(taskFor, getTaskDTO);
            getTaskDTO.setPriority(priorityRecord);
            getTaskDTOS.add(getTaskDTO);
        }

        return getTaskDTOS;
    }

    public void delete(Long id) {
        Collection<Property> properties = taskRepository.findById(id).get().getProperties();
        propertyRepository.deleteAll(properties);
        taskRepository.deleteById(id);
    }

    public GetTaskDTO create(PostTaskDTO postTaskDTO) {
        Task task = new Task();
        BeanUtils.copyProperties(postTaskDTO, task);
        Priority prioritySaved = Priority.valueOf(postTaskDTO.getPriority().name());
        prioritySaved.backgroundColor = postTaskDTO.getPriority().backgroundColor();
        task.setPriority(prioritySaved);

        setStatusListIndex(task);
        System.out.println(taskRepository.save(task).getProject().getName());
        Task task2 = taskRepository.save(task);
        System.out.println(task2.getProject().getName());
        System.out.println(task2.getProject().getId());
        syncUserTaskTable(task2);
//        System.out.println("task2 CREATE");
//        System.out.println(task2.getCreator().getName());
        TaskProcessor.getInstance().resolveTask(task2);
//        System.out.println("task2 CREATE resolved");
//        System.out.println(task2.getCreator().getName());

        GetTaskDTO getTaskDTO = new GetTaskDTO();
        BeanUtils.copyProperties(task2, getTaskDTO);
        return getTaskDTO;
    }

    public GetTaskDTO update(PutTaskDTO putTaskDTO) {
        Task task = new Task();
        Priority prioritySaved = Priority.valueOf(putTaskDTO.getPriority().name());
        prioritySaved.backgroundColor = putTaskDTO.getPriority().backgroundColor();
        BeanUtils.copyProperties(putTaskDTO, task);
        task.setPriority(prioritySaved);

        setStatusListIndex(task);

        Task updatedTask = taskRepository.save(task);

        syncUserTaskTable(updatedTask);

        TaskProcessor.getInstance().resolveTask(updatedTask);
        GetTaskDTO getTaskDTO = new GetTaskDTO();
        BeanUtils.copyProperties(updatedTask,getTaskDTO);
        return getTaskDTO;
    }

    private void syncUserTaskTable(Task task) {
        if (task.getAssociates() != null) {

            for (User user : task.getAssociates()) {
                if (!userTaskRepository.existsById(new UserTaskId(user.getId(), task.getId()))) {
                    UserTask userTask = new UserTask();
                    userTask.setUserId(user.getId());
                    userTask.setTaskId(task.getId());
                    userTaskRepository.save(userTask);
                }
            }

            userTaskRepository.findAll().stream()
                    .filter(userTask -> Objects.equals(userTask.getTaskId(), task.getId()))
                    .filter(userTask -> !task.getAssociates().contains(userTask.getUser()))
                    .forEach(userTask -> userTaskRepository.delete(userTask));
        }

    }

    private void setStatusListIndex(Task task) {
        Integer defaultIndex = -1;
        Integer firstIndex = 0;
        if (task.getCurrentStatus() != null && task.getStatusListIndex() != null) {
            if (task.getStatusListIndex() == defaultIndex) {
                Collection<GetTaskDTO> listaTasks = getTasksByStatus(task.getCurrentStatus().getId());
                if (listaTasks != null) {
                    task.setStatusListIndex(listaTasks.size());
                } else {
                    task.setStatusListIndex(firstIndex);
                }
            }
        } else {
            task.setStatusListIndex(defaultIndex);
        }
    }

    private final StatusRepository statusRepository;

    public Collection<GetTaskDTO> getTasksByStatus(Long id) {

        Collection<Task> tasks = taskRepository.getTaskByCurrentStatus(statusRepository.findById(id).get());
        Collection<GetTaskDTO> getTaskDTOS = new ArrayList<>();
        tasks
                .forEach((task) -> {
                    TaskProcessor.getInstance().resolveTask(task);

                    GetTaskDTO getTaskDTO = new GetTaskDTO();
                    BeanUtils.copyProperties(task, getTaskDTO);
                    getTaskDTOS.add(getTaskDTO);
                });

        return getTaskDTOS;
    }

}