package net.weg.taskmanager.model.property.values;

public class DoubleValue extends Value<Double>{

    private Double doubleValue;
    @Override
    protected Double getValue() {
        return doubleValue;
    }
}
