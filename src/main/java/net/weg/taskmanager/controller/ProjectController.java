package net.weg.taskmanager.controller;

import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.AllArgsConstructor;
import net.weg.taskmanager.model.UserProject;
import net.weg.taskmanager.model.entity.Status;
import net.weg.taskmanager.model.dto.get.GetProjectDTO;
import net.weg.taskmanager.model.dto.post.PostProjectDTO;
import net.weg.taskmanager.model.entity.Task;
import net.weg.taskmanager.model.property.Property;
import net.weg.taskmanager.security.model.entity.Role;
import net.weg.taskmanager.service.ProjectService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.NoSuchElementException;

@RestController
@AllArgsConstructor
@RequestMapping("/project")
public class ProjectController {

    private final ProjectService projectService;

    @GetMapping("/{projectId}")
    public ResponseEntity<GetProjectDTO> findById(@PathVariable Long projectId) {
        try{
            return ResponseEntity.ok(projectService.findById(projectId));
        } catch (NoSuchElementException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/team/{teamId}")
    public ResponseEntity<Collection<GetProjectDTO>> findByTeamId(@PathVariable Long teamId) {
        try{
            return ResponseEntity.ok(projectService.findByTeamId(teamId));
        } catch (NoSuchElementException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/user/{userId}")
    public ResponseEntity<Collection<GetProjectDTO>> findByUserId(@PathVariable Long userId) {
        try{
            return ResponseEntity.ok(projectService.findByUserId(userId));
        } catch (NoSuchElementException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }



//    @GetMapping
//    public Collection<GetProjectDTO> findAll() {
//        return projectService.findAll();
//    }

    @DeleteMapping("/{projectId}")
    public void delete(@PathVariable Long projectId) {
        projectService.delete(projectId);
    }

    @PostMapping("/team/{teamId}")
    public GetProjectDTO create(@RequestBody PostProjectDTO project) {
        return projectService.create(project);
    }

//    @PutMapping
//    public GetProjectDTO update(@RequestBody PutProjectDTO project) {
//        return projectService.update(project);
//    }

    @PatchMapping("/{projectId}/name")
    public ResponseEntity<GetProjectDTO> patchName(@PathVariable Long projectId, @RequestParam String name){
        try {
            return ResponseEntity.ok(projectService.patchName(projectId, name));
        } catch (Exception e ){
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }
    }

    @PatchMapping("/{projectId}/description")
    public ResponseEntity<GetProjectDTO> patchDescription(@PathVariable Long projectId, @RequestParam String description){
        try {
            return ResponseEntity.ok(projectService.patchDescription(projectId, description));
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }
    }

    @PatchMapping("/{projectId}/image")
    public ResponseEntity<GetProjectDTO> patchImage(@PathVariable Long projectId, @RequestParam MultipartFile image){
        try {
            return ResponseEntity.ok(projectService.patchImage(projectId, image));
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }
    }

    @PatchMapping("/{projectId}/image/remove")
    public ResponseEntity<GetProjectDTO> patchImageRemove(@PathVariable Long projectId){
        try {
            return ResponseEntity.ok(projectService.patchImageRemove(projectId));
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }
    }

    @PatchMapping("/{projectId}/imageColor")
    public ResponseEntity<GetProjectDTO> patchImageColor(@PathVariable Long projectId, @RequestParam String imageColor){
        try {
            return ResponseEntity.ok(projectService.patchImageColor(projectId, imageColor));
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }
    }

    @PatchMapping("/{projectId}/finalDate")
    public ResponseEntity<GetProjectDTO> patchFinalDate(@PathVariable Long projectId, @RequestParam LocalDateTime finalDate){
        try {
            return ResponseEntity.ok(projectService.patchFinalDate(projectId, finalDate));
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }
    }

    @PatchMapping("/{projectId}/properties")
    public ResponseEntity<GetProjectDTO> patchProperties(@PathVariable Long projectId, @RequestParam Collection<Property> properties){
        try {
            return ResponseEntity.ok(projectService.patchProperties(projectId, properties));
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }
    }

    @PatchMapping("/{projectId}/statusList")
    public ResponseEntity<GetProjectDTO> patchStatusList(@PathVariable Long projectId, @RequestBody Collection<Status> statusList)  {
        try {
            return ResponseEntity.ok(projectService.patchStatusList(projectId, statusList));
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }
    }

    @PatchMapping("/{projectId}/statusList/remove/{statusId}")
    public ResponseEntity<GetProjectDTO> patchStatusListRemove(@PathVariable Long projectId, @PathVariable Long statusId) {
        try{
            return ResponseEntity.ok(projectService.patchStatusListRemove(projectId, statusId));
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }
    }

    @PatchMapping("/{projectId}/members")
    public ResponseEntity<GetProjectDTO> patchMembers(@PathVariable Long projectId, @RequestBody Collection<UserProject> members){
        try {
            return ResponseEntity.ok(projectService.patchMembers(projectId, members));
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }
    }

    @PatchMapping("/{projectId}/tasks")
    public ResponseEntity<GetProjectDTO> patchTasks(@PathVariable Long projectId, @RequestBody Collection<Task> tasks){
        try {
            return ResponseEntity.ok(projectService.patchTasks(projectId, tasks));
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }
    }

    @PatchMapping("/{projectId}/tasks/remove/{taskId}")
    public ResponseEntity<GetProjectDTO> patchTasksRemove(@PathVariable Long projectId, @PathVariable Long taskId){
        try {
            return ResponseEntity.ok(projectService.patchTasksRemove(projectId, taskId));
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }
    }

    @PatchMapping("/{projectId}/defaultRole")
    public ResponseEntity<GetProjectDTO> patchDefaultRole(@PathVariable Long projectId, @RequestBody Role defaultRole){
        try {
            return ResponseEntity.ok(projectService.patchDefaultRole(projectId, defaultRole));
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }
    }

}
