package net.weg.taskmanager.controller;

import lombok.AllArgsConstructor;

import net.weg.taskmanager.Producer;
import net.weg.taskmanager.model.dto.GetCommentDTO;
import net.weg.taskmanager.model.dto.get.GetUserDTO;
import net.weg.taskmanager.model.dto.get.GetUserTaskDTO;
import net.weg.taskmanager.model.dto.shortDTOs.ShortUserDTO;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    private final Producer topicProducer;

    @GetMapping("/{taskId}")//security nao implementado. Esse metodo é usado?
    public ResponseEntity<GetTaskDTO> findById(@PathVariable Long taskId) {
        try{
            return ResponseEntity.ok(taskService.findById(taskId));
        } catch (Exception e){
            topicProducer.sendErrorMessage("Não foi possível achar a tarefa de id: " + taskId, e.getMessage(), null);
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/{projectId}/status/{statusId}") //onde é usado? metodo semm cobertura do security
    public ResponseEntity<Collection<GetTaskDTO>> getTasksByStatus(@PathVariable Long statusId) {
        try{
            return ResponseEntity.ok(taskService.getTasksByStatus(statusId));
        } catch (Exception e){
            topicProducer.sendErrorMessage("Falha ao procurar por tarefas com o status: " + statusId, e.getMessage(), null);
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity delete(@PathVariable Long taskId) {
        try{
            taskService.delete(taskId);
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e){
            topicProducer.sendErrorMessage("Falha ao tentar deletar a tarefa de id: " + taskId, e.getMessage(), null);
            return new ResponseEntity(HttpStatus.CONFLICT);
        }
    }

    @PostMapping("/project/{projectId}")
    public ResponseEntity<GetTaskDTO> create(@RequestBody PostTaskDTO postTaskDTO, @PathVariable Long projectId){
        try{
            return ResponseEntity.ok(taskService.create(postTaskDTO));
        } catch (Exception e){
            topicProducer.sendErrorMessage("Falha ao tentar criar tarefa no projeto de id: " + projectId, e.getMessage(), null);
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @PatchMapping("/{taskId}/dependencies/{userId}")
    public ResponseEntity<GetTaskDTO> patchDependencies(@PathVariable Long taskId, @RequestBody Collection<GetTaskDTO> taskDTOS, @PathVariable Long userId) {
        try{
            return ResponseEntity.ok(taskService.patchDependencies(taskId, taskDTOS));
        } catch (Exception e){
            topicProducer.sendErrorMessage("Não foi possível atualizar as dependências da task de id: " + taskId, e.getMessage(), userId);
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }


    @PutMapping("/{taskId}/user/{userId}")
    public ResponseEntity<GetTaskDTO> update(@RequestBody PutTaskDTO putTaskDTO,@PathVariable Long userId, @PathVariable Long taskId){
        try{
            return ResponseEntity.ok(taskService.update(putTaskDTO,userId));
        } catch (Exception e){
            topicProducer.sendErrorMessage("Não foi possível atualizar a task de id: " + taskId, e.getMessage(), userId);
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }
    @PatchMapping("/{taskId}/userTask")
    public ResponseEntity<UserTask> setWorkedTime(@RequestBody UserTask userTask) {
        try{
            return ResponseEntity.ok(taskService.setWorkedTime(userTask));
        } catch (Exception e){
            topicProducer.sendErrorMessage("Falha ao tentar atualizar o tempo trabalhado", e.getMessage(), userTask.getUserId());
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }
    @PatchMapping("/{taskId}/property/{userId}")
    public ResponseEntity<GetTaskDTO> patchProperty(@RequestBody Property property, @PathVariable Long taskId, @PathVariable Long userId) {
        try{
            return ResponseEntity.ok(taskService.patchProperty(property,taskId, userId));
        } catch (Exception e){
            topicProducer.sendErrorMessage(e.getMessage());
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @PatchMapping("/{taskId}/property/associates/{userId}")
    public ResponseEntity<Collection<GetUserDTO>> patchAssociate(@PathVariable Long taskId, @RequestBody Collection<ShortUserDTO> associates, @PathVariable Long userId) {
        try{
            return ResponseEntity.ok(taskService.patchAssociate(taskId,associates,userId));
        } catch (Exception e){
            topicProducer.sendErrorMessage(e.getMessage());
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @PatchMapping("/{taskId}/property/associates/delete/{userId}")
    public ResponseEntity<Collection<GetUserDTO>> deleteAssociate(@PathVariable Long taskId, @PathVariable Long userId, @RequestParam Long removedAssociateId) {
        try{
            return ResponseEntity.ok(taskService.removeAssociate(taskId,removedAssociateId,userId));
        } catch (Exception e){
            topicProducer.sendErrorMessage(e.getMessage());
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @PatchMapping("/{taskId}/update/finalDate/{userId}/calendar")
    public ResponseEntity<GetTaskDTO> updateTaskFinalDate(@PathVariable Long taskId, @PathVariable Long userId, @RequestParam String newFinalDate) {
        try{
            LocalDate localDateTime = LocalDate.parse(newFinalDate);
            return ResponseEntity.ok(taskService.updateTaskFinalDate(taskId,userId,localDateTime));
        } catch (Exception e){
            topicProducer.sendErrorMessage(e.getMessage());
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @PatchMapping("/{taskId}/notes")
    public ResponseEntity setTaskNotes(@PathVariable Long taskId, @RequestParam String notes) {
        try{
            taskService.patchNotes(taskId,notes);
             return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e){
            topicProducer.sendErrorMessage(e.getMessage());
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @PatchMapping("/{taskId}/description/{userId}")
    public ResponseEntity<GetTaskDTO> patchDescription(@PathVariable Long taskId, @PathVariable Long userId, @RequestParam String description) {
        try {
            return ResponseEntity.ok(taskService.patchDescription(taskId,userId,description));
        } catch (Exception e){
            topicProducer.sendErrorMessage(e.getMessage());
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @PatchMapping("/{taskId}/update/scheludeDate/{userId}/calendar")
    public ResponseEntity<GetTaskDTO> updateTaskScheludeDate(@PathVariable Long taskId, @PathVariable Long userId, @RequestParam String newDate) {
        LocalDate localDateTime = LocalDate.parse(newDate);
        try{
             return ResponseEntity.ok(taskService.updateTaskScheludeDate(taskId,userId,localDateTime));
        } catch (Exception e){
            topicProducer.sendErrorMessage(e.getMessage());
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/userTask/{userId}/{taskId}")
    public ResponseEntity<UserTask> getUserTask(@PathVariable Long userId, @PathVariable Long taskId){
        try{
             return ResponseEntity.ok(taskService.getUserTask(userId, taskId));
        } catch (Exception e){
            topicProducer.sendErrorMessage(e.getMessage());
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @PatchMapping("/{taskId}/property/put/{propertyId}/{userId}")
    public ResponseEntity<Property> putPropertyValue(@PathVariable Long propertyId,
                                     @RequestBody PropertyValue propertyValue,
                                     @PathVariable Long userId,
                                     @PathVariable Long taskId) {
        try{
             return ResponseEntity.ok(taskService.putPropertyValue(propertyValue, propertyId, userId, taskId));
        } catch (Exception e){
            topicProducer.sendErrorMessage(e.getMessage());
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @PatchMapping("/{taskId}/property/put/option/{userId}/{propertyId}")
    public ResponseEntity<Option> putPropertyOption(@RequestBody Option newOption,
                                    @PathVariable Long userId,
                                    @PathVariable Long taskId,
                                    @PathVariable Long propertyId) {
        try{
             return ResponseEntity.ok(taskService.putPropertyOption(newOption,userId, taskId, propertyId));
        } catch (Exception e){
            topicProducer.sendErrorMessage(e.getMessage());
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @DeleteMapping("/{taskId}/property/delete/option/{userId}/{propertyId}/{optionId}")
    public ResponseEntity<Property> deletePropertyOption(@PathVariable Long userId,
                                         @PathVariable Long taskId,
                                         @PathVariable Long propertyId,
                                         @PathVariable Long optionId) {
        try{
             return ResponseEntity.ok(taskService.deletePropertyOption(userId, taskId, propertyId, optionId));
        } catch (Exception e){
            topicProducer.sendErrorMessage(e.getMessage());
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @PatchMapping("/{taskId}/subtask/{userId}")
    public ResponseEntity<GetTaskDTO> patchSubtask(@RequestBody Subtask subtask, @PathVariable Long taskId, @PathVariable Long userId) {
        try{
             return ResponseEntity.ok(taskService.patchSubtask(subtask,taskId, userId));
        } catch (Exception e){
            topicProducer.sendErrorMessage(e.getMessage());
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @DeleteMapping("/{taskId}/subtask/delete/{subtaskId}/{userId}")
    public ResponseEntity<GetTaskDTO> deleteSubtask(@PathVariable Long subtaskId, @PathVariable Long taskId, @PathVariable Long userId) {
        try{
             return ResponseEntity.ok(taskService.deleteSubtask(subtaskId, taskId, userId));
        } catch (Exception e){
            topicProducer.sendErrorMessage(e.getMessage());
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/property/get/getall")
    public ResponseEntity<Collection<Property>> getAllProperties() {
        try{
             return ResponseEntity.ok(taskService.getAllProperties());
        } catch (Exception e){
            topicProducer.sendErrorMessage(e.getMessage());
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    } //rever a segurança disso aqui (não pode existir?)

    @GetMapping("/priorities")
    public ResponseEntity<Collection<PriorityRecord>> getAllPriorities() {
        try{
            List<Priority> listTest =  List.of(Priority.values());
            Collection<PriorityRecord> res = listTest.stream().map(priority -> new PriorityRecord(priority.name(), priority.backgroundColor)).collect(Collectors.toList());
            return ResponseEntity.ok(res);
        } catch (Exception e){
            topicProducer.sendErrorMessage(e.getMessage());
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<Collection<GetTaskDTO>> getTasksByUserId(@PathVariable Long userId){
        try{
             return ResponseEntity.ok(taskService.getTasksByUserId(userId));
        } catch (Exception e){
            topicProducer.sendErrorMessage(e.getMessage());
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/{taskId}/project/{projectId}")
    public ResponseEntity<Collection<GetTaskDTO>> getTasksByProjectId(@PathVariable Long projectId){
        try{
             return ResponseEntity.ok(taskService.getTasksByProjectId(projectId));
        } catch (Exception e){
            topicProducer.sendErrorMessage(e.getMessage());
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

//    @PutMapping("/{userId}")
//    public ResponseEntity<GetTaskDTO> update(@RequestBody PutTaskDTO putTaskDTO,@PathVariable Long userId){
//        return taskService.update(putTaskDTO,userId);
//    } //isso aqui é para continuar existindo?



//    @PostMapping("/{projectId}")
//    public ResponseEntity<GetTaskDTO> create(@PathVariable Long projectId, @RequestBody PostTaskDTO postTaskDTO) {
//        return taskService.create(postTaskDTO);
//    }

//    @PutMapping("/{projectId}")
//    public ResponseEntity<GetTaskDTO> update(@PathVariable Long projectId, @RequestBody PutTaskDTO putTaskDTO) {
//        return taskService.update(putTaskDTO);
//    }

    @PatchMapping("/{taskId}/{projectId}/property")
    public ResponseEntity<GetTaskDTO> patchProperty(@PathVariable Long projectId, @RequestBody Property property, @PathVariable Long taskId) {
        try{
             return ResponseEntity.ok(taskService.patchProperty(property, taskId));
        } catch (Exception e){
            topicProducer.sendErrorMessage(e.getMessage());
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    } //é usado?

    @GetMapping("/{taskId}/{projectId}/userTask/{userId}")
    public ResponseEntity<UserTask> getUserTask(@PathVariable Long projectId, @PathVariable Long userId, @PathVariable Long taskId) {
        try{
             return ResponseEntity.ok(taskService.getUserTask(userId, taskId));
        } catch (Exception e){
            topicProducer.sendErrorMessage(e.getMessage());
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

//    @PutMapping("/{projectId}/putProperty")
//    public PropertyValue putPropertyValue(@RequestBody PropertyValue propertyValue){
//            return taskService.putPropertyValue(propertyValue);
//    }

    @GetMapping("/{projectId}/priorities")
    public ResponseEntity<Collection<PriorityRecord>> getAllPriorities(@PathVariable Long projectId) {
        try{
            List<Priority> listTest = List.of(Priority.values());
            Collection<PriorityRecord> res = listTest.stream().map(priority -> new PriorityRecord(priority.name(), priority.backgroundColor)).collect(Collectors.toList());
            return ResponseEntity.ok(res);
        } catch (Exception e){
            topicProducer.sendErrorMessage(e.getMessage());
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    } //rever a segurança disso aqui (não pode existir?)

    @GetMapping("/{taskId}/comments/getAll")
    public ResponseEntity<Collection<Comment>> getAllCommentsOfTask(@PathVariable Long taskId) {
        try{
             return ResponseEntity.ok(taskService.getAllCommentsOfTask(taskId));
        } catch (Exception e){
            topicProducer.sendErrorMessage(e.getMessage());
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    } //rever a sugurança p isso aqui

    @PatchMapping("/{taskId}/comments/patch/user/{userId}")
    public ResponseEntity<Comment> patchNewComment(@PathVariable Long taskId,
                                   @RequestBody Comment newComment, @PathVariable Long userId) {
        try{
             return ResponseEntity.ok(taskService.patchNewComment(taskId, newComment, userId));
        } catch (Exception e){
            topicProducer.sendErrorMessage(e.getMessage());
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @DeleteMapping("/{taskId}/comments/delete/{commentId}/user/{userId}")
    public ResponseEntity<Collection<GetCommentDTO>> deleteComment(@PathVariable Long commentId,
                              @PathVariable Long taskId, @PathVariable Long userId) {
        try{
             return ResponseEntity.ok(taskService.deleteComment(commentId,taskId,userId));
        } catch (Exception e){
            topicProducer.sendErrorMessage(e.getMessage());
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @PatchMapping("/{taskId}/update/currentStatus/user/{userId}")
    public ResponseEntity<GetTaskDTO> updateCurrentStatus(@PathVariable Long taskId,
                                    @PathVariable Long userId,
                                    @RequestBody Status newCurrentStatus) {
        try{
             return ResponseEntity.ok(taskService.patchCurrentStatus(taskId,userId,newCurrentStatus));
        } catch (Exception e){
            topicProducer.sendErrorMessage(e.getMessage());
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @PatchMapping("/{taskId}/update/currentOptions/user/{userId}/property/{propertyId}")
    public ResponseEntity<Property> updatePropertyOptions(@PathVariable Long taskId,
                                          @PathVariable Long userId,
                                          @PathVariable Long propertyId,
                                          @RequestBody Collection<Option> newCurrentOptions) {
        try{
             return ResponseEntity.ok(taskService.updatePropertyCurrentOptions(newCurrentOptions, propertyId, taskId, userId));
        } catch (Exception e){
            topicProducer.sendErrorMessage(e.getMessage());
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @PatchMapping("/setConcluded")
    public ResponseEntity<?> setTaskConcluded(@RequestParam Long taskId) {
        try{
            taskService.setTaskConcluded(taskId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e){
            topicProducer.sendErrorMessage(e.getMessage());
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @PatchMapping("/{taskId}/user/{userId}/update/currentPriority")
    public ResponseEntity<GetTaskDTO> updateCurrentPriority(@PathVariable Long taskId,
                                            @PathVariable Long userId,
                                            @RequestBody PriorityRecord priorityRecord) {
        try{
            return ResponseEntity.ok(taskService.updateCurrentPriority(taskId,userId,priorityRecord));
        } catch (Exception e){
            topicProducer.sendErrorMessage(e.getMessage());
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    } //é usado?

    @PatchMapping("/{taskId}/update/user/{userId}/name")
    public ResponseEntity<GetTaskDTO> updateTaskName(@PathVariable Long taskId,
                                     @PathVariable Long userId,
                                     @RequestParam String name) {
        try{
             return ResponseEntity.ok(taskService.updateTaskName(taskId,name));
        } catch (Exception e){
            topicProducer.sendErrorMessage(e.getMessage());
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @DeleteMapping("/{taskId}/property/delete/user/{userId}/{propertyId}")
    public ResponseEntity<GetTaskDTO> deleteProperty(@PathVariable Long taskId,
                                     @PathVariable Long userId,
                                     @PathVariable Long propertyId) {
        try{
             return ResponseEntity.ok(taskService.deleteProperty(taskId,userId,propertyId));
        } catch (Exception e){
            topicProducer.sendErrorMessage(e.getMessage());
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @DeleteMapping("/{taskId}/delete")
    public ResponseEntity deleteTask(@PathVariable Long taskId) {
        try{
            taskService.deleteTask(taskId);
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e){
            topicProducer.sendErrorMessage(e.getMessage());
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @PatchMapping("/{taskId}/patch/task/file/{userId}")
    public ResponseEntity<GetTaskDTO> patchFile(@PathVariable Long taskId,
                                @RequestParam MultipartFile file,
                                @PathVariable Long userId){
        try{
             return ResponseEntity.ok(taskService.patchFile(taskId, file, userId));
        } catch (Exception e){
            topicProducer.sendErrorMessage(e.getMessage());
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @DeleteMapping("/{taskId}/delete/file/{fileId}/{userId}")
    public ResponseEntity deleteFile(@PathVariable Long taskId,
                           @PathVariable Long fileId,
                           @PathVariable Long userId) {
        try{
            taskService.deleteFile(taskId,fileId,userId);
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e){
            topicProducer.sendErrorMessage(e.getMessage());
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/{taskId}/get/{userId}")
    public ResponseEntity<GetUserTaskDTO> getUserWorkedTime(@PathVariable Long userId,
                                                           @PathVariable Long taskId) {
        try {
            return ResponseEntity.ok(userTaskService.getUserWorkedTime(userId,taskId));
        } catch (Exception e){
            topicProducer.sendErrorMessage(e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }
    }

    @PatchMapping("/{taskId}/put/workedTime")
    public ResponseEntity updateUserWorkedTime(@RequestBody UserTask userTaskUpdated) {
        try{
            userTaskService.updateWorkedTime(userTaskUpdated);
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e){
            topicProducer.sendErrorMessage(e.getMessage());
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }
}
