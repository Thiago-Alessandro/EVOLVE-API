package net.weg.taskmanager.service;

import lombok.AllArgsConstructor;
import net.weg.taskmanager.model.entity.Project;
import net.weg.taskmanager.model.property.Property;
import net.weg.taskmanager.repository.PropertyRepository;
import org.springframework.stereotype.Service;

import javax.management.InvalidAttributeValueException;
import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PropertyService {

    private final PropertyRepository repository;

    public void createProjectPropertiesIfNotExists(Project project) throws InvalidAttributeValueException {
        Collection<Property> properties = project.getProperties();
        if (properties == null) throw new InvalidAttributeValueException();
//        properties.forEach(property -> property.setProject(project));
        properties.stream()
                .filter(this::doesPropertyNotExists)
                .forEach(repository::save);
    }

    public Property findPropertyById(Long propertyId) {
        Optional<Property> optionalProperty = repository.findById(propertyId);
        if (optionalProperty.isEmpty()) throw new NoSuchElementException("Property not found");
        return optionalProperty.get();
    }

    private boolean doesPropertyNotExists(Property property){
        return !repository.existsById(property.getId());
    }

}
