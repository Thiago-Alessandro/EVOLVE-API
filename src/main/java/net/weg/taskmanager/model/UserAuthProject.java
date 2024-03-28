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
@NoArgsConstructor
@IdClass(UserAuthProjectId.class)
public class UserAuthProject {
    @Id
    private Long userId;
    @Id
    private Long projectId;

    @ManyToOne
    @JoinColumn(name = "userId", insertable = false, updatable = false)
    private User user;
    @ManyToOne
    @JoinColumn(name = "projectId", insertable = false, updatable = false)
    private Task task;

    //    @OneToMany
    private Collection<Auth> authorizations;
}
