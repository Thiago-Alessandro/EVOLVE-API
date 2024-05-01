package net.weg.taskmanager.service.processor;

import lombok.NoArgsConstructor;
import net.weg.taskmanager.model.entity.Project;
import net.weg.taskmanager.model.property.Property;
import net.weg.taskmanager.model.property.values.PropertyValue;

import java.util.ArrayList;

@NoArgsConstructor
public class PropertyProcessor {

    private Property resolvingProperty;
    private  String propertyClassName = Property.class.getSimpleName();
    private ArrayList<String> resolvingCascade;

    public static PropertyProcessor getInstance(){
        return new PropertyProcessor();
    }

    public Property resolveProperty(Property property, ArrayList<String> _resolvingCascade){

        resolvingCascade = _resolvingCascade;
        resolvingCascade.add(propertyClassName);

        resolvingProperty = property;

        resolvePropertyPropertyValue();
        resolvePropertyProject();

        resolvingCascade.remove(propertyClassName);

        return resolvingProperty;
    }

    public Property resolveProperty(Property property){
        return resolveProperty(property, new ArrayList<>());
    }

    private void resolvePropertyPropertyValue(){
        if(resolvingProperty.getPropertyValues()!=null){
            if(resolvingCascade.contains(PropertyValue.class.getSimpleName())){
                resolvingProperty.setPropertyValues(null);
                return;
            }
            resolvingProperty.getPropertyValues()
                    .forEach(propertyValue ->  PropertyValueProcessor.getInstance().resolvePropertyValue(propertyValue, resolvingCascade));
        }
    }
    private void resolvePropertyProject(){
        if(resolvingProperty.getProject()!=null){
            if(resolvingCascade.contains(Project.class.getSimpleName())){
                resolvingProperty.setProject(null);
                return;
            }
            ProjectProcessor.getInstance().resolveProject(resolvingProperty.getProject(), resolvingCascade);
        }
    }

}
