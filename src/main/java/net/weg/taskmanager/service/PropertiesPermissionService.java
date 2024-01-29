package net.weg.taskmanager.service;

import lombok.AllArgsConstructor;
import net.weg.taskmanager.model.PropertiesPermission;
import net.weg.taskmanager.repository.PropertiesPermissionRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@AllArgsConstructor
public class PropertiesPermissionService {

    private final PropertiesPermissionRepository permissionOfPropertiesRepository;

    public PropertiesPermission findById(Integer id){return permissionOfPropertiesRepository.findById(id).get();}

    public Collection<PropertiesPermission> findAll(){return permissionOfPropertiesRepository.findAll();}

    public void delete(Integer id){
        permissionOfPropertiesRepository.deleteById(id);}

    public PropertiesPermission create(PropertiesPermission permissionOfProperties){return permissionOfPropertiesRepository.save(permissionOfProperties);}
    public PropertiesPermission update(PropertiesPermission permissionOfProperties){return permissionOfPropertiesRepository.save(permissionOfProperties);}

}
