package net.weg.taskmanager.model.property;

import net.weg.taskmanager.model.property.values.Value;
import net.weg.taskmanager.model.property.values.MultiSelectValue;
import net.weg.taskmanager.model.property.values.UniSelectValue;
import net.weg.taskmanager.model.property.values.IntegerValue;
import net.weg.taskmanager.model.property.values.DoubleValue;
import net.weg.taskmanager.model.property.values.DataValue;
import net.weg.taskmanager.model.property.values.TextValue;

public enum PropertyType {

    MultiSelectValue(new MultiSelectValue()),
    UniSelectValue(new UniSelectValue()),
    IntegerValue(new IntegerValue()),
    DoubleValue(new DoubleValue()),
    DataValue(new DataValue()),
    TextValue(new TextValue());

    private Value<?> value;

    public Value<?> getValue() {
        return value;
    }

    private PropertyType(Value<?> value) {
        this.value = value;
    }

}