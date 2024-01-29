package net.weg.taskmanager.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Status {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Integer id;
    private String name;
    private String backgroundColor;
    private String textColor;
    private Boolean enabled;

    public Status(String name, String backgroundColor, String textColor, Boolean enabled) {
        this.name = name;
        this.backgroundColor = backgroundColor;
        this.textColor = textColor;
        this.enabled = enabled;
    }

}
