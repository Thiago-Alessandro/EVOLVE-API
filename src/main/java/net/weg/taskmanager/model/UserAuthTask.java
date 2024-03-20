package net.weg.taskmanager.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.weg.taskmanager.security.model.enums.Auth;

import java.util.Collection;

@Entity
@AllArgsConstructor
@Data
@IdClass(UserAuthTaskId.class)
@NoArgsConstructor
public class UserAuthTask {
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

    @OneToMany
    private Collection<Auth> authorizations = null;

}
