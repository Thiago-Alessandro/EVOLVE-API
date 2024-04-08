package net.weg.taskmanager.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.weg.taskmanager.security.model.entity.ProfileAcess;

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
    private PropertiesPermission permissionOfProperties;
    @ManyToOne
    private CardsPermission permissionOfCards;
    //    @OneToMany
//    private Collection<Auth> authorizations;

    @ManyToOne
    @JoinColumn(name = "userId", insertable = false, updatable = false)
    private User user;
    @ManyToOne
    @JoinColumn(name = "teamId", insertable = false, updatable = false)
    private Team team;

    @ManyToOne
    private ProfileAcess acessProfile;

    public UserTeam(Long userId, Long teamId, ProfileAcess acessProfile) {
        this.userId = userId;
        this.teamId = teamId;
        this.acessProfile = acessProfile;
    }
}
