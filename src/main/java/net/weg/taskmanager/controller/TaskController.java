package net.weg.taskmanager.controller;

import com.fasterxml.jackson.databind.annotation.JsonAppend;
import lombok.AllArgsConstructor;

import net.weg.taskmanager.model.dto.get.GetUserDTO;
import net.weg.taskmanager.model.entity.Comment;
import net.weg.taskmanager.model.entity.User;
import net.weg.taskmanager.model.enums.Priority;
import net.weg.taskmanager.model.entity.UserTask;

//OLHAR PARA JUNTAR AS DTOs MAIS TARDE
import net.weg.taskmanager.model.dto.post.PostTaskDTO;
import net.weg.taskmanager.model.dto.get.GetTaskDTO;


import net.weg.taskmanager.model.dto.put.PutTaskDTO;
import net.weg.taskmanager.model.property.Option;
import net.weg.taskmanager.model.property.Property;
import net.weg.taskmanager.model.property.values.PropertyValue;
import net.weg.taskmanager.model.record.PriorityRecord;
import net.weg.taskmanager.service.TaskService;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("/task")
public class TaskController {

    private final TaskService taskService;

    @GetMapping("/{id}")

    public GetTaskDTO findById(@PathVariable Long id){return taskService.findById(id);}

    @GetMapping
    public Collection<GetTaskDTO> findAll(){return taskService.findAll();}

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        taskService.delete(id);}


    @PostMapping
    public GetTaskDTO create(@RequestBody PostTaskDTO postTaskDTO){
        return taskService.create(postTaskDTO);}

    @PutMapping("/{userId}")
    public GetTaskDTO update(@RequestBody PutTaskDTO putTaskDTO,@PathVariable Long userId){
//        GetTaskDTO getTaskDTO = taskService.update(putTaskDTO);
        return taskService.update(putTaskDTO,userId);
    }

    @GetMapping("/status/{id}")
    public Collection<GetTaskDTO> getTasksByStatus(@PathVariable Long id){return taskService.getTasksByStatus(id);}

    @PatchMapping("/userTask")
    public UserTask setWorkedTime(@RequestBody UserTask userTask){
        return taskService.setWorkedTime(userTask);
    }

    @PatchMapping("/property/{taskId}/{userId}")
    public GetTaskDTO patchProperty(@RequestBody Property property, @PathVariable Long taskId, @PathVariable Long userId) {
        return taskService.patchProperty(property,taskId, userId);
    }

    @PatchMapping("property/associates/{taskId}/{userId}")
    public Collection<GetUserDTO> patchAssociate(@PathVariable Long taskId, @RequestBody Collection<User> associates, @PathVariable Long userId) {
        return taskService.patchAssociate(taskId,associates,userId);
    }

    @GetMapping("/userTask/{userId}/{taskId}")
    public UserTask getUserTask(@PathVariable Long userId, @PathVariable Long taskId){
        return taskService.getUserTask(userId, taskId);
    }

    @PutMapping("/property/put/{propertyId}/{userId}/{taskId}")
    public Property putPropertyValue(@PathVariable Long propertyId,
                                     @RequestBody PropertyValue propertyValue,
                                     @PathVariable Long userId,
                                     @PathVariable Long taskId) {
        Property propertyOfPropertyValue = taskService.putPropertyValue(propertyValue, propertyId, userId, taskId);
        return propertyOfPropertyValue;
    }

    @PutMapping("/property/put/option/{userId}")
    public Option putPropertyOption(@RequestBody Option newOption, @PathVariable Long userId) {
        return taskService.putPropertyOption(newOption,userId);
    }

    @GetMapping("/property/get/getall")
    public Collection<Property> getAllProperties() {
        return taskService.getAllProperties();
    }

    @GetMapping("/priorities")
    public Collection<PriorityRecord> getAllPriorities() {
        List<Priority> listTest =  List.of(Priority.values());
       return listTest.stream().map(priority -> new PriorityRecord(priority.name(), priority.backgroundColor)).collect(Collectors.toList());
    }

    @GetMapping("/user/{userId}")
    public Collection<GetTaskDTO> getTasksByUserId(@PathVariable Long userId){
        return taskService.getTasksByUserId(userId);
    }

    @GetMapping("/comments/getAll/{taskId}")
    public Collection<Comment> getAllCommentsOfTask(@PathVariable Long taskId) {
        return taskService.getAllCommentsOfTask(taskId);
    }

    @PatchMapping("/comments/patch/{taskId}/{userId}")
    public Comment patchNewComment(@PathVariable Long taskId,
                                   @RequestBody Comment newComment, @PathVariable Long userId) {
        return taskService.patchNewComment(taskId, newComment, userId);
    }

    @DeleteMapping("/comments/delete/{commentId}/{taskId}/{userId}")
    public Collection<Comment> deleteComment(@PathVariable Long commentId,
                              @PathVariable Long taskId, @PathVariable Long userId) {
        return taskService.deleteComment(commentId,taskId,userId);
    }
}
