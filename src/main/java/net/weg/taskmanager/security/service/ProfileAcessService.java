package net.weg.taskmanager.security.service;

import lombok.AllArgsConstructor;
import net.weg.taskmanager.security.model.entity.Role;
import net.weg.taskmanager.security.repository.ProfileAcessRepository;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProfileAcessService {

    private final ProfileAcessRepository profileAcessRepository;

    public Role getProfileAcessByName(String name){
        return profileAcessRepository.findByName(name);
    }

}
