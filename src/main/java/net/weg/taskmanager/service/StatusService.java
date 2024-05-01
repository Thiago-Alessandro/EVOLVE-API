package net.weg.taskmanager.service;

import lombok.AllArgsConstructor;
import net.weg.taskmanager.model.entity.Status;
import net.weg.taskmanager.repository.StatusRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@AllArgsConstructor
public class StatusService {

    private final StatusRepository repository;

    public void deleteAll(Collection<Status> statusList){
        repository.deleteAll(statusList);
    }
    public Status findStatusById(Long statusId){
        Optional<Status> optionalStatus = repository.findById(statusId);
        if (optionalStatus.isEmpty()) throw new NoSuchElementException();
        return optionalStatus.get();
    }

    public void deleteStatusById(Long statusId){
        repository.deleteById(statusId);
    }

}
