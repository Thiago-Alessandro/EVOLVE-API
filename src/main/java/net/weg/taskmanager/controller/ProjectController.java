package net.weg.taskmanager.controller;

import lombok.AllArgsConstructor;
import net.weg.taskmanager.model.Project;
import net.weg.taskmanager.service.ProjectService;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@AllArgsConstructor
@RequestMapping("/projeto")
public class ProjectController {

    private final ProjectService projectService;

    @GetMapping("/{id}")
    public Project findById(@PathVariable Integer id){return projectService.findById(id);}
    @GetMapping
    public Collection<Project> findAll(){return projectService.findAll();}
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id){
        projectService.delete(id);}
    @PostMapping
    public Project create(@RequestBody Project project){return projectService.create(project);}
    @PutMapping
    public Project update(@RequestBody Project project){return projectService.update(project);}
//    @GetMapping("/status")
//    public Collection<Status> getStatus(){return projetoService.getAllStatus();}

}
