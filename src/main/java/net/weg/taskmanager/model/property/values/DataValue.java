package net.weg.taskmanager.model.property.values;

public class DataValue extends Value<String>{

    private String dataValue;
    @Override
    protected String getValue() {
        return dataValue;
    }
}
