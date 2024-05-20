package net.weg.taskmanager.security.service;

import lombok.AllArgsConstructor;
import net.weg.taskmanager.security.model.entity.Role;
import net.weg.taskmanager.security.repository.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@AllArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    public Role getRoleByName(String name){
        return roleRepository.findByName(name);
    }
    public Role findRoleById(Long roleId){
        Optional<Role> optionalRole = roleRepository.findById(roleId);
        if (optionalRole.isEmpty()) throw new NoSuchElementException("Could not find role with id: " + roleId);
        return optionalRole.get();
    }

}
