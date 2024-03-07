package net.weg.taskmanager.model.property.values;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.weg.taskmanager.model.property.Property;

@Entity
@AllArgsConstructor
@Data
@NoArgsConstructor
public class PropertyValue {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @OneToOne(cascade = CascadeType.ALL)
    private Value<?> value;
    @ManyToOne
    @JsonIgnore
    private Property property;

//    public PropertyValue() {
//        if(property != null) {
//            value = property.getPropertyType().getValue();
//        }
//    }
}
