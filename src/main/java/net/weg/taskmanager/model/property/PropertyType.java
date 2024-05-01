package net.weg.taskmanager.model.property;

import net.weg.taskmanager.model.property.values.*;

public enum PropertyType {

    MultiSelectValue(new MultiSelectValue()),
    UniSelectValue(new UniSelectValue()),
    IntegerValue(new IntegerValue()),
    DoubleValue(new DoubleValue()),
    DataValue(new DataValue()),
    TextValue(new TextValue()),
    Associates(new AssociatesValue());

    private Value<?> value;

    public Value<?> getValue() {
        return value;
    }

    private PropertyType(Value<?> value) {
        this.value = value;
    }

}