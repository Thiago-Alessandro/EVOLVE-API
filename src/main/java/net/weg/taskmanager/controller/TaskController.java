package net.weg.taskmanager.controller;

import lombok.AllArgsConstructor;
import net.weg.taskmanager.model.Priority;
import net.weg.taskmanager.model.Task;
import net.weg.taskmanager.model.UserTask;

//OLHAR PARA JUNTAR AS DTOs MAIS TARDE
import net.weg.taskmanager.model.dto.post.PostTaskDTO;
import net.weg.taskmanager.model.dto.get.GetTaskDTO;


import net.weg.taskmanager.model.dto.put.PutTaskDTO;
import net.weg.taskmanager.model.property.values.PropertyValue;
import net.weg.taskmanager.model.record.PriorityRecord;
import net.weg.taskmanager.model.property.Property;
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

    @GetMapping("/{projectId}/{taskId}")
    public GetTaskDTO findById(@PathVariable Long projectId, @PathVariable Long taskId) {
        return taskService.findById(taskId);
    }

//    @GetMapping
//    public Collection<GetTaskDTO> findAll(){return taskService.findAll();}

    @DeleteMapping("/{projectId}/{taskId}")
    public void delete(@PathVariable Long projectId, @PathVariable Long taskId) {
        taskService.delete(taskId);
    }

    @PostMapping("/{projectId}")
    public GetTaskDTO create(@PathVariable Long projectId, @RequestBody PostTaskDTO postTaskDTO) {
//        System.out.println("postTaskDTO");
//        System.out.println(postTaskDTO.getProject().getId());
        return taskService.create(postTaskDTO);
    }

    @PutMapping("/{projectId}")
    public GetTaskDTO update(@PathVariable Long projectId, @RequestBody PutTaskDTO putTaskDTO) {
        return taskService.update(putTaskDTO);
    }

    @GetMapping("/{projectId}/status/{statusId}")
    public Collection<GetTaskDTO> getTasksByStatus(@PathVariable Long projectId, @PathVariable Long statusId) {
        return taskService.getTasksByStatus(statusId);
    }

    @PatchMapping("/userTask")
    public UserTask setWorkedTime(@RequestBody UserTask userTask) {
        return taskService.setWorkedTime(userTask);
    }

    @PatchMapping("/{projectId}/property/{taskId}")
    public GetTaskDTO patchProperty(@PathVariable Long projectId, @RequestBody Property property, @PathVariable Long taskId) {
        return taskService.patchProperty(property, taskId);
    }

    @GetMapping("/{projectId}/userTask/{userId}/{taskId}")
    public UserTask getUserTask(@PathVariable Long projectId, @PathVariable Long userId, @PathVariable Long taskId) {
        return taskService.getUserTask(userId, taskId);
    }

    @PutMapping("/{projectId}/putProperty")
    public PropertyValue putPropertyValue(@RequestBody PropertyValue propertyValue) {
        return taskService.putPropertyValue(propertyValue);
    }

    @GetMapping("/{projectId}/priorities")
    public Collection<PriorityRecord> getAllPriorities(@PathVariable Long projectId) {
        List<Priority> listTest = List.of(Priority.values());
        return listTest.stream().map(priority -> new PriorityRecord(priority.name(), priority.backgroundColor)).collect(Collectors.toList());
    }

}
