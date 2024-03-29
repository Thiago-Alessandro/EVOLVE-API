package net.weg.taskmanager.model.property.values;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.Data;
import net.weg.taskmanager.model.entity.User;
import net.weg.taskmanager.model.property.Option;

import java.util.List;

@Data
@Entity
public class AssociatesValue extends Value<List<User>> {
    @ManyToMany
    private List<User> value;

    @Override
    public List<User> getValue() {
        return value;
    }
}
