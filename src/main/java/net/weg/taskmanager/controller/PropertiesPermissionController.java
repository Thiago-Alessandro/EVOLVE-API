package net.weg.taskmanager.controller;

import lombok.AllArgsConstructor;
import net.weg.taskmanager.model.PropertiesPermission;
import net.weg.taskmanager.service.PropertiesPermissionService;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@AllArgsConstructor
@RequestMapping("/permission/property")
public class PropertiesPermissionController {

    private final PropertiesPermissionService permissionOfPropertieService;

    @GetMapping("/{propertyId}")
    public PropertiesPermission findById(@PathVariable Long propertyId){return permissionOfPropertieService.findById(propertyId);}
    @GetMapping
    public Collection<PropertiesPermission> findAll(){return permissionOfPropertieService.findAll();}
    @DeleteMapping("/{propertyId}")
    public void delete(@PathVariable Long propertyId){
        permissionOfPropertieService.delete(propertyId);}
    @PostMapping
    public PropertiesPermission create(@RequestBody PropertiesPermission propertiesPermission){return permissionOfPropertieService.create(propertiesPermission);}
    @PutMapping
    public PropertiesPermission update(@RequestBody PropertiesPermission propertiesPermission){return permissionOfPropertieService.update(propertiesPermission);}

}
