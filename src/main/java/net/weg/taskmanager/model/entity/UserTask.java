package net.weg.taskmanager.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Entity
@Data
@AllArgsConstructor
@IdClass(UserTaskId.class)
@Table(name = "user_task")
public class UserTask {

    public UserTask(){
        this.workedHours = 0;
        this.workedMinutes = 0;
        this.workedSeconds = 0;
    }

    @Id
    private Long userId;
    @Id
    private Long taskId;

    @ManyToOne
    @JoinColumn(name = "userId", insertable = false, updatable = false)
    private User user;
    @ManyToOne
    @JoinColumn(name = "taskId", insertable = false, updatable = false)
    private Task task;

    private Integer workedHours;
    private Integer workedMinutes;
    private Integer workedSeconds;

}
