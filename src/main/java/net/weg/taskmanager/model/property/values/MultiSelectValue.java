package net.weg.taskmanager.model.property.values;

import jakarta.persistence.*;
import lombok.Data;
import net.weg.taskmanager.model.property.Option;

import java.util.List;
@Data
@Entity
public class MultiSelectValue extends Value<List<Option>> {

    @ManyToMany
    private List<Option> value;

    @Override
    public List<Option> getValue() {
        return value;
    }
}