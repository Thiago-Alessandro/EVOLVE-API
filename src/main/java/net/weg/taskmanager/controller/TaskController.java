package net.weg.taskmanager.controller;

import lombok.AllArgsConstructor;
import net.weg.taskmanager.model.Task;
import net.weg.taskmanager.model.User;
import net.weg.taskmanager.model.UserTask;
import net.weg.taskmanager.model.dto.post.PostTaskDTO;
import net.weg.taskmanager.service.TaskService;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@AllArgsConstructor
@RequestMapping("/task")
public class TaskController {

    private final TaskService taskService;

    @GetMapping("/{id}")
    public Task findById(@PathVariable Long id){

        return taskService.findById(id);}
    @GetMapping
    public Collection<Task> findAll(){return taskService.findAll();}
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        taskService.delete(id);}
    @PostMapping
    public Task create(@RequestBody PostTaskDTO task){
        System.out.println("Controller yay");
        return taskService.create(task);}
    @PutMapping
    public Task update(@RequestBody Task task){return taskService.update(task);}

    @GetMapping("/status/{id}")
    public Collection<Task> getTasksByStatus(@PathVariable Long id){return taskService.getTasksByStatus(id);}

    @PatchMapping("/userTask")
    public UserTask setWorkedTime(@RequestBody UserTask userTask){
        return taskService.setWorkedTime(userTask);
    }

    @GetMapping("/userTask/{userId}/{taskId}")
    public UserTask getUserTask(@PathVariable Long userId, @PathVariable Long taskId){
        return taskService.getUserTask(userId, taskId);
    }

}
