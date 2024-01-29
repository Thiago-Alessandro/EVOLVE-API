package net.weg.taskmanager.service;

import lombok.AllArgsConstructor;
import net.weg.taskmanager.model.PermissionOfProperties;
import net.weg.taskmanager.repository.PermissionOfPropertiesRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@AllArgsConstructor
public class PermissionOfPropertieService {

    private final PermissionOfPropertiesRepository permissionOfPropertiesRepository;

    public PermissionOfProperties findById(Integer id){return permissionOfPropertiesRepository.findById(id).get();}

    public Collection<PermissionOfProperties> findAll(){return permissionOfPropertiesRepository.findAll();}

    public void delete(Integer id){
        permissionOfPropertiesRepository.deleteById(id);}

    public PermissionOfProperties create(PermissionOfProperties permissionOfProperties){return permissionOfPropertiesRepository.save(permissionOfProperties);}
    public PermissionOfProperties update(PermissionOfProperties permissionOfProperties){return permissionOfPropertiesRepository.save(permissionOfProperties);}

}
