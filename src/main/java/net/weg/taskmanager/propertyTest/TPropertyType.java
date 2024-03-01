package net.weg.taskmanager.propertyTest;

public enum TPropertyType {

    TMultiSelect(new TValue.MultiSelect()) , DOIS;

    private TValue value;

    public TValue getValue() {
        return value;
    }

    private TPropertyType(TValue tValue) {
        this.value = tValue;
    }
}
