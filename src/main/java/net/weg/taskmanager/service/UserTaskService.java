package net.weg.taskmanager.service;

import lombok.AllArgsConstructor;
import net.weg.taskmanager.model.dto.get.GetUserTaskDTO;
import net.weg.taskmanager.model.entity.User;
import net.weg.taskmanager.model.entity.UserTask;
import net.weg.taskmanager.repository.TaskRepository;
import net.weg.taskmanager.repository.UserRepository;
import net.weg.taskmanager.repository.UserTaskRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
public class UserTaskService {

    private final UserTaskRepository repository;
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;

    public UserTask findByUserIdAndTaskId(Long userId, Long taskId) {
        Optional<UserTask> optionalUserTask = repository.findByUserIdAndTaskId(userId, taskId);
        if(optionalUserTask.isEmpty()) throw new NoSuchElementException("UserTask not found");
        return optionalUserTask.get();
    }

    public Collection<UserTask> findAllByUserId(Long userId) {
        Collection<UserTask> userTaskCollection = repository.findAllByUserId(userId);
        if(userTaskCollection.isEmpty()) throw new NoSuchElementException("UserTask not found");
        return userTaskCollection;
    }

    public GetUserTaskDTO getUserWorkedTime(Long userId, Long taskId) {
        Optional<UserTask> optionalUserTask = repository.findByUserIdAndTaskId(userId,taskId);
        if(optionalUserTask.isEmpty()) throw new NoSuchElementException("UserTask not found");
        return new GetUserTaskDTO(optionalUserTask.get());
    }

    public void updateWorkedTime(UserTask userTask) {
        userTask.setUser(userRepository.findById(userTask.getUserId()).get());
        System.out.println(userTask);
        repository.save(userTask);
    }

    public Collection<GetUserTaskDTO> getAllWorkedTime(Long userId, Long projectId) {
        Collection<UserTask> userTaskCollection = repository.findAllByUserId(userId);
        if(userTaskCollection.isEmpty()) throw new NoSuchElementException("UserTask not found");
        Collection<GetUserTaskDTO> userTaskDTOCollection = new ArrayList<>();
        userTaskCollection.forEach(userTask -> {
            if(Objects.equals(userTask.getTask().getProject().getId(), projectId)) {
                userTaskDTOCollection.add(new GetUserTaskDTO(userTask));
            }
        });
        return userTaskDTOCollection;
    }

}
