package net.weg.taskmanager.controller;

import lombok.AllArgsConstructor;

import net.weg.taskmanager.model.dto.get.GetUserDTO;
import net.weg.taskmanager.model.entity.*;
import net.weg.taskmanager.model.enums.Priority;

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
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("/task")
public class TaskController {

    private final TaskService taskService;

    @GetMapping("/{taskId}")//security nao implementado. Esse metodo é usado?
    public GetTaskDTO findById(@PathVariable Long taskId) {
        return taskService.findById(taskId);
    }

    @GetMapping("/{projectId}/status/{statusId}") //onde é usado? metodo semm cobertura do security
    public Collection<GetTaskDTO> getTasksByStatus(@PathVariable Long statusId) {
        return taskService.getTasksByStatus(statusId);
    }

    @GetMapping("/user/{userId}")
    public Collection<GetTaskDTO> getTasksByUserId(@PathVariable Long userId){
        return taskService.getTasksByUserId(userId);
    }

    @GetMapping("/project/{projectId}")
    public Collection<GetTaskDTO> getTasksByProjectId(@PathVariable Long projectId){
        return taskService.getTasksByProjectId(projectId);
    }

    @PostMapping("/project/{projectId}")
    public GetTaskDTO create(@RequestBody PostTaskDTO postTaskDTO){
        return taskService.create(postTaskDTO);}

    @PutMapping("/{userId}")
    public GetTaskDTO update(@RequestBody PutTaskDTO putTaskDTO,@PathVariable Long userId){
        return taskService.update(putTaskDTO,userId);
    } //isso aqui é para continuar existindo?

    @DeleteMapping("/{taskId}")
    public void delete(@PathVariable Long taskId) {
        taskService.delete(taskId);
    }

//    @PostMapping("/{projectId}")
//    public GetTaskDTO create(@PathVariable Long projectId, @RequestBody PostTaskDTO postTaskDTO) {
//        return taskService.create(postTaskDTO);
//    }

//    @PutMapping("/{projectId}")
//    public GetTaskDTO update(@PathVariable Long projectId, @RequestBody PutTaskDTO putTaskDTO) {
//        return taskService.update(putTaskDTO);
//    }

    @PatchMapping("/userTask")
    public UserTask setWorkedTime(@RequestBody UserTask userTask) {
        return taskService.setWorkedTime(userTask);
    }


    @PatchMapping("/{taskId}/{projectId}/property")
    public GetTaskDTO patchProperty(@PathVariable Long projectId, @RequestBody Property property, @PathVariable Long taskId) {
        return taskService.patchProperty(property, taskId);
    } //é usado?

    @PatchMapping("/{taskId}/property/{userId}")
    public GetTaskDTO patchProperty(@RequestBody Property property, @PathVariable Long taskId, @PathVariable Long userId) {
        return taskService.patchProperty(property,taskId, userId);
    }

    @PatchMapping("/{taskId}/property/associates/{userId}")
    public Collection<GetUserDTO> patchAssociate(@PathVariable Long taskId, @RequestBody Collection<GetUserDTO> associates, @PathVariable Long userId) {
        return taskService.patchAssociate(taskId,associates,userId);
    }

    @PatchMapping("/{taskId}/update/finalDate/{userId}/calendar/{newFinalDate}")
    public GetTaskDTO updateTaskFinalDate(@PathVariable Long taskId, @PathVariable Long userId, @PathVariable LocalDateTime newFinalDate) {
        return taskService.updateTaskFinalDate(taskId,userId,newFinalDate);
    }

    @GetMapping("/{taskId}/{projectId}/userTask/{userId}")
    public UserTask getUserTask(@PathVariable Long projectId, @PathVariable Long userId, @PathVariable Long taskId) {
        return taskService.getUserTask(userId, taskId);
    }

//    @PutMapping("/{projectId}/putProperty")
//    public PropertyValue putPropertyValue(@RequestBody PropertyValue propertyValue){
//            return taskService.putPropertyValue(propertyValue);
//    }

    @PatchMapping("/{taskId}/property/put/{propertyId}/{userId}")
    public Property putPropertyValue(@PathVariable Long propertyId,
                                     @RequestBody PropertyValue propertyValue,
                                     @PathVariable Long userId,
                                     @PathVariable Long taskId) {
        return taskService.putPropertyValue(propertyValue, propertyId, userId, taskId);
    }

    @PatchMapping("/{taskId}/property/put/option/{userId}/{propertyId}")
    public Option putPropertyOption(@RequestBody Option newOption,
                                    @PathVariable Long userId,
                                    @PathVariable Long taskId,
                                    @PathVariable Long propertyId) {
        return taskService.putPropertyOption(newOption,userId, taskId, propertyId);
    }

