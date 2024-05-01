package net.weg.taskmanager.security.service;

import lombok.AllArgsConstructor;
import net.weg.taskmanager.security.model.entity.Role;
import net.weg.taskmanager.security.repository.RoleRepository;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    public Role getRoleByName(String name){
        return roleRepository.findByName(name);
    }

}
