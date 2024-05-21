package net.weg.taskmanager.controller;

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
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("/task")
public class TaskController {

    private final TaskService taskService;
    private final UserTaskService userTaskService;

    @GetMapping("/{taskId}")//security nao implementado. Esse metodo é usado?
    public GetTaskDTO findById(@PathVariable Long taskId) {
        return taskService.findById(taskId);
    }

    @GetMapping("/{projectId}/status/{statusId}") //onde é usado? metodo semm cobertura do security
    public Collection<GetTaskDTO> getTasksByStatus(@PathVariable Long statusId) {
        return taskService.getTasksByStatus(statusId);
    }

    @DeleteMapping("/{taskId}")
    public void delete(@PathVariable Long taskId) {
        taskService.delete(taskId);
    }

    @PostMapping("/project/{projectId}")
    public GetTaskDTO create(@RequestBody PostTaskDTO postTaskDTO){
        return taskService.create(postTaskDTO);}

    @PutMapping("/{taskId}/user/{userId}")
    public GetTaskDTO update(@RequestBody PutTaskDTO putTaskDTO,@PathVariable Long userId){
//        GetTaskDTO getTaskDTO = taskService.update(putTaskDTO);
        return taskService.update(putTaskDTO,userId);
    }
    @PatchMapping("/{taskId}/userTask")
    public UserTask setWorkedTime(@RequestBody UserTask userTask) {
        return taskService.setWorkedTime(userTask);
    }
    @PatchMapping("/{taskId}/property/{userId}")
    public GetTaskDTO patchProperty(@RequestBody Property property, @PathVariable Long taskId, @PathVariable Long userId) {
        return taskService.patchProperty(property,taskId, userId);
    }

    @PatchMapping("/{taskId}/property/associates/{userId}")
    public Collection<GetUserDTO> patchAssociate(@PathVariable Long taskId, @RequestBody Collection<GetUserDTO> associates, @PathVariable Long userId) {
        return taskService.patchAssociate(taskId,associates,userId);
    }

    @PatchMapping("/{taskId}/property/associates/delete/{userId}")
    public Collection<GetUserDTO> deleteAssociate(@PathVariable Long taskId, @PathVariable Long userId, @RequestParam Long removedAssociateId) {
        return taskService.removeAssociate(taskId,removedAssociateId,userId);
    }

    @PatchMapping("/{taskId}/update/finalDate/{userId}/calendar")
    public GetTaskDTO updateTaskFinalDate(@PathVariable Long taskId, @PathVariable Long userId, @RequestParam String newFinalDate) {
        System.out.println(newFinalDate);
        LocalDate localDateTime = LocalDate.parse(newFinalDate);
        return taskService.updateTaskFinalDate(taskId,userId,localDateTime);
    }

    @PatchMapping("/{taskId}/update/scheludeDate/{userId}/calendar")
    public GetTaskDTO updateTaskScheludeDate(@PathVariable Long taskId, @PathVariable Long userId, @RequestParam String newDate) {
        System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAA");
        LocalDate localDateTime = LocalDate.parse(newDate);
        return taskService.updateTaskScheludeDate(taskId,userId,localDateTime);
    }

    @GetMapping("/userTask/{userId}/{taskId}")
    public UserTask getUserTask(@PathVariable Long userId, @PathVariable Long taskId){
        return taskService.getUserTask(userId, taskId);
    }

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

    @GetMapping("/priorities")
    public Collection<PriorityRecord> getAllPriorities() {
        List<Priority> listTest =  List.of(Priority.values());
       return listTest.stream().map(priority -> new PriorityRecord(priority.name(), priority.backgroundColor)).collect(Collectors.toList());
    }

    @GetMapping("/user/{userId}")
    public Collection<GetTaskDTO> getTasksByUserId(@PathVariable Long userId){
        return taskService.getTasksByUserId(userId);
    }

    @GetMapping("/{taskId}/project/{projectId}")
    public Collection<GetTaskDTO> getTasksByProjectId(@PathVariable Long projectId){
        return taskService.getTasksByProjectId(projectId);
    }

//    @PutMapping("/{userId}")
//    public GetTaskDTO update(@RequestBody PutTaskDTO putTaskDTO,@PathVariable Long userId){
//        return taskService.update(putTaskDTO,userId);
//    } //isso aqui é para continuar existindo?



//    @PostMapping("/{projectId}")
//    public GetTaskDTO create(@PathVariable Long projectId, @RequestBody PostTaskDTO postTaskDTO) {
//        return taskService.create(postTaskDTO);
//    }

//    @PutMapping("/{projectId}")
//    public GetTaskDTO update(@PathVariable Long projectId, @RequestBody PutTaskDTO putTaskDTO) {
//        return taskService.update(putTaskDTO);
//    }

    @PatchMapping("/{taskId}/{projectId}/property")
    public GetTaskDTO patchProperty(@PathVariable Long projectId, @RequestBody Property property, @PathVariable Long taskId) {
        return taskService.patchProperty(property, taskId);
    } //é usado?

    @GetMapping("/{taskId}/{projectId}/userTask/{userId}")
    public UserTask getUserTask(@PathVariable Long projectId, @PathVariable Long userId, @PathVariable Long taskId) {
        return taskService.getUserTask(userId, taskId);
    }

//    @PutMapping("/{projectId}/putProperty")
//    public PropertyValue putPropertyValue(@RequestBody PropertyValue propertyValue){
//            return taskService.putPropertyValue(propertyValue);
//    }

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
    public Collection<GetCommentDTO> deleteComment(@PathVariable Long commentId,
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

    @PatchMapping("/setConcluded")
    public void setTaskConcluded(@RequestParam Long taskId) {
        taskService.setTaskConcluded(taskId);
    }

    @PatchMapping("/{taskId}/user/{userId}/update/currentPriority")
    public GetTaskDTO updateCurrentPriority(@PathVariable Long taskId,
                                            @PathVariable Long userId,
                                            @RequestBody PriorityRecord priorityRecord) {
        return taskService.updateCurrentPriority(taskId,userId,priorityRecord);
    } //é usado?

    @PatchMapping("/{taskId}/update/user/{userId}/name")
    public GetTaskDTO updateTaskName(@PathVariable Long taskId,
                                     @PathVariable Long userId,
                                     @RequestParam String name) {
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

    @GetMapping("/{taskId}/get/{userId}")
    public ResponseEntity<GetUserTaskDTO> getUserWorkedTime(@PathVariable Long userId,
                                                           @PathVariable Long taskId) {
        try {
            return ResponseEntity.ok(userTaskService.getUserWorkedTime(userId,taskId));
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }
    }

    @PatchMapping("/{taskId}/put/workedTime")
    public void updateUserWorkedTime(@RequestBody UserTask userTaskUpdated) {
        userTaskService.updateWorkedTime(userTaskUpdated);
    }
}
