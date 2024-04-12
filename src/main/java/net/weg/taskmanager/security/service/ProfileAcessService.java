package net.weg.taskmanager.security.service;

import lombok.AllArgsConstructor;
import net.weg.taskmanager.security.model.entity.ProfileAcess;
import net.weg.taskmanager.security.repository.ProfileAcessRepository;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProfileAcessService {

    private final ProfileAcessRepository profileAcessRepository;

    public ProfileAcess getProfileAcessByName(String name){
        return profileAcessRepository.findByName(name);
    }

}
