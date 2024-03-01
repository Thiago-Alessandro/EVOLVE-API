package net.weg.taskmanager.model.property.values;

public class TextValue extends Value<String>{

    private String textValue;
    @Override
    protected String getValue() {
        return textValue;
    }
}
