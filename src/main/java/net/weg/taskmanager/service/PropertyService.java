package net.weg.taskmanager.service;

import lombok.AllArgsConstructor;
import net.weg.taskmanager.model.Project;
import net.weg.taskmanager.model.property.Property;
import net.weg.taskmanager.repository.PropertyRepository;
import org.springframework.stereotype.Service;

import javax.management.InvalidAttributeValueException;
import java.util.Collection;

@Service
@AllArgsConstructor
public class PropertyService {

    private final PropertyRepository repository;

    public void createProjectPropertiesIfNotExists(Project project) throws InvalidAttributeValueException {
        Collection<Property> properties = project.getProperties();
        if (properties == null) throw new InvalidAttributeValueException();
        properties.forEach(property -> property.setProject(project));
        properties.stream()
                .filter(this::doesPropertyNotExists)
                .forEach(repository::save);
    }

    private boolean doesPropertyNotExists(Property property){
        return !repository.existsById(property.getId());
    }

}
