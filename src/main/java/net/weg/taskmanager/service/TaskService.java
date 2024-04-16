package net.weg.taskmanager.service;

import lombok.AllArgsConstructor;


import net.weg.taskmanager.model.entity.*;
import net.weg.taskmanager.model.dto.get.GetUserDTO;
import net.weg.taskmanager.model.dto.utils.DTOUtils;
import net.weg.taskmanager.model.dto.post.PostTaskDTO;
import net.weg.taskmanager.model.dto.put.PutTaskDTO;
import net.weg.taskmanager.model.property.Option;
import net.weg.taskmanager.model.property.Property;
import net.weg.taskmanager.model.property.PropertyType;
import net.weg.taskmanager.model.record.PriorityRecord;
import net.weg.taskmanager.service.processor.PropertyProcessor;
import net.weg.taskmanager.service.processor.TaskProcessor;

import net.weg.taskmanager.model.enums.Priority;
import net.weg.taskmanager.model.dto.get.GetTaskDTO;
import net.weg.taskmanager.model.property.values.PropertyValue;
import net.weg.taskmanager.repository.*;
import org.springframework.beans.BeanUtils;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Service
@AllArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final PropertyRepository propertyRepository;
    private final UserTaskRepository userTaskRepository;
    private final UserRepository userRepository;
    //    private final ModelMapper modelMapper;
    private final PropertyValueRepository propertyValueRepository;
    private final OptionRepository optionRepository;
    private final CommentRepository commentRepository;
    private final HistoricRepository historicRepository;
    private final HistoricService historicService;
    private final SubTaskRepository subTaskRepository;

    public UserTask setWorkedTime(UserTask userTask) {

        UserTask changingUserTask = userTaskRepository.findByUserIdAndTaskId(userTask.getUserId(), userTask.getTaskId());

        if (changingUserTask != null) {
            changingUserTask.setWorkedHours(userTask.getWorkedHours());
            changingUserTask.setWorkedMinutes(userTask.getWorkedMinutes());
            changingUserTask.setWorkedSeconds(userTask.getWorkedSeconds());
        }

        return changingUserTask;
    }

    public Collection<Comment> getAllCommentsOfTask(Long taskId) {
        Task task = taskRepository.findById(taskId).get();
        return task.getComments();
    }

    public Comment patchNewComment(Long taskId, Comment newComment, Long userId) {
        Task task = taskRepository.findById(taskId).get();
        newComment.setTask(task);
        Comment commentSaved = commentRepository.save(newComment);

        task = historicService.patchNewCommentHistoric(taskId, userId);

        taskRepository.save(task);

        return commentSaved;
    }

    public Collection<Comment> deleteComment(Long commentId, Long taskId, Long userId) {
        Task task = taskRepository.findById(taskId).get();

        commentRepository.deleteById(commentId);
        task = historicService.deleteCommentHistoric(taskId, userId);

        return task.getComments();
    }


    public Property putPropertyValue(PropertyValue propertyValue, Long propertyId, Long userId, Long taskId) {
        PropertyValue propertyValueReturn = this.propertyValueRepository.save(propertyValue);
        Property propertyOfPropertyValue = this.propertyRepository.findById(propertyId).get();


        this.propertyRepository.save(propertyOfPropertyValue);

        propertyValueReturn.setProperty(propertyOfPropertyValue);
        propertyOfPropertyValue.getPropertyValues().add(propertyValueReturn);


        this.propertyRepository.save(propertyOfPropertyValue);

        this.historicService.putPropertyValueHistoric(propertyOfPropertyValue, propertyValueReturn, userId, taskId);

        return PropertyProcessor.getInstance().resolveProperty(propertyOfPropertyValue);
    }

    public Option putPropertyOption(Option newOption, Long userId, Long taskId, Long propertyId) {
        Property property = this.propertyRepository.findById(propertyId).get();
        this.historicService.putPropertyOptionHistoric(newOption, userId, taskId, propertyId);
        Option savedOption = this.optionRepository.save(newOption);
        property.getOptions().add(savedOption);
        this.propertyRepository.save(property);
        return savedOption;
    }

    public Property deletePropertyOption(Long userId, Long taskId, Long propertyId, Long optionId) {
        Property property = this.propertyRepository.findById(propertyId).get();
        Option option = this.optionRepository.findById(Math.toIntExact(optionId)).get();
        this.historicService.deletePropertyOptionHistoric(userId, taskId, propertyId, optionId);
        property.getOptions().remove(option);
        this.optionRepository.delete(option);
        return this.propertyRepository.save(property);
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

    public Property updatePropertyCurrentOptions(Collection<Option> newCurrentOptions, Long propertyId, Long taskId, Long userId) {
        Property property = propertyRepository.findById(propertyId).get();
        newCurrentOptions.forEach(newCurrentOption -> {
            if (!property.getCurrentOptions().contains(newCurrentOption)) {
                historicService.updatePropertyCurrentOptions(userId, taskId, newCurrentOption, property);
            }
        });

        if (newCurrentOptions.size() < property.getCurrentOptions().size()) {
            property.getCurrentOptions().forEach(oldCurrentOption -> {
                if (!newCurrentOptions.contains(oldCurrentOption)) {
                    historicService.oldPropertyCurrentOptions(userId, taskId, oldCurrentOption, property);
                }
            });
        }

        property.setCurrentOptions(newCurrentOptions);

        return propertyRepository.save(property);
    }

    public GetTaskDTO patchProperty(Property property, Long taskId, Long userId) {
        Task task = taskRepository.findById(taskId).get();
        if (property.getOptions() != null) {
            optionRepository.saveAll(property.getOptions());
        }
        property = this.propertyRepository.save(property);
        task.getProperties().add(property);

        Task savedTask = historicService.patchProperty(property, userId, taskId);

        return resolveAndGetDTO(savedTask);
    }

    public Collection<GetUserDTO> patchAssociate(Long taskId, Collection<GetUserDTO> associates, Long userId) {
        Collection<User> newList = new ArrayList<>();
        Task task = taskRepository.findById(taskId).get();
        associates.forEach(associate -> {
            User user = new User();
            BeanUtils.copyProperties(associate, user);
            newList.add(user);
        });
        task.setAssociates(newList);
        ArrayList<String> currentUsersAssociates = new ArrayList<>();
        associates.forEach(user -> {
            currentUsersAssociates.add(userRepository.findById(user.getId()).get().getName());
        });

        task = historicService.patchAssociateHistoric(taskId, userId, newList, currentUsersAssociates);

        return DTOUtils.usersToGetUserDTOs(taskRepository.save(task).getAssociates());
    }

    public GetTaskDTO updateCurrentStatus(Long taskId, Long userId, Status status) {
        Task task = this.taskRepository.findById(taskId).get();
        User user = this.userRepository.findById(userId).get();

        task.setCurrentStatus(status);

        task = historicService.updateCurrentStatusHistoric(user, task, status);

        return new GetTaskDTO(task);
    }

    public GetTaskDTO updateCurrentPriority(Long taskId, Long userId, PriorityRecord priorityRecord) {
        Task task = this.taskRepository.findById(taskId).get();
        User user = this.userRepository.findById(userId).get();
        Priority priority = Priority.valueOf(priorityRecord.name());

        task.setPriority(priority);

        task = historicService.updateCurrentPriorityHistoric(task, user, priority);
        System.out.println(task.getPriority());

        return new GetTaskDTO(task);
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

    private GetTaskDTO resolveAndGetDTO(Task task) {
        taskProcessor.resolveTask(task);
        return new GetTaskDTO(task);
    }

    private Collection<GetTaskDTO> resolveAndGetDTOS(Collection<Task> tasks) {
        return tasks.stream().map(this::resolveAndGetDTO).toList();
    }

    public GetTaskDTO update(PutTaskDTO putTaskDTO, Long userId) {
        Task task = taskRepository.findById(putTaskDTO.getId()).get();
        User userForHistoric = userRepository.findById(userId).get();

        task = historicService.generalUpdateHistoric(putTaskDTO, task, userForHistoric);

        BeanUtils.copyProperties(putTaskDTO, task);

        setStatusListIndex(task);

        task.setProgress(setProgress(task));

        Task updatedTask = taskRepository.save(task);
        syncUserTaskTable(updatedTask);


        return resolveAndGetDTO(updatedTask);
    }

    private double setProgress(Task task) {
        Collection<Subtask> totalConcludedSubtasks = task.getSubtasks().stream().filter(Subtask::getConcluded).toList();
        double total = task.getSubtasks().size();
        if (total == 0) {
            return 0.0;
        }
        double totalConcluded = totalConcludedSubtasks.size();
        double progress = (totalConcluded / total) * 100;

        return progress;

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

            userTaskRepository.findAll().stream().filter(userTask -> Objects.equals(userTask.getTaskId(), task.getId())).filter(userTask -> !task.getAssociates().contains(userTask.getUser())).forEach(userTask -> userTaskRepository.delete(userTask));
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

    public Collection<GetTaskDTO> getTasksByUserId(Long id) {

        User user = userRepository.findById(id).get();

        Collection<Task> associatedTasks = taskRepository.getTasksByAssociatesContaining(user);
        Collection<Task> createdTasks = taskRepository.getTasksByCreatorIs(user);

        Collection<GetTaskDTO> getTaskDTOS = new HashSet<>();

        associatedTasks.forEach((task -> getTaskDTOS.add(transformToTaskDTO(task))));
        createdTasks.forEach(createdTask -> associatedTasks.forEach(associatedTask -> {
            if (!createdTask.getId().equals(associatedTask.getId())) {
                getTaskDTOS.add(transformToTaskDTO(createdTask));
            }
        }));

        return getTaskDTOS;
    }

    private GetTaskDTO transformToTaskDTO(Task task) {
        TaskProcessor.getInstance().resolveTask(task);
        return new GetTaskDTO(task);
    }


    private final StatusRepository statusRepository;

    public Collection<GetTaskDTO> getTasksByStatus(Long id) {

        Collection<Task> tasks = taskRepository.getTaskByCurrentStatus(statusRepository.findById(id).get());
        Collection<GetTaskDTO> getTaskDTOS = new ArrayList<>();
        tasks.forEach((task) -> {
            getTaskDTOS.add(transformToTaskDTO(task));
        });

        return getTaskDTOS;
    }

    public GetTaskDTO updateTaskName(Long taskId, Long userId, String name) {
        Task task = taskRepository.findById(taskId).get();
        task.setName(name);
        taskRepository.save(task);
        return transformToTaskDTO(task);
    }

    public GetTaskDTO updateTaskFinalDate(Long taskId, Long userId, LocalDate newFinalDate) {
        Task task = taskRepository.findById(taskId).get();
        task.setFinalDate(newFinalDate);
        taskRepository.save(task);
        task = historicService.updateTaskFinalDateHistoric(taskId, userId, newFinalDate);
        return transformToTaskDTO(task);
    }

    public GetTaskDTO patchSubtask(Subtask subtask, Long taskId, Long userId) {
        Task task = taskRepository.findById(taskId).get();
        this.subTaskRepository.save(subtask);
        task.getSubtasks().add(subtask);
        this.taskRepository.save(task);
        task = this.historicService.patchSubtaskHistoric(subtask, userId, taskId);
        return transformToTaskDTO(task);
    }

    public GetTaskDTO deleteSubtask(Long subtaskId, Long taskId, Long userId) {
        Subtask subtask = subTaskRepository.findById(subtaskId).get();
        Task task = this.historicService.deleteSubtaskHistoric(subtaskId, userId, taskId);
        task.getSubtasks().remove(subtask);
        subTaskRepository.delete(subtask);
        return transformToTaskDTO(task);
    }
}