package net.weg.taskmanager.service;

import lombok.AllArgsConstructor;
import net.weg.taskmanager.model.dto.put.PutTaskDTO;
import net.weg.taskmanager.model.entity.Comment;
import net.weg.taskmanager.model.entity.Historic;
import net.weg.taskmanager.model.entity.Task;
import net.weg.taskmanager.model.entity.User;
import net.weg.taskmanager.model.enums.Priority;
import net.weg.taskmanager.model.property.Property;
import net.weg.taskmanager.model.property.PropertyType;
import net.weg.taskmanager.model.property.values.PropertyValue;
import net.weg.taskmanager.repository.HistoricRepository;
import net.weg.taskmanager.repository.TaskRepository;
import net.weg.taskmanager.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

@Service
@AllArgsConstructor
public class HistoricService {
    private final HistoricRepository historicRepository;
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;

    public Task patchNewCommentHistoric(Long taskId, Long userId) {
        User userForHistoric = userRepository.findById(userId).get();
        Task taskForHistoric = taskRepository.findById(taskId).get();

        Historic historic = new Historic(
                userForHistoric,
                userForHistoric.getName() + " adicionou um comentário",
                LocalDateTime.now()
        );

        Historic savedHistoric = this.historicRepository.save(historic);

        taskForHistoric.getHistoric().add(savedHistoric);

        return taskRepository.save(taskForHistoric);
    }

    public Task deleteCommentHistoric(Long taskId, Long userId) {
        User userForHistoric = userRepository.findById(userId).get();
        Task taskForHistoric = taskRepository.findById(taskId).get();

        Historic historic = new Historic(
                userForHistoric,
                userForHistoric.getName() + " deletou um comentário.",
                LocalDateTime.now()
        );

        Historic savedHistoric = this.historicRepository.save(historic);

        taskForHistoric.getHistoric().add(savedHistoric);

        taskRepository.save(taskForHistoric);

        return taskForHistoric;
    }

    public void putPropertyValueHistoric(Property property, PropertyValue propertyValue,
                                         Long userId, Long taskId) {
        User userForHistoric = userRepository.findById(userId).get();
        Task taskForHistoric = taskRepository.findById(taskId).get();

        if (property.getPropertyType() == PropertyType.DataValue || property.getPropertyType() == PropertyType.IntegerValue ||
                property.getPropertyType() == PropertyType.TextValue || property.getPropertyType() == PropertyType.DoubleValue) {
            Historic historic = new Historic(
                    userForHistoric,
                    userForHistoric.getName() + " mudou o valor da propriedade " + property.getName() + " para " + propertyValue.getValue().getValue().toString(),
                    LocalDateTime.now()
            );
            Historic savedHistoric = this.historicRepository.save(historic);

            taskForHistoric.getHistoric().add(savedHistoric);

            taskRepository.save(taskForHistoric);
        }
    }

    public Task patchProperty(Property property, Long userId, Long taskId) {
        Historic historic = new Historic();
        User userForHistoric = userRepository.findById(userId).get();
        Task task = taskRepository.findById(taskId).get();

        if (property.getPropertyType().toString() == "IntegerValue") {

            historic = new Historic(
                    userForHistoric,
                    userForHistoric.getName() + " criou uma propriedade de número chamada " + property.getName(),
                    LocalDateTime.now()
            );

        }
        if (property.getPropertyType().toString() == "DoubleValue") {

            historic = new Historic(
                    userForHistoric,
                    userForHistoric.getName() + " criou uma propriedade de decimal chamada " + property.getName(),
                    LocalDateTime.now()
            );

        }
        if (property.getPropertyType().toString() == "DataValue") {

            historic = new Historic(
                    userForHistoric,
                    userForHistoric.getName() + " criou uma propriedade de calendário chamada " + property.getName(),
                    LocalDateTime.now()
            );

        }
        if (property.getPropertyType().toString() == "AssociatesValue") {

            historic = new Historic(
                    userForHistoric,
                    userForHistoric.getName() + " criou uma propriedade de pessoas chamada " + property.getName(),
                    LocalDateTime.now()
            );

        }
        if (property.getPropertyType().toString() == "MultiSelectValue") {

            historic = new Historic(
                    userForHistoric,
                    userForHistoric.getName() + " criou uma propriedade de multiselect chamada " + property.getName(),
                    LocalDateTime.now()
            );

        }
        if (property.getPropertyType().toString() == "TextValue") {

            historic = new Historic(
                    userForHistoric,
                    userForHistoric.getName() + " criou uma propriedade de texto chamada " + property.getName(),
                    LocalDateTime.now()
            );

        }
        if (property.getPropertyType().toString() == "UniSelectValue") {

            historic = new Historic(
                    userForHistoric,
                    userForHistoric.getName() + " criou uma propriedade de uniselect chamada " + property.getName(),
                    LocalDateTime.now()
            );

        }

        Historic savedHistoric = this.historicRepository.save(historic);

        task.getHistoric().add(savedHistoric);

        return taskRepository.save(task);
    }

