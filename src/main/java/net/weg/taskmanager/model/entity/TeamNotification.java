package net.weg.taskmanager.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeamNotification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToMany
    private Collection<User> notificatedUsers;
    @ManyToOne
    private User actionUser;
    @ManyToOne
    @JsonIgnore
    private Project project;
    private boolean readed;
    private String value;
    private String timeHour;
    private String timeDayAndMonth;
}
