package net.weg.taskmanager.model.property.values;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class DoubleValue extends Value<Double>{

    private Double value;
    @Override
    public Double getValue() {
        return value;
    }
}
