package net.weg.taskmanager.service;

import lombok.AllArgsConstructor;
import net.weg.taskmanager.model.Status;
import net.weg.taskmanager.repository.StatusRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@AllArgsConstructor
public class StatusService {

    private final StatusRepository repository;

    public void deleteAll(Collection<Status> statusList){
        repository.deleteAll(statusList);
    }

}
