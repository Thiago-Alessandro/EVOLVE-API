package net.weg.taskmanager.model.property;

import net.weg.taskmanager.model.property.values.Value;

public enum PropertyType {

    MultiSelect(new Value.MultiSelect()),
    UniSelect(new Value.UniSelect());


    private Value value;

    public Value getValue() {
        return value;
    }

    private PropertyType(Value value) {
        this.value = value;
    }

}