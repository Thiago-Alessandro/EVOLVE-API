package net.weg.taskmanager.controller;

import lombok.AllArgsConstructor;
import net.weg.taskmanager.model.Priority;
import net.weg.taskmanager.model.Task;
import net.weg.taskmanager.model.UserTask;
import net.weg.taskmanager.model.dto.TaskDTO;
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

    @GetMapping("/{id}")
    public TaskDTO findById(@PathVariable Integer id){return taskService.findById(id);}
    @GetMapping
    public Collection<TaskDTO> findAll(){return taskService.findAll();}
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id){
        taskService.delete(id);}
    @PostMapping
    public Task create(@RequestBody TaskDTO taskDTO){
        return taskService.create(taskDTO);}
    @PutMapping
    public Task update(@RequestBody TaskDTO taskDTO){return taskService.update(taskDTO);}

    @GetMapping("/status/{id}")
    public Collection<Task> getTasksByStatus(@PathVariable Integer id){return taskService.getTasksByStatus(id);}

    @PatchMapping("/userTask")
    public UserTask setWorkedTime(@RequestBody UserTask userTask){
        return taskService.setWorkedTime(userTask);
    }

//    @PatchMapping("/property/{taskId}")
//    public Task patchProperty(@RequestBody Property property, @PathVariable Integer taskId) {
//        return taskService.patchProperty(property,taskId);
//    }

    @GetMapping("/userTask/{userId}/{taskId}")
    public UserTask getUserTask(@PathVariable Integer userId, @PathVariable Integer taskId){
        return taskService.getUserTask(userId, taskId);
    }

//    @PutMapping("/property/{propertyId}")
//    public Property putPropertyValue(@PathVariable Integer propertyId, @RequestBody Property propertyValue) {
//        return taskService.putPropertyValue(propertyId, propertyValue);
//    }

    @GetMapping("/priorities")
    public Collection<PriorityRecord> getAllPriorities() {
        List<Priority> listTest =  List.of(Priority.values());
       return listTest.stream().map(priority -> new PriorityRecord(priority.name(), priority.backgroundColor)).collect(Collectors.toList());
    }

}
