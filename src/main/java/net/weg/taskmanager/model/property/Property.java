package net.weg.taskmanager.model.property;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.weg.taskmanager.model.entity.Project;
import net.weg.taskmanager.model.property.values.PropertyValue;

import java.util.Collection;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Property {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "property",orphanRemoval = true)
    private Collection<PropertyValue> propertyValues; //

    @Enumerated(EnumType.STRING)
    private PropertyType propertyType;

    @OneToMany
    private Collection<Option> options;

    @OneToMany
    private Collection<Option> currentOptions;

    private boolean global = false;

}
