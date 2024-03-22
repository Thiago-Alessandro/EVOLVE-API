package net.weg.taskmanager.service;

import lombok.AllArgsConstructor;

import net.weg.taskmanager.model.entity.User;
import net.weg.taskmanager.model.entity.UserTask;
import net.weg.taskmanager.model.entity.UserTaskId;
import net.weg.taskmanager.model.entity.Task;
import net.weg.taskmanager.model.dto.post.PostTaskDTO;
import net.weg.taskmanager.model.dto.put.PutTaskDTO;
import net.weg.taskmanager.model.property.Property;
import net.weg.taskmanager.service.processor.PropertyProcessor;
import net.weg.taskmanager.service.processor.TaskProcessor;

import net.weg.taskmanager.model.enums.Priority;
import net.weg.taskmanager.model.dto.get.GetTaskDTO;
import net.weg.taskmanager.model.property.values.PropertyValue;
import net.weg.taskmanager.model.record.PriorityRecord;
import net.weg.taskmanager.repository.*;
import org.springframework.beans.BeanUtils;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;

@Service
@AllArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final PropertyRepository propertyRepository;
    private final UserTaskRepository userTaskRepository;
    private final UserRepository userRepository;
    //    private final ModelMapper modelMapper;
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


    public Property putPropertyValue(PropertyValue propertyValue,
                                     Long propertyId) {

        PropertyValue propertyValueReturn = this.propertyValueRepository.save(propertyValue);
        Property propertyOfPropertyValue = this.propertyRepository.findById(propertyId).get();

        if (propertyOfPropertyValue.getPropertyType().name().equals("MultiSelectValue") ||
                propertyOfPropertyValue.getPropertyType().name().equals("UniSelectValue")) {
            propertyOfPropertyValue.getPropertyValues().add(propertyValueReturn);
        } else {

            propertyOfPropertyValue.getPropertyValues().forEach(propertyValue1 -> {
                propertyValue1.setProperty(null);
            });

            this.propertyRepository.save(propertyOfPropertyValue);

            propertyValueReturn.setProperty(propertyOfPropertyValue);
            propertyOfPropertyValue.getPropertyValues().add(propertyValueReturn);
        }
        this.propertyRepository.save(propertyOfPropertyValue);

        return PropertyProcessor.getInstance().resolveProperty(propertyOfPropertyValue);
    }

    public Collection<Property> getAllProperties() {
        return this.propertyRepository.findAll();
    }

    public UserTask getUserTask(Long userId, Long taskId) {
        UserTaskId userTaskId = new UserTaskId();
        userTaskId.setUserId(userId);
        userTaskId.setTaskId(taskId);

        return userTaskRepository.findById(userTaskId).get();
    }

    public GetTaskDTO patchProperty(Property property, Long taskId) {
        Task task = taskRepository.findById(taskId).get();

        property = this.propertyRepository.save(property);
        task.getProperties().add(property);

        Task savedTask = taskRepository.save(task);

        return resolveAndGetDTO(savedTask);
    }

    public GetTaskDTO findById(Long id) {
        Task task = taskRepository.findById(id).get();

        return resolveAndGetDTO(task);
    }

    public Collection<GetTaskDTO> findAll() {
        Collection<Task> tasks = taskRepository.findAll();
        return resolveAndGetDTOS(tasks);
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
        taskRepository.save(task);
        Task task2 = taskRepository.findById(task.getId()).get();
        syncUserTaskTable(task2);


        return resolveAndGetDTO(task2);
    }
    private final TaskProcessor taskProcessor = new TaskProcessor();
    private GetTaskDTO resolveAndGetDTO(Task task){
        taskProcessor.resolveTask(task);
        System.out.println(task + "df");
        return new GetTaskDTO(task);
    }
    private Collection<GetTaskDTO> resolveAndGetDTOS(Collection<Task> tasks){
        return tasks.stream().map(this::resolveAndGetDTO).toList();
    }
    public GetTaskDTO update(PutTaskDTO putTaskDTO) {
        Task task = taskRepository.findById(putTaskDTO.getId()).get();
        Priority prioritySaved = Priority.valueOf(putTaskDTO.getPriority().name());
        prioritySaved.backgroundColor = putTaskDTO.getPriority().backgroundColor();
        BeanUtils.copyProperties(putTaskDTO, task);
        task.setPriority(prioritySaved);
        setStatusListIndex(task);

        Task updatedTask = taskRepository.save(task);
        syncUserTaskTable(updatedTask);


        return resolveAndGetDTO(updatedTask);
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

    public Collection<GetTaskDTO> getTasksByUserId(Long id){

        User user = userRepository.findById(id).get();

        Collection<Task> associatedTasks = taskRepository.getTasksByAssociatesContaining(user);
        Collection<Task> createdTasks = taskRepository.getTasksByCreatorIs(user);

        Collection<GetTaskDTO> getTaskDTOS = new HashSet<>();

        associatedTasks.forEach((task -> getTaskDTOS.add(transformToTaskDTO(task))));
        createdTasks.forEach(createdTask ->
            associatedTasks.forEach(associatedTask -> {
                if (!createdTask.getId().equals(associatedTask.getId())){
                    getTaskDTOS.add(transformToTaskDTO(createdTask));
                }
            })
        );

        return getTaskDTOS;
    }

    private GetTaskDTO transformToTaskDTO(Task task){
        TaskProcessor.getInstance().resolveTask(task);
        return new GetTaskDTO(task);
    }


    private final StatusRepository statusRepository;

    public Collection<GetTaskDTO> getTasksByStatus(Long id) {

        Collection<Task> tasks = taskRepository.getTaskByCurrentStatus(statusRepository.findById(id).get());
        Collection<GetTaskDTO> getTaskDTOS = new ArrayList<>();
        tasks
                .forEach((task) -> {
                    getTaskDTOS.add(transformToTaskDTO(task));
                });

        return getTaskDTOS;
    }

}