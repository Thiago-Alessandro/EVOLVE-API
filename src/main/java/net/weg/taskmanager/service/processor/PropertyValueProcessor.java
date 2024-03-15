package net.weg.taskmanager.service.processor;
import net.weg.taskmanager.model.property.Property;
import net.weg.taskmanager.model.property.values.PropertyValue;

import java.util.ArrayList;

public class PropertyValueProcessor {

    private PropertyValue resolvingPropertyValue;
    private  String propertyValueClassName = PropertyValue.class.getSimpleName();
    private  ArrayList<String> resolvingCascade;

    public static PropertyValueProcessor getInstance(){
        return new PropertyValueProcessor();
    }

    public PropertyValue resolvePropertyValue(PropertyValue propertyValue, ArrayList<String> _resolvingCascade){

        resolvingCascade = _resolvingCascade;
        resolvingCascade.add(propertyValueClassName);

        resolvingPropertyValue = propertyValue;

        resolvePropertyValueProperty();

        resolvingCascade.remove(propertyValueClassName);

        return resolvingPropertyValue;
    }

    private void resolvePropertyValueProperty(){
        if(resolvingPropertyValue.getProperty()!=null){
            if(resolvingCascade.contains(Property.class.getSimpleName())){
                resolvingPropertyValue.setProperty(null);
                return;
            }
            PropertyProcessor.getInstance().resolveProperty(resolvingPropertyValue.getProperty(), resolvingCascade);
        }
    }

}
