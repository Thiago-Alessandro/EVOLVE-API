package net.weg.taskmanager.service;

import lombok.AllArgsConstructor;
import net.weg.taskmanager.model.entity.UserTask;
import net.weg.taskmanager.repository.UserTaskRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserTaskService {

    private final UserTaskRepository repository;

    public UserTask findByUserIdAndTaskId(Long userId, Long taskId) {
        Optional<UserTask> optionalUserTask = repository.findByUserIdAndTaskId(userId, taskId);
        if(optionalUserTask.isEmpty()) throw new NoSuchElementException("UserTask not found");
        return optionalUserTask.get();
    }

}
