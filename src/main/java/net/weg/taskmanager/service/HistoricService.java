package net.weg.taskmanager.service;

import lombok.AllArgsConstructor;
import net.weg.taskmanager.model.dto.put.PutTaskDTO;
import net.weg.taskmanager.model.entity.*;
import net.weg.taskmanager.model.enums.Priority;
import net.weg.taskmanager.model.property.Option;
import net.weg.taskmanager.model.property.Property;
import net.weg.taskmanager.model.property.PropertyType;
import net.weg.taskmanager.model.property.values.PropertyValue;
import net.weg.taskmanager.repository.*;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

@Service
@AllArgsConstructor
public class HistoricService {
    private final HistoricRepository historicRepository;
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;
    private final PropertyRepository propertyRepository;
    private final SubTaskRepository subTaskRepository;

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

    public Task putPropertyOptionHistoric(Option newOption, Long userId, Long taskId, Long propertyId) {
        Property property = propertyRepository.findById(propertyId).get();
        User userForHistoric = userRepository.findById(userId).get();
        Task taskForHistoric = taskRepository.findById(taskId).get();

        Historic historic = new Historic(
                userForHistoric,
                userForHistoric.getName() + " criou uma opção chamada "+newOption.getValue()+" para propriedade "+property.getName(),
                LocalDateTime.now()
        );

        Historic savedHistoric = this.historicRepository.save(historic);

        taskForHistoric.getHistoric().add(savedHistoric);

        taskRepository.save(taskForHistoric);

        return taskForHistoric;
    }

    public Task deletePropertyOptionHistoric(Long userId, Long taskId, Long propertyId, Long optionId) {
        Property property = propertyRepository.findById(propertyId).get();
        User userForHistoric = userRepository.findById(userId).get();
        Task taskForHistoric = taskRepository.findById(taskId).get();

        Option option = property.getOptions().stream().filter(option1 -> option1.getId().equals(optionId)).findFirst().get();

        Historic historic = new Historic(
                userForHistoric,
                userForHistoric.getName() + " deletou a opção chamada "+option.getValue()+" da propriedade "+property.getName(),
                LocalDateTime.now()
        );

        Historic savedHistoric = this.historicRepository.save(historic);

        taskForHistoric.getHistoric().add(savedHistoric);

        taskRepository.save(taskForHistoric);

        return taskForHistoric;
    }

    public Task updateTaskFinalDateHistoric(Long taskId, Long userId, LocalDate newFinalDate) {
        User userForHistoric = userRepository.findById(userId).get();
        Task taskForHistoric = taskRepository.findById(taskId).get();

        Historic historic = new Historic(
                userForHistoric,
                userForHistoric.getName() + " mudou a data final da tarefa para "+newFinalDate.toString(),
                LocalDateTime.now()
        );

        Historic savedHistoric = this.historicRepository.save(historic);

        taskForHistoric.getHistoric().add(savedHistoric);

        taskRepository.save(taskForHistoric);

        return taskForHistoric;
    }

    public Task patchSubtaskHistoric(Subtask subtask, Long userId, Long taskId) {
        User userForHistoric = userRepository.findById(userId).get();
        Task taskForHistoric = taskRepository.findById(taskId).get();

        Historic historic = new Historic(
                userForHistoric,
                userForHistoric.getName() + " criou uma subtarefa chamada "+subtask.getName(),
                LocalDateTime.now()
        );

        Historic savedHistoric = this.historicRepository.save(historic);

        taskForHistoric.getHistoric().add(savedHistoric);

        taskRepository.save(taskForHistoric);

        return taskForHistoric;
    }

