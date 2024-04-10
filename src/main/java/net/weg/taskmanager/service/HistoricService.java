package net.weg.taskmanager.service;

import lombok.AllArgsConstructor;
import net.weg.taskmanager.model.entity.Historic;
import net.weg.taskmanager.model.entity.Task;
import net.weg.taskmanager.model.entity.User;
import net.weg.taskmanager.repository.HistoricRepository;
import net.weg.taskmanager.repository.TaskRepository;
import net.weg.taskmanager.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class HistoricService {
    private final HistoricRepository historicRepository;
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;

    public Task patchNewCommentHistoric(Long userId, Long taskId) {
        System.out.println(userId);
        System.out.println(taskId);

        User userForHistoric = userRepository.findById(userId).get();
        Task taskForHistoric = taskRepository.findById(taskId).get();

        Historic historic = new Historic(
                userForHistoric,
                userForHistoric.getName() + " adicionou um comentário",
                LocalDateTime.now()
        );

        Historic savedHistoric = this.historicRepository.save(historic);

        taskForHistoric.getHistoric().add(savedHistoric);

        return taskRepository.save(taskForHistoric);
    }
}
