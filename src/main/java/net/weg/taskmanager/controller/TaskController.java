package net.weg.taskmanager.controller;

import com.fasterxml.jackson.databind.annotation.JsonAppend;
import lombok.AllArgsConstructor;

import net.weg.taskmanager.model.dto.GetCommentDTO;
import net.weg.taskmanager.model.dto.get.GetUserDTO;
import net.weg.taskmanager.model.dto.get.GetUserTaskDTO;
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
import net.weg.taskmanager.service.UserTaskService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("/task")
public class TaskController {

    private final TaskService taskService;
    private final UserTaskService userTaskService;

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
    public Collection<GetUserDTO> patchAssociate(@PathVariable Long taskId, @RequestBody Collection<GetUserDTO> associates, @PathVariable Long userId) {
        return taskService.patchAssociate(taskId,associates,userId);
    }

    @DeleteMapping("property/associates/delete/{taskId}/{userId}/{removedAssociateId}")
    public Collection<GetUserDTO> deleteAssociate(@PathVariable Long taskId, @PathVariable Long userId, @PathVariable Long removedAssociateId) {
        return taskService.removeAssociate(taskId,removedAssociateId,userId);
    }

    @PutMapping("/update/finalDate/{taskId}/{userId}/calendar/{newFinalDate}")
    public GetTaskDTO updateTaskFinalDate(@PathVariable Long taskId, @PathVariable Long userId, @PathVariable LocalDate newFinalDate) {
        return taskService.updateTaskFinalDate(taskId,userId,newFinalDate);
    }

    @PatchMapping("/update/{taskId}/scheludeDate/{userId}/calendar/{newDate}")
    public GetTaskDTO updateTaskScheludeDate(@PathVariable Long taskId, @PathVariable Long userId, @PathVariable LocalDate newDate) {
        return taskService.updateTaskScheludeDate(taskId,userId,newDate);
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

    @PutMapping("/property/put/option/{userId}/{taskId}/{propertyId}")
    public Option putPropertyOption(@RequestBody Option newOption,
                                    @PathVariable Long userId,
                                    @PathVariable Long taskId,
                                    @PathVariable Long propertyId) {
        return taskService.putPropertyOption(newOption,userId, taskId, propertyId);
    }

    @DeleteMapping("/property/delete/option/{userId}/{taskId}/{propertyId}/{optionId}")
    public Property deletePropertyOption(@PathVariable Long userId,
                                     @PathVariable Long taskId,
                                     @PathVariable Long propertyId,
                                     @PathVariable Long optionId) {
        return taskService.deletePropertyOption(userId, taskId, propertyId, optionId);
    }

    @PatchMapping("/subtask/{taskId}/{userId}")
    public GetTaskDTO patchSubtask(@RequestBody Subtask subtask, @PathVariable Long taskId, @PathVariable Long userId) {
        return taskService.patchSubtask(subtask,taskId, userId);
    }

    @DeleteMapping("/subtask/delete/{subtaskId}/{taskId}/{userId}")
    public GetTaskDTO deleteSubtask(@PathVariable Long subtaskId, @PathVariable Long taskId, @PathVariable Long userId) {
        return taskService.deleteSubtask(subtaskId, taskId, userId);
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
    public Collection<GetCommentDTO> deleteComment(@PathVariable Long commentId,
                                                   @PathVariable Long taskId, @PathVariable Long userId) {
        return taskService.deleteComment(commentId,taskId,userId);
    }

    @PatchMapping("/update/{taskId}/currentStatus/{userId}")
    public GetTaskDTO updateCurrentStatus(@PathVariable Long taskId,
                                    @PathVariable Long userId,
                                    @RequestBody Status newCurrentStatus) {
        return taskService.updateCurrentStatus(taskId,userId,newCurrentStatus);
    }

    @PatchMapping("update/{taskId}/currentOptions/{userId}/{propertyId}")
    public Property updatePropertyOptions(@PathVariable Long taskId,
                                          @PathVariable Long userId,
                                          @PathVariable Long propertyId,
                                          @RequestBody Collection<Option> newCurrentOptions) {
        return taskService.updatePropertyCurrentOptions(newCurrentOptions, propertyId, taskId, userId);
    }

    @PatchMapping("/update/{taskId}/{userId}/currentPriority")
    public GetTaskDTO updateCurrentPriority(@PathVariable Long taskId,
                                            @PathVariable Long userId,
                                            @RequestBody PriorityRecord priorityRecord) {
        return taskService.updateCurrentPriority(taskId,userId,priorityRecord);
    }

    @PatchMapping("/update/{taskId}/name/{userId}/{name}")
    public GetTaskDTO updateTaskName(@PathVariable Long taskId,
                                     @PathVariable Long userId,
                                     @PathVariable String name) {
        return taskService.updateTaskName(taskId,userId,name);
    }

    @DeleteMapping("/property/delete/{taskId}/{userId}/{propertyId}")
    public GetTaskDTO deleteProperty(@PathVariable Long taskId,
                                     @PathVariable Long userId,
                                     @PathVariable Long propertyId) {
        return taskService.deleteProperty(taskId,userId,propertyId);
    }

    @DeleteMapping("/delete/{taskId}")
    public void deleteTask(@PathVariable Long taskId) {
        taskService.deleteTask(taskId);
    }

    @PatchMapping("/patch/task/file/{taskId}/{userId}")
    public GetTaskDTO patchFile(@PathVariable Long taskId,
                                @RequestParam MultipartFile file,
                                @PathVariable Long userId){
        return taskService.patchFile(taskId, file, userId);
    }

    @DeleteMapping("/delete/task/file/{taskId}/{fileId}/{userId}")
    public void deleteFile(@PathVariable Long taskId,
                           @PathVariable Long fileId,
                           @PathVariable Long userId) {
        taskService.deleteFile(taskId,fileId,userId);
    }

    @GetMapping("/get/{userId}/{taskId}")
    public GetUserTaskDTO getUserWorkedTime(@PathVariable Long userId,
                                            @PathVariable Long taskId) {
        return userTaskService.getUserWorkedTime(userId,taskId);
    }

    @PutMapping("/put/workedTime")
    public void updateUserWorkedTime(@RequestBody UserTask userTaskUpdated) {
        userTaskService.updateWorkedTime(userTaskUpdated);
    }
}