    public Task deleteSubtaskHistoric(Long subtaskId, Long taskId, Long userId) {
        User userForHistoric = userRepository.findById(userId).get();
        Task taskForHistoric = taskRepository.findById(taskId).get();
        System.out.println("Oi mano");
        System.out.println(taskForHistoric.getSubtasks());
        Subtask subtask = subTaskRepository.findById(subtaskId).get();

        Historic historic = new Historic(
                userForHistoric,
                userForHistoric.getName() + " deletou a subtarefa chamada "+subtask.getName(),
                LocalDateTime.now()
        );

        Historic savedHistoric = this.historicRepository.save(historic);

        taskForHistoric.getHistoric().add(savedHistoric);

        taskRepository.save(taskForHistoric);

        return taskForHistoric;
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
//        putTaskDTO.getProperties().forEach(property -> {
//            task.getProperties().forEach(property1 -> {
//                if (property.getCurrentOptions() != property1.getCurrentOptions()) {
//                    ArrayList<String> currentOptionListUpdate = new ArrayList<>();
//                    property.getCurrentOptions().forEach(currentOption -> {
//                        currentOptionListUpdate.add(currentOption.getValue());
//                    });
//                    String description = userForHistoric.getName() + " mudou o valor da propriedade " + property.getName() + " para " + currentOptionListUpdate.stream().toList();
//                    description = description.replace("[", "");
//                    description = description.replace("]", "");
//                    Historic historic = new Historic(userForHistoric, description, LocalDateTime.now());
//                    Historic savedHistoric = this.historicRepository.save(historic);
//
//                    task.getHistoric().add(savedHistoric);
//
//                    taskRepository.save(task);
//                }
//            });
//        });
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

    public Task updatePropertyCurrentOptions(Long userId, Long taskId, Option option,Property property) {
        User user = userRepository.findById(userId).get();
        Task task = taskRepository.findById(taskId).get();

        Historic historic =
                new Historic(user,user.getName() + " adicionou uma nova opção atual chamada "+option.getValue()+ " a propriedade "+property.getName(),LocalDateTime.now());
        Historic savedHistoric = this.historicRepository.save(historic);

        task.getHistoric().add(savedHistoric);

        return taskRepository.save(task);
    }

    public Task deletePropertyHistoric(Long taskId, Long userId, Long propertyId) {
        User user = userRepository.findById(userId).get();
        Task task = taskRepository.findById(taskId).get();
        Property property = propertyRepository.findById(propertyId).get();

        Historic historic =
                new Historic(user,user.getName() + " removeu a propriedade "+property.getName(),LocalDateTime.now());
        Historic savedHistoric = this.historicRepository.save(historic);

        task.getHistoric().add(savedHistoric);

        return taskRepository.save(task);
    }

    public Task patchFileHistoric(Long taskId, Long userId, MultipartFile file) {
        User user = userRepository.findById(userId).get();
        Task task = taskRepository.findById(taskId).get();

        Historic historic =
                new Historic(user,user.getName() + " adicionou um novo a anexo a tarefa chamado "+file.getOriginalFilename(),LocalDateTime.now());
        Historic savedHistoric = this.historicRepository.save(historic);

        task.getHistoric().add(savedHistoric);

        return taskRepository.save(task);
    }

    public Task deleteFileHistoric(Long taskId, Long userId, File file) {
        User user = userRepository.findById(userId).get();
        Task task = taskRepository.findById(taskId).get();

        Historic historic =
                new Historic(user,user.getName() + " removou o anexo "+file.getName()+" da tarefa",LocalDateTime.now());
        Historic savedHistoric = this.historicRepository.save(historic);

        task.getHistoric().add(savedHistoric);

        return taskRepository.save(task);
    }

    public Task oldPropertyCurrentOptions(Long userId, Long taskId, Option option,Property property) {
        User user = userRepository.findById(userId).get();
        Task task = taskRepository.findById(taskId).get();

        Historic historic =
                new Historic(user,user.getName() + " removeu uma opção atual chamada "+option.getValue()+ " da propriedade "+property.getName(),LocalDateTime.now());
        Historic savedHistoric = this.historicRepository.save(historic);

        task.getHistoric().add(savedHistoric);

        return taskRepository.save(task);
    }

    public Task updateCurrentStatusHistoric(User user,Task task, Status status) {
        Historic historic =
                new Historic(user, user.getName() + " mudou o valor da propriedade status para " + status.getName(), LocalDateTime.now());
        Historic savedHistoric = this.historicRepository.save(historic);

        task.getHistoric().add(savedHistoric);

        return taskRepository.save(task);
    }

    public Task updateCurrentPriorityHistoric(Task task, User user, Priority priority) {
        Historic historic =
                new Historic(user, user.getName() + " mudou o valor da propriedade prioridade para " + priority.name().toLowerCase(), LocalDateTime.now());
        Historic savedHistoric = this.historicRepository.save(historic);

        task.getHistoric().add(savedHistoric);

        return taskRepository.save(task);
    }

}
