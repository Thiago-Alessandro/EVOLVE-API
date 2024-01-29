package net.weg.taskmanager.controller;

import lombok.AllArgsConstructor;
import net.weg.taskmanager.model.property.Property;
import net.weg.taskmanager.service.PropertyService;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@AllArgsConstructor
@RequestMapping("/propriedade")
public class PropertyController {

    private final PropertyService propertyService;

    @GetMapping("/{id}")
    public Property findById(@PathVariable Integer id){return propertyService.findById(id);}
    @GetMapping
    public Collection<Property> findAll(){return propertyService.findAll();}
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id){
        propertyService.delete(id);}
    @PostMapping
    public Property create(@RequestBody Property propriedade){return propertyService.create(propriedade);}
    @PutMapping
    public Property update(@RequestBody Property propriedade){return propertyService.update(propriedade);}

}