    public Task patchAssociateHistoric(Long taskId,Long userId, Collection<User> associates, Collection<String> currentUsersAssociates) {
        User userForHistoric = userRepository.findById(userId).get();
        Task taskForHistoric = taskRepository.findById(taskId).get();

        String description =  userForHistoric.getName() + " associou " + currentUsersAssociates.stream().toList() + " a tarefa";
        description = description.replace("[","");
        description = description.replace("]","");

        if (associates.size() == 2) {
            description = description.replace(","," e");
        }

        Historic historic = new Historic(
                userForHistoric,
                description,
                LocalDateTime.now()
        );
        Historic savedHistoric = this.historicRepository.save(historic);

        taskForHistoric.getHistoric().add(savedHistoric);

        return taskRepository.save(taskForHistoric);
    }

    // change later, divide in multiple functions with different services and requests
    public Task generalUpdateHistoric(PutTaskDTO putTaskDTO, Task task, User userForHistoric) {

        //for select
        putTaskDTO.getProperties().forEach(property -> {
            task.getProperties().forEach(property1 -> {
                if (property.getCurrentOptions() != property1.getCurrentOptions()) {
                    ArrayList<String> currentOptionListUpdate = new ArrayList<>();
                    property.getCurrentOptions().forEach(currentOption -> {
                        currentOptionListUpdate.add(currentOption.getValue());
                    });
                    String description = userForHistoric.getName() + " mudou o valor da propriedade " + property.getName() + " para " + currentOptionListUpdate.stream().toList();
                    description = description.replace("[", "");
                    description = description.replace("]", "");
                    Historic historic = new Historic(userForHistoric, description, LocalDateTime.now());
                    Historic savedHistoric = this.historicRepository.save(historic);

                    task.getHistoric().add(savedHistoric);

                    taskRepository.save(task);
                }
            });
        });
        // for status
        if (!putTaskDTO.getCurrentStatus().getName().equals(task.getCurrentStatus().getName())) {
            Historic historic = new Historic(userForHistoric, userForHistoric.getName() + " mudou o valor da propriedade status para " + putTaskDTO.getCurrentStatus().getName(), LocalDateTime.now());
            Historic savedHistoric = this.historicRepository.save(historic);

            task.getHistoric().add(savedHistoric);

            taskRepository.save(task);
        }
        // for priority
        if (!putTaskDTO.getPriority().name().toUpperCase().equals(task.getPriority().name())) {
            Priority prioritySaved = Priority.valueOf(putTaskDTO.getPriority().name());
            prioritySaved.backgroundColor = putTaskDTO.getPriority().backgroundColor();
            BeanUtils.copyProperties(putTaskDTO, task);
            task.setPriority(prioritySaved);

            Historic historic = new Historic(userForHistoric, userForHistoric.getName() + " mudou o valor da propriedade prioridade para " + putTaskDTO.getPriority().name().toLowerCase(), LocalDateTime.now());
            Historic savedHistoric = this.historicRepository.save(historic);

            task.getHistoric().add(savedHistoric);

            taskRepository.save(task);

        }

        return task;
    }

}
