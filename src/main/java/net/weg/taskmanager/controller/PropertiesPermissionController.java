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

    @GetMapping("/{id}")
    public PropertiesPermission findById(@PathVariable Integer id){return permissionOfPropertieService.findById(id);}
    @GetMapping
    public Collection<PropertiesPermission> findAll(){return permissionOfPropertieService.findAll();}
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id){
        permissionOfPropertieService.delete(id);}
    @PostMapping
    public PropertiesPermission create(@RequestBody PropertiesPermission propertiesPermission){return permissionOfPropertieService.create(propertiesPermission);}
    @PutMapping
    public PropertiesPermission update(@RequestBody PropertiesPermission propertiesPermission){return permissionOfPropertieService.update(propertiesPermission);}

}
