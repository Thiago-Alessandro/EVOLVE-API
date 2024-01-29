package net.weg.taskmanager.controller;

import lombok.AllArgsConstructor;
import net.weg.taskmanager.model.PermissionOfProperties;
import net.weg.taskmanager.service.PermissionOfPropertieService;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@AllArgsConstructor
@RequestMapping("/permissao/propriedade")
public class PermissionOfPropertiesController {

    private final PermissionOfPropertieService permissionOfPropertieService;

    @GetMapping("/{id}")
    public PermissionOfProperties findById(@PathVariable Integer id){return permissionOfPropertieService.findById(id);}
    @GetMapping
    public Collection<PermissionOfProperties> findAll(){return permissionOfPropertieService.findAll();}
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id){
        permissionOfPropertieService.delete(id);}
    @PostMapping
    public PermissionOfProperties create(@RequestBody PermissionOfProperties permissionOfProperties){return permissionOfPropertieService.create(permissionOfProperties);}
    @PutMapping
    public PermissionOfProperties update(@RequestBody PermissionOfProperties permissionOfProperties){return permissionOfPropertieService.update(permissionOfProperties);}

}
