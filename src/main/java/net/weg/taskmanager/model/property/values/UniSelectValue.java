package net.weg.taskmanager.model.property.values;

import jakarta.persistence.*;
import lombok.Data;
import net.weg.taskmanager.model.property.Option;
@Data
@Entity
public class UniSelectValue extends Value<Option>{
    @ManyToOne
    private Option value;

    @Override
    public Option getValue(){
        return value;
    }
}
