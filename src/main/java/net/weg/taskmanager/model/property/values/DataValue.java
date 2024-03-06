package net.weg.taskmanager.model.property.values;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.Getter;


@Data
@Entity
public class DataValue extends Value<String>{

    private String value;

}
