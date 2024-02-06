package net.weg.taskmanager.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@IdClass(UserTaskId.class)
public class UserTask {

    @Id
    private Integer userId;
    @Id
    private Integer taskId;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;
    @ManyToOne
    @JoinColumn(name = "taskId")
    private Task task;

    private Integer workedHours;
    private Integer workedMinutes;
    private Integer workedSeconds;

}
