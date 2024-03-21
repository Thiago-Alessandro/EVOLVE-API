package net.weg.taskmanager.controller;

import lombok.AllArgsConstructor;

import net.weg.taskmanager.model.enums.Priority;
import net.weg.taskmanager.model.entity.UserTask;

//OLHAR PARA JUNTAR AS DTOs MAIS TARDE
import net.weg.taskmanager.model.dto.post.PostTaskDTO;
import net.weg.taskmanager.model.dto.get.GetTaskDTO;


import net.weg.taskmanager.model.dto.put.PutTaskDTO;
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
        System.out.println("postTaskDTO");
        System.out.println(postTaskDTO.getProject().getId());
        return taskService.create(postTaskDTO);}

    @PutMapping
    public GetTaskDTO update(@RequestBody PutTaskDTO putTaskDTO){
        GetTaskDTO getTaskDTO = taskService.update(putTaskDTO);
        return taskService.update(putTaskDTO);
    }

    @GetMapping("/status/{id}")
    public Collection<GetTaskDTO> getTasksByStatus(@PathVariable Long id){return taskService.getTasksByStatus(id);}

    @PatchMapping("/userTask")
    public UserTask setWorkedTime(@RequestBody UserTask userTask){
        return taskService.setWorkedTime(userTask);
    }

    @PatchMapping("/property/{taskId}")
    public GetTaskDTO patchProperty(@RequestBody net.weg.taskmanager.model.property.Property property, @PathVariable Long taskId) {
        return taskService.patchProperty(property,taskId);
    }

    @GetMapping("/userTask/{userId}/{taskId}")
    public UserTask getUserTask(@PathVariable Long userId, @PathVariable Long taskId){
        return taskService.getUserTask(userId, taskId);
    }

    @PutMapping("/property/put/{propertyId}")
    public Property putPropertyValue(@PathVariable Long propertyId,
                                     @RequestBody PropertyValue propertyValue) {
        Property propertyOfPropertyValue = taskService.putPropertyValue(propertyValue, propertyId);
        return propertyOfPropertyValue;
    }

    @GetMapping("/property/get/getall")
    public Collection<net.weg.taskmanager.model.property.Property> getAllProperties() {
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

}
