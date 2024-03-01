package net.weg.taskmanager.model.property;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.weg.taskmanager.model.Project;
import net.weg.taskmanager.model.Task;
import net.weg.taskmanager.model.property.values.PropertyValue;

import java.util.Collection;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Property {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Integer id;

    private String name;

    @ManyToOne
    @JsonIgnore
    private Project project;  // Possivelmente relacionada

    @OneToMany(mappedBy = "property")
    private Collection<PropertyValue> propertyValues; //

    @Enumerated(EnumType.STRING)
    private PropertyType propertyType;

    @ManyToOne
    private Collection<Option> options;

}
