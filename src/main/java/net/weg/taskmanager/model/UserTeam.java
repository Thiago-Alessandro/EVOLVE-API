package net.weg.taskmanager.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@IdClass(UserTeam.class)
public class UserTeam {

    @Id
    private Long userId;
    @Id
    private Long teamId;

    @ManyToOne
    private PropertiesPermission permissionOfProperties;
    @ManyToOne
    private CardsPermission permissionOfCards;

}