    @DeleteMapping("/{taskId}/property/delete/option/{userId}/{propertyId}/{optionId}")
    public Property deletePropertyOption(@PathVariable Long userId,
                                     @PathVariable Long taskId,
                                     @PathVariable Long propertyId,
                                     @PathVariable Long optionId) {
        return taskService.deletePropertyOption(userId, taskId, propertyId, optionId);
    }

    @PatchMapping("/{taskId}/subtask/{userId}")
    public GetTaskDTO patchSubtask(@RequestBody Subtask subtask, @PathVariable Long taskId, @PathVariable Long userId) {
        return taskService.patchSubtask(subtask,taskId, userId);
    }

    @DeleteMapping("/{taskId}/subtask/delete/{subtaskId}/{userId}")
    public GetTaskDTO deleteSubtask(@PathVariable Long subtaskId, @PathVariable Long taskId, @PathVariable Long userId) {
        return taskService.deleteSubtask(subtaskId, taskId, userId);
    }


    @GetMapping("/property/get/getall")
    public Collection<Property> getAllProperties() {
        return taskService.getAllProperties();
    } //rever a segurança disso aqui (não pode existir?)

    @GetMapping("/{projectId}/priorities")
    public Collection<PriorityRecord> getAllPriorities(@PathVariable Long projectId) {
        List<Priority> listTest = List.of(Priority.values());
        return listTest.stream().map(priority -> new PriorityRecord(priority.name(), priority.backgroundColor)).collect(Collectors.toList());
    } //rever a segurança disso aqui (não pode existir?)

    @GetMapping("/{taskId}/comments/getAll")
    public Collection<Comment> getAllCommentsOfTask(@PathVariable Long taskId) {
        return taskService.getAllCommentsOfTask(taskId);
    } //rever a sugurança p isso aqui

    @PatchMapping("/{taskId}/comments/patch/user/{userId}")
    public Comment patchNewComment(@PathVariable Long taskId,
                                   @RequestBody Comment newComment, @PathVariable Long userId) {
        return taskService.patchNewComment(taskId, newComment, userId);
    }

    @DeleteMapping("/{taskId}/comments/delete/{commentId}/user/{userId}")
    public Collection<Comment> deleteComment(@PathVariable Long commentId,
                              @PathVariable Long taskId, @PathVariable Long userId) {
        return taskService.deleteComment(commentId,taskId,userId);
    }

    @PatchMapping("/{taskId}/update/currentStatus/user/{userId}")
    public GetTaskDTO updateCurrentStatus(@PathVariable Long taskId,
                                    @PathVariable Long userId,
                                    @RequestBody Status newCurrentStatus) {
        return taskService.patchCurrentStatus(taskId,userId,newCurrentStatus);
    }

    @PatchMapping("/{taskId}/update/currentOptions/user/{userId}/property/{propertyId}")
    public Property updatePropertyOptions(@PathVariable Long taskId,
                                          @PathVariable Long userId,
                                          @PathVariable Long propertyId,
                                          @RequestBody Collection<Option> newCurrentOptions) {
        return taskService.updatePropertyCurrentOptions(newCurrentOptions, propertyId, taskId, userId);
    }

    @PatchMapping("/{taskId}/user/{userId}/update/currentPriority")
    public GetTaskDTO updateCurrentPriority(@PathVariable Long taskId,
                                            @PathVariable Long userId,
                                            @RequestBody PriorityRecord priorityRecord) {
        return taskService.updateCurrentPriority(taskId,userId,priorityRecord);
    } //é usado?

    @PatchMapping("/{taskId}/update/user/{userId}/name{name}")
    public GetTaskDTO updateTaskName(@PathVariable Long taskId,
                                     @PathVariable Long userId,
                                     @PathVariable String name) {
        return taskService.updateTaskName(taskId,name);
    }

    @DeleteMapping("/{taskId}/property/delete/user/{userId}/{propertyId}")
    public GetTaskDTO deleteProperty(@PathVariable Long taskId,
                                     @PathVariable Long userId,
                                     @PathVariable Long propertyId) {
        return taskService.deleteProperty(taskId,userId,propertyId);
    }

    @DeleteMapping("/{taskId}/delete")
    public void deleteTask(@PathVariable Long taskId) {
        taskService.deleteTask(taskId);
    }

    @PatchMapping("/{taskId}/patch/task/file/{userId}")
    public GetTaskDTO patchFile(@PathVariable Long taskId,
                                @RequestParam MultipartFile file,
                                @PathVariable Long userId){
        return taskService.patchFile(taskId, file, userId);
    }

    @DeleteMapping("/{taskId}/delete/file/{fileId}/{userId}")
    public void deleteFile(@PathVariable Long taskId,
                           @PathVariable Long fileId,
                           @PathVariable Long userId) {
        taskService.deleteFile(taskId,fileId,userId);
    }
}
