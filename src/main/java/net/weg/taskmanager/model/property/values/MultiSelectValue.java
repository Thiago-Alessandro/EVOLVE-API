package net.weg.taskmanager.model.property.values;

import net.weg.taskmanager.model.property.Option;

import java.util.List;

public class MultiSelectValue extends Value<List<Option>> {
    private List<Option> selectedOptions;

    @Override
    public List<Option> getValue() {
        return selectedOptions;
    }
}