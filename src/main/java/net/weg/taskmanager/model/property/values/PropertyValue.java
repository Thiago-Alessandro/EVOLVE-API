package net.weg.taskmanager.model.property.values;

import net.weg.taskmanager.model.property.Property;

public class PropertyValue {
    private Value value;
    private Property property;

    public PropertyValue() {
        value = property.getPropertyType().getValue();
    }
}
