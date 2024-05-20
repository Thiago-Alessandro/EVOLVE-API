package net.weg.taskmanager.service;

import lombok.AllArgsConstructor;
import net.weg.taskmanager.model.entity.Subtask;
import net.weg.taskmanager.repository.SubTaskRepository;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@AllArgsConstructor
@Service
public class SubtaskService {

    private final SubTaskRepository repository;

    public Subtask findSubtaskById(Long subtaskId){
        Optional<Subtask> optionalSubtask = repository.findById(subtaskId);
        if(optionalSubtask.isEmpty()) throw new NoSuchElementException("Subtask not found");
        return optionalSubtask.get();
    }

}
