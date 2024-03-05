package net.weg.taskmanager.model.property.values;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class IntegerValue extends Value<Integer>{

    private Integer value;
    @Override
    public Integer getValue() {
        return value;
    }
}
