package net.weg.taskmanager.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.weg.taskmanager.model.entity.User;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class NotificationsConfig {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private boolean taskProperties = true;
    private boolean taskDescription = true;
    private boolean taskName = true;
    private boolean taskAssociates = true;
    private boolean taskSubtasks = true;
    private boolean taskComments = true;
    private boolean taskAttachments = true;
    private boolean taskFinalDate = true;
    private boolean taskStatus = true;
    private boolean taskPriority = true;

    private boolean projectDashboards = true;
    private boolean projectComments = true;
    private boolean projectStatus = true;
    private boolean projectParticipants = true;
    private boolean projectInfos = true;

    private boolean taskAll = true;
    private boolean projectAll = true;

    @OneToOne
    @JsonIgnore
    private User user;

}
