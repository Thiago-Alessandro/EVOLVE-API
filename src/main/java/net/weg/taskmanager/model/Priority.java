package net.weg.taskmanager.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Priority {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Integer id;
    private String name;
    private String backgroundColor;

    public Priority(String name,String backgroundColor) {
        this.name = name;
        this.backgroundColor = backgroundColor;
    }
}
