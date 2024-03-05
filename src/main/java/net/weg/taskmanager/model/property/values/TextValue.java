package net.weg.taskmanager.model.property.values;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class TextValue extends Value<String>{
    private String value;
    @Override
    public String getValue() {
        return value;
    }
}
