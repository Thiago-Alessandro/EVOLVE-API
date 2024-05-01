package net.weg.taskmanager.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.weg.taskmanager.security.model.entity.Role;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@IdClass(UserTeamId.class)
public class UserTeam {

    @Id
    private Long userId;
    @Id
    private Long teamId;

    @ManyToOne
    @JoinColumn(name = "userId", insertable = false, updatable = false)
    private User user;
    @ManyToOne
    @JoinColumn(name = "teamId", insertable = false, updatable = false)
    private Team team;

    @ManyToOne
    private Role role;
    private boolean isManager;

    public UserTeam(Long userId, Long teamId, Role role) {
        this.userId = userId;
        this.teamId = teamId;
        this.role = role;
    }

    public UserTeam(Long userId, Long teamId, User user, Team team, Role role) {
        this.userId = userId;
        this.teamId = teamId;
        this.user = user;
        this.team = team;
        this.role = role;
    }

}
