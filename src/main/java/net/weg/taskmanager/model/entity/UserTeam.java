package net.weg.taskmanager.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import net.weg.taskmanager.security.model.entity.Role;

import javax.management.InvalidAttributeValueException;
import java.util.NoSuchElementException;
import java.util.Objects;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@IdClass(UserTeamId.class)
@ToString(onlyExplicitlyIncluded = true)
public class UserTeam {
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserTeam userTeam = (UserTeam) o;
        return Objects.equals(userId, userTeam.userId) && Objects.equals(teamId, userTeam.teamId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, teamId);
    }

    @Id
    @ToString.Include
    private Long userId;
    @Id
    @ToString.Include
    private Long teamId;

    @ManyToOne()
    @JoinColumn(name = "userId", insertable = false, updatable = false)
    private User user;
    @ToString.Exclude
    @ManyToOne()
    @JoinColumn(name = "teamId", insertable = false, updatable = false)
    private Team team;

    @ManyToOne
    @ToString.Include
    private Role role;
    private boolean manager;

    public boolean getManager(){
        return manager;
    }

//    public UserTeam(Long userId, Long teamId, Role role) {
//        this.userId = userId;
//        this.teamId = teamId;
//        this.role = role;
//    }

    public UserTeam(Long userId, Long teamId, User user, Team team, Role role){
        this.userId = userId;
        this.teamId = teamId;
        this.user = user;
        this.team = team;
        this.role = role;
    }

}
