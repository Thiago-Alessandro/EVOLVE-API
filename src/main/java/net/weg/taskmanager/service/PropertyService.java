package net.weg.taskmanager.service;

import lombok.AllArgsConstructor;
import net.weg.taskmanager.model.Project;
import net.weg.taskmanager.model.property.Property;
import net.weg.taskmanager.repository.PropertyRepository;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PropertyService {

    private final PropertyRepository repository;

    public void createProjectPropertiesIfNotExists(Project project){
        if (project.getProperties() != null) {
            for (Property propriedade : project.getProperties()) {
                if(!doesPropertyExists(propriedade)){
                    //Adiciona e salva a propriedade com a referencia do projeto
                    propriedade.setProject(project);
                    repository.save(propriedade);
                }
            }
        }
    }

    private boolean doesPropertyExists(Property property){
        return repository.existsById(property.getId());
    }

}
