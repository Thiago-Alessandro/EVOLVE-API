package net.weg.taskmanager.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.weg.taskmanager.security.model.enums.Auth;

import java.util.Collection;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@IdClass(UserAuthProjectId.class)
public class UserAuthTeam {
    @Id
    private Long userId;
    @Id
    private Long teamId;

    @ManyToOne
    @JoinColumn(name = "userId",insertable = false, updatable = false)
    private User user;
    @ManyToOne
    @JoinColumn(name = "teamId",insertable = false,updatable = false)
    private Team team;

    @OneToMany
    private Collection<Auth> authorizations;
}