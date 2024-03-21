package net.weg.taskmanager.model.property.values;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.weg.taskmanager.model.property.Option;

import java.util.List;

@Entity
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "propertyType", visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = DataValue.class, name = "DataValue"),
        @JsonSubTypes.Type(value = TextValue.class, name = "TextValue"),
        @JsonSubTypes.Type(value = IntegerValue.class, name = "IntegerValue")
})
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Value <T>{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String propertyType;
    public abstract T getValue();

    public Value() {

    }

}
