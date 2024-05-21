package net.weg.taskmanager.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.weg.taskmanager.model.entity.Project;
import net.weg.taskmanager.model.entity.User;
import net.weg.taskmanager.security.model.entity.Role;

import java.util.Objects;

@Entity
@AllArgsConstructor
@Data
@NoArgsConstructor
@IdClass(UserProjectId.class)
public class UserProject {

    @Id
    private Long userId;
    @Id
    private Long projectId;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "userId", insertable = false)
    private User user;
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "projectId", insertable = false)
    private Project project;

    @ManyToOne
    private Role role;

    private boolean manager = false;
//    private boolean isAdm;

    public UserProject(Long userId, Long projectId, Role acessProfile) {
        this.userId = userId;
        this.projectId = projectId;
        this.role = acessProfile;
    }

    public UserProject(User user, Project project) {
        this.userId = user.getId();
        this.projectId = project.getId();
        this.user = user;
        this.project = project;
        this.role = project.getDefaultRole();
    }
}
