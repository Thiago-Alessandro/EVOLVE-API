package net.weg.taskmanager.service;

import lombok.AllArgsConstructor;


import net.weg.taskmanager.model.dto.converter.Converter;
import net.weg.taskmanager.model.dto.converter.get.GetTaskConverter;
import net.weg.taskmanager.model.dto.converter.get.GetUserConverter;
import net.weg.taskmanager.model.entity.*;
import net.weg.taskmanager.model.dto.get.GetUserDTO;
import net.weg.taskmanager.model.dto.post.PostTaskDTO;
import net.weg.taskmanager.model.dto.put.PutTaskDTO;
import net.weg.taskmanager.model.property.Option;
import net.weg.taskmanager.model.property.Property;
import net.weg.taskmanager.model.record.PriorityRecord;
import net.weg.taskmanager.service.processor.PropertyProcessor;
import net.weg.taskmanager.service.processor.TaskProcessor;

import net.weg.taskmanager.model.enums.Priority;
import net.weg.taskmanager.model.dto.get.GetTaskDTO;
import net.weg.taskmanager.model.property.values.PropertyValue;
import net.weg.taskmanager.repository.*;
import org.springframework.beans.BeanUtils;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

@Service
@AllArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final PropertyRepository propertyRepository;
    private final UserTaskRepository userTaskRepository;
    private final PropertyValueRepository propertyValueRepository;
    private final OptionRepository optionRepository;
    private final CommentRepository commentRepository;
    private final SubTaskRepository subTaskRepository;
    private final ProjectRepository projectRepository;

    private final HistoricService historicService;
    private final Converter<GetTaskDTO, Task> converter = new GetTaskConverter();

    public UserTask setWorkedTime(UserTask userTask) {

        UserTask changingUserTask = userTaskService.findByUserIdAndTaskId(userTask.getUserId(), userTask.getTaskId());

        if (changingUserTask != null) {
            changingUserTask.setWorkedHours(userTask.getWorkedHours());
            changingUserTask.setWorkedMinutes(userTask.getWorkedMinutes());
            changingUserTask.setWorkedSeconds(userTask.getWorkedSeconds());
        }

        return changingUserTask;
    }

    public Task findTaskById(Long taskId) {
        Optional<Task> optionalTask = taskRepository.findById(taskId);
        if (optionalTask.isEmpty()) throw new NoSuchElementException();
        return optionalTask.get();
    }

    public Collection<Comment> getAllCommentsOfTask(Long taskId) {
        Task task = findTaskById(taskId);
        return task.getComments();
    }

    public Comment patchNewComment(Long taskId, Comment newComment, Long userId) {
        Task task = findTaskById(taskId);
        newComment.setTask(task);
        Comment commentSaved = commentRepository.save(newComment);

        task = historicService.patchNewCommentHistoric(taskId, userId);

        taskRepository.save(task);

        return commentSaved;
    }

    public Collection<Comment> deleteComment(Long commentId, Long taskId, Long userId) {
        commentRepository.deleteById(commentId);
        Task task = historicService.deleteCommentHistoric(taskId, userId);

        return task.getComments();
    }


    public Property putPropertyValue(PropertyValue propertyValue, Long propertyId, Long userId, Long taskId) {
        PropertyValue propertyValueReturn = propertyValueRepository.save(propertyValue);
        Property propertyOfPropertyValue = propertyService.findPropertyById(propertyId);


        propertyRepository.save(propertyOfPropertyValue);

        propertyValueReturn.setProperty(propertyOfPropertyValue);
        propertyOfPropertyValue.getPropertyValues().add(propertyValueReturn);


        propertyRepository.save(propertyOfPropertyValue);

        historicService.putPropertyValueHistoric(propertyOfPropertyValue, propertyValueReturn, userId, taskId);

        return PropertyProcessor.getInstance().resolveProperty(propertyOfPropertyValue);
    }

    public Option putPropertyOption(Option newOption, Long userId, Long taskId, Long propertyId) {
        Property property = propertyService.findPropertyById(propertyId);
        historicService.putPropertyOptionHistoric(newOption, userId, taskId, propertyId);
        Option savedOption = optionRepository.save(newOption);
        property.getOptions().add(savedOption);
        propertyRepository.save(property);
        return savedOption;
    }

    private final OptionService optionService;

    public Property deletePropertyOption(Long userId, Long taskId, Long propertyId, Long optionId) {
        Property property = propertyService.findPropertyById(propertyId);
        Option option = optionService.findOptionById(optionId);
        historicService.deletePropertyOptionHistoric(userId, taskId, propertyId, optionId);
        property.getOptions().remove(option);
        optionRepository.delete(option);
        return propertyRepository.save(property);
    }

    public Collection<Property> getAllProperties() {
        return propertyRepository.findAll();
    }

    public UserTask getUserTask(Long userId, Long taskId) {
        return userTaskService.findByUserIdAndTaskId(userId, taskId);
    }

    private final UserTaskService userTaskService;


    public GetTaskDTO patchProperty(Property property, Long taskId) {
        Task task = findTaskById(taskId);
        if (property.getId() == 0) {
            property.setId(null);
        }
        property = propertyRepository.save(property);
        task.getProperties().add(property);

        GetTaskDTO getTaskDTO = new GetTaskDTO();
        PriorityRecord priorityRecord = new PriorityRecord(task.getPriority().name(), task.getPriority().backgroundColor);
        BeanUtils.copyProperties(task, getTaskDTO);
        getTaskDTO.setPriority(priorityRecord);

        taskRepository.save(task);

        return getTaskDTO;
    }


    public Property updatePropertyCurrentOptions(Collection<Option> newCurrentOptions, Long propertyId, Long taskId, Long userId) {
        Property property = propertyService.findPropertyById(propertyId);
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
        Task task = findTaskById(taskId);
        if (property.getOptions() != null) {
            optionRepository.saveAll(property.getOptions());
        }
        property = propertyRepository.save(property);
        task.getProperties().add(property);

        Task savedTask = historicService.patchProperty(property, userId, taskId);

        return converter.convertOne(savedTask);
    }
    private final PropertyService propertyService;

    public GetTaskDTO deleteProperty(Long taskId, Long userId, Long propertyId) {
        Property property = propertyService.findPropertyById(propertyId);

        Task taskSaved = historicService.deletePropertyHistoric(taskId,userId,propertyId);

        taskSaved.getProperties().remove(property);
        propertyRepository.deleteById(propertyId);
        return converter.convertOne(taskRepository.save(taskSaved));
    }

    public Collection<GetUserDTO> patchAssociate(Long taskId, Collection<GetUserDTO> associates, Long userId) {
        Collection<User> newList = new ArrayList<>();
        Task task = findTaskById(taskId);
        associates.forEach(associate -> {
            User user = new User();
            BeanUtils.copyProperties(associate, user);
            newList.add(user);
        });
        task.setAssociates(newList);
        ArrayList<String> currentUsersAssociates = new ArrayList<>();
        associates.forEach(user -> {
            currentUsersAssociates.add(userService.findUserById(userId).getName());
        });

        task = historicService.patchAssociateHistoric(taskId, userId, newList, currentUsersAssociates);

        Converter<GetUserDTO, User> userConverter = new GetUserConverter();
        return userConverter.convertAll(taskRepository.save(task).getAssociates());

    }

    public GetTaskDTO patchCurrentStatus(Long taskId, Long userId, Status status) {
        Task task = findTaskById(taskId);
        User user = userService.findUserById(userId);

        task.setCurrentStatus(status);
        task = historicService.updateCurrentStatusHistoric(user, task, status);
        return converter.convertOne(task);
    }

    public GetTaskDTO updateCurrentPriority(Long taskId, Long userId, PriorityRecord priorityRecord) {
        Task task = findTaskById(taskId);
        User user = userService.findUserById(userId);
        Priority priority = Priority.valueOf(priorityRecord.name());

        task.setPriority(priority);

        task = historicService.updateCurrentPriorityHistoric(task, user, priority);
        System.out.println(task.getPriority());

        return new GetTaskDTO(task);
    }

    public GetTaskDTO findById(Long id) {
        Task task = findTaskById(id);
        GetTaskDTO getTaskDTO = new GetTaskDTO();
        PriorityRecord priorityRecord = new PriorityRecord(task.getPriority().name(), task.getPriority().backgroundColor);
        BeanUtils.copyProperties(task, getTaskDTO);
        getTaskDTO.setPriority(priorityRecord);
        TaskProcessor.getInstance().resolveTask(task);
        return getTaskDTO;
    }

    public void deleteAll(Collection<Task> tasks){
        tasks.forEach(task -> delete(task.getId()));
//        Task task = taskRepository.findById(id).get();
//        return resolveAndGetDTO(task);
    }

    public void delete(Long id) {
        Collection<Property> properties = findTaskById(id).getProperties();
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
        Task task2 = findTaskById(task.getId());
        syncUserTaskTable(task2);


        return converter.convertOne(task2);
    }

    private final ProjectService projectService;

    public void deleteTask(Long taskId) {
        Task task = findTaskById(taskId);
        Project projectOfTask = projectService.findProjectById(task.getProject().getId());

        taskRepository.deleteById(taskId);

        projectOfTask.getTasks().remove(task);
        projectRepository.save(projectOfTask);

    }

    public GetTaskDTO update(PutTaskDTO putTaskDTO, Long userId) {
        Task task = findTaskById(putTaskDTO.getId());
        User userForHistoric = userService.findUserById(userId);

        task = historicService.generalUpdateHistoric(putTaskDTO, task, userForHistoric);

        BeanUtils.copyProperties(putTaskDTO, task);

        setStatusListIndex(task);

        task.setProgress(setProgress(task));

        Task updatedTask = taskRepository.save(task);
        syncUserTaskTable(updatedTask);

        TaskProcessor.getInstance().resolveTask(updatedTask);
        GetTaskDTO getTaskDTO = new GetTaskDTO();
        BeanUtils.copyProperties(updatedTask, getTaskDTO);
        return getTaskDTO;
    }

    public GetTaskDTO patchFile(Long taskId, MultipartFile file, Long userId){
        Task task = historicService.patchFileHistoric(taskId,userId,file);
        task.setFile(file);
        Task updatedTask = taskRepository.save(task);
        return converter.convertOne(updatedTask);
    }

    public void deleteFile(Long taskId, Long fileId, Long userId) {
        AtomicReference<File> newFile = new AtomicReference<>(new File());
        Task task = findTaskById(taskId);

            task.getFiles().forEach(file -> {
                if (file.getId().equals(fileId)) {
                    historicService.deleteFileHistoric(taskId,userId,file);
                     newFile.set(file);
                }
            });

        if(newFile.get() != null) {
            task.getFiles().remove(newFile.get());
        }

        taskRepository.save(task);
    }

    private double setProgress(Task task) {
        if(task.getSubtasks()!=null){
            Collection<Subtask> totalConcludedSubtasks = task.getSubtasks().stream().filter(Subtask::getConcluded).toList();
            double totalSubtasks = task.getSubtasks().size();
            if (totalSubtasks == 0) {
                return 0.0;
            }
            double totalConcluded = totalConcludedSubtasks.size();
            double progress = (totalConcluded / totalSubtasks) * 100;

            return progress;
        }
        return 0;
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
            deleteUserTaskIfUserIsNotAssociate(task);
        }
    }

    private void deleteUserTaskIfUserIsNotAssociate(Task task) {
        userTaskRepository.findAll().stream()
                .filter(userTask -> Objects.equals(userTask.getTaskId(), task.getId()))
                .filter(userTask -> !task.getAssociates().contains(userTask.getUser()))
                .forEach(userTaskRepository::delete);
    }

    private void setStatusListIndex(Task task) {
        Integer defaultIndex = -1;
        Integer firstIndex = 0;
        if (task.getCurrentStatus() != null && task.getStatusListIndex() != null) {
            if (task.getStatusListIndex().equals(defaultIndex)) {
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

    private final UserService userService;

    public Collection<GetTaskDTO> getTasksByUserId(Long userId) {

        User user = userService.findUserById(userId);

        Collection<Task> associatedTasks = taskRepository.getTasksByAssociatesContaining(user);
        Collection<Task> createdTasks = taskRepository.getTasksByCreatorIs(user);

        Collection<GetTaskDTO> getTaskDTOS = new HashSet<>(converter.convertAll(associatedTasks));

        createdTasks.forEach(createdTask -> associatedTasks.forEach(associatedTask -> {
            if (!createdTask.getId().equals(associatedTask.getId())) {
                getTaskDTOS.add(converter.convertOne(createdTask));
            }
        }));
        return getTaskDTOS;
    }

    public Collection<GetTaskDTO> getTasksByProjectId(Long projectId){
        Collection<Task> tasks = taskRepository.findAllByProject_Id(projectId);
        return converter.convertAll(tasks);
    }

    private final StatusService statusService;

    public Collection<GetTaskDTO> getTasksByStatus(Long id) {
        Collection<Task> tasks = taskRepository.getTaskByCurrentStatus(statusService.findStatusById(id));
        return converter.convertAll(tasks);
    }

    public GetTaskDTO updateTaskName(Long taskId, String name) {
        Task task = findTaskById(taskId);
        task.setName(name);
        Task savedTask = taskRepository.save(task);
        return converter.convertOne(savedTask);
    }

    public GetTaskDTO updateTaskFinalDate(Long taskId, Long userId, LocalDateTime newFinalDate) {
        Task task = findTaskById(taskId);
        task.setFinalDate(newFinalDate);
        taskRepository.save(task);
        task = historicService.updateTaskFinalDateHistoric(taskId, userId, newFinalDate);
        return converter.convertOne(task);
    }

    public GetTaskDTO patchSubtask(Subtask subtask, Long taskId, Long userId) {
        Task task = findTaskById(taskId);
        subTaskRepository.save(subtask);
        task.getSubtasks().add(subtask);
        taskRepository.save(task);
        task = historicService.patchSubtaskHistoric(subtask, userId, taskId);
        return converter.convertOne(task);
    }
    private final SubtaskService subtaskService;
    public GetTaskDTO deleteSubtask(Long subtaskId, Long taskId, Long userId) {
        Subtask subtask = subtaskService.findSubtaskById(subtaskId);
        Task task = historicService.deleteSubtaskHistoric(subtaskId, userId, taskId);
        task.getSubtasks().remove(subtask);
        subTaskRepository.delete(subtask);
        return converter.convertOne(task);
    }
}