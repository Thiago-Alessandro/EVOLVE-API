package net.weg.taskmanager.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Historic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JsonIgnore
    private User user;
    private String description;
    private LocalDateTime dateTime;

    public Historic(User userForHistoric, String s, LocalDateTime now) {
        this.user = userForHistoric;
        this.description = s;
        this.dateTime = now;
    }
}
