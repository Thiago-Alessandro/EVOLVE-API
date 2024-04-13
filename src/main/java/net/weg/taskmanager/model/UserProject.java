package net.weg.taskmanager.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.weg.taskmanager.security.model.entity.ProfileAcess;

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
    @JoinColumn(name = "userId", insertable = false, updatable = false)
    private User user;
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "projectId", insertable = false, updatable = false)
    private Project project;

    @ManyToOne
    private ProfileAcess acessProfile;

    public UserProject(Long userId, Long projectId, ProfileAcess acessProfile) {
        this.userId = userId;
        this.projectId = projectId;
        this.acessProfile = acessProfile;
    }

    public UserProject(User user, Project project) {
        this.userId = user.getId();
        this.projectId = project.getId();
        this.user = user;
        this.project = project;
        this.acessProfile = project.getDefaultProfileAccess();
    }
}
