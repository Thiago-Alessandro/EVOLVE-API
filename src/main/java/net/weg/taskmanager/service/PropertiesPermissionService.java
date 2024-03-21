package net.weg.taskmanager.service;

import lombok.AllArgsConstructor;
import net.weg.taskmanager.model.entity.PropertiesPermission;
import net.weg.taskmanager.repository.PropertiesPermissionRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@AllArgsConstructor
public class PropertiesPermissionService {

    private final PropertiesPermissionRepository permissionOfPropertiesRepository;

    public PropertiesPermission findById(Long id){return permissionOfPropertiesRepository.findById(id).get();}

    public Collection<PropertiesPermission> findAll(){return permissionOfPropertiesRepository.findAll();}

    public void delete(Long id){
        permissionOfPropertiesRepository.deleteById(id);}

    public PropertiesPermission create(PropertiesPermission permissionOfProperties){return permissionOfPropertiesRepository.save(permissionOfProperties);}
    public PropertiesPermission update(PropertiesPermission permissionOfProperties){return permissionOfPropertiesRepository.save(permissionOfProperties);}

}
