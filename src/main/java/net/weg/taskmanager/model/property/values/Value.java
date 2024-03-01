package net.weg.taskmanager.model.property.values;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.weg.taskmanager.model.property.Option;

import java.util.List;

@Entity
public abstract class Value <T>{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    protected abstract T getValue();

}
