package net.weg.taskmanager.model.property.values;

import jakarta.persistence.Entity;
import lombok.Data;
import net.weg.taskmanager.model.entity.User;

import java.util.List;

@Data
@Entity
public class AssociatesValue extends Value<List<User>> {
    private List<User> value;
}
