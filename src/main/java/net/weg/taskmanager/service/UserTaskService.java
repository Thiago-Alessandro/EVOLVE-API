package net.weg.taskmanager.service;

import lombok.AllArgsConstructor;
import net.weg.taskmanager.model.dto.get.GetUserTaskDTO;
import net.weg.taskmanager.model.entity.UserTask;
import net.weg.taskmanager.repository.TaskRepository;
import net.weg.taskmanager.repository.UserRepository;
import net.weg.taskmanager.repository.UserTaskRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.Optional;

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

}
