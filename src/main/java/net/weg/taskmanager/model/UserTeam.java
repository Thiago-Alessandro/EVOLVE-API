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
    private Integer userId;
    @Id
    private Integer teamId;

    @ManyToOne
    private PermissionOfProperties permissionOfProperties;
    @ManyToOne
    private PermissionOfCards permissionOfCards;

}
