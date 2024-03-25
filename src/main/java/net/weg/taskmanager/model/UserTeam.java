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

}
