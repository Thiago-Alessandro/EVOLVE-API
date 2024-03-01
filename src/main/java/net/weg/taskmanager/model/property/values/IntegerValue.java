package net.weg.taskmanager.model.property.values;

public class IntegerValue extends Value<Integer>{
    private Integer integerValue;
    @Override
    protected Integer getValue() {
        return integerValue;
    }
}
