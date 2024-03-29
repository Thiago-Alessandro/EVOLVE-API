package net.weg.taskmanager.controller;

import lombok.AllArgsConstructor;
import net.weg.taskmanager.model.entity.Status;
import net.weg.taskmanager.model.dto.get.GetProjectDTO;
import net.weg.taskmanager.model.dto.post.PostProjectDTO;
import net.weg.taskmanager.model.dto.put.PutProjectDTO;
import net.weg.taskmanager.service.ProjectService;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@AllArgsConstructor
@RequestMapping("/project")
@CrossOrigin
public class ProjectController {

    private final ProjectService projectService;

    @GetMapping("/{id}")
    public GetProjectDTO findById(@PathVariable Long id){return projectService.findById(id);}
    @GetMapping
    public Collection<GetProjectDTO> findAll(){return projectService.findAll();}
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        projectService.delete(id);}
    @PostMapping
    public GetProjectDTO create(@RequestBody PostProjectDTO project){return projectService.create(project);}
    @PutMapping
    public GetProjectDTO update(@RequestBody PutProjectDTO project){return projectService.update(project);}
//    @GetMapping("/status")
//    public Collection<Status> getStatus(){return projetoService.getAllStatus();}

    @PatchMapping("/{projectId}")
    public GetProjectDTO updateStatusList(@PathVariable Long projectId, @RequestBody Status status){
        return projectService.updateStatusList(projectId, status);
    }

    @GetMapping("/user/{userId}")
    public Collection<GetProjectDTO> getProjectsByUserId(@PathVariable Long userId){
        return projectService.getProjectsByUserId(userId);
    }

}
