package net.weg.taskmanager.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.weg.taskmanager.security.model.enums.Auth;

import java.util.Collection;

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
//    @OneToMany
//    private Collection<Auth> authorizations;

    private Integer workedHours;
    private Integer workedMinutes;
    private Integer workedSeconds;

}
