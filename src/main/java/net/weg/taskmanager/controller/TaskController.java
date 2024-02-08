package net.weg.taskmanager.controller;

import lombok.AllArgsConstructor;
import net.weg.taskmanager.model.Task;
import net.weg.taskmanager.model.User;
import net.weg.taskmanager.model.UserTask;
import net.weg.taskmanager.model.property.TaskProjectProperty;
import net.weg.taskmanager.service.TaskService;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@AllArgsConstructor
@RequestMapping("/task")
public class TaskController {

    private final TaskService taskService;

    @GetMapping("/{id}")
    public Task findById(@PathVariable Integer id){return taskService.findById(id);}
    @GetMapping
    public Collection<Task> findAll(){return taskService.findAll();}
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id){
        taskService.delete(id);}
    @PostMapping
    public Task create(@RequestBody Task task){
        System.out.println("Controller yay");
        return taskService.create(task);}
    @PutMapping
    public Task update(@RequestBody Task task){return taskService.update(task);}

    @GetMapping("/status/{id}")
    public Collection<Task> getTasksByStatus(@PathVariable Integer id){return taskService.getTasksByStatus(id);}

    @PatchMapping("/userTask")
    public UserTask setWorkedTime(@RequestBody UserTask userTask){
        return taskService.setWorkedTime(userTask);
    }

    @PatchMapping("/property/{taskId}")
    public Task patchProperty(@RequestBody TaskProjectProperty taskProjectProperty,@PathVariable Integer taskId) {return taskService.patchProperty(taskProjectProperty,taskId);}

    @GetMapping("/userTask/{userId}/{taskId}")
    public UserTask getUserTask(@PathVariable Integer userId, @PathVariable Integer taskId){
        return taskService.getUserTask(userId, taskId);
    }

}
