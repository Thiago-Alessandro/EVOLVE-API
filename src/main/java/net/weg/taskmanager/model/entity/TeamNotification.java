package net.weg.taskmanager.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.weg.taskmanager.model.dto.get.GetUserDTO;
import net.weg.taskmanager.model.dto.shortDTOs.ShortProjectDTO;
import net.weg.taskmanager.model.dto.shortDTOs.ShortUserDTO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeamNotification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToMany
    private Collection<User> notificatedUsers;
    @ManyToOne
    private User actionUser;
    private boolean readed;
    private String value;
    private LocalDateTime dateTime;

    public TeamNotification(User userAction, Collection<User> users, boolean b, String s, LocalDateTime now) {
        this.actionUser = userAction;
        this.notificatedUsers = users;
        this.readed = b;
        this.value = s;
        this.dateTime = now;
    }
}
