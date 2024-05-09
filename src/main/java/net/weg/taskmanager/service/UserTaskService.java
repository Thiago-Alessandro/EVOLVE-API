package net.weg.taskmanager.service;

import lombok.AllArgsConstructor;
import net.weg.taskmanager.model.dto.get.GetUserTaskDTO;
import net.weg.taskmanager.model.entity.UserTask;
import net.weg.taskmanager.repository.UserTaskRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;
@AllArgsConstructor
@Service
public class UserTaskService {
    private final UserTaskRepository userTaskRepository;

    public UserTask findById(Long id) {
        return null;
    }

    public Collection<UserTask> findAll() {
        return null;
    }

    public GetUserTaskDTO getUserWorkedTime(Long userId, Long taskId) {
        return new GetUserTaskDTO(this.userTaskRepository.findByUserIdAndTaskId(userId,taskId));
    }

    public void updateWorkedTime(UserTask userTask) {
        this.userTaskRepository.save(userTask);
    }

    public void delete(Long id) {

    }

    public UserTask create(UserTask obj) {
        return null;
    }


    public UserTask update(UserTask obj) {
        return null;
    }
}
