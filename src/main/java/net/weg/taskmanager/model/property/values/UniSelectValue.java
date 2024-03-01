package net.weg.taskmanager.model.property.values;

import net.weg.taskmanager.model.property.Option;

public class UniSelectValue extends Value<Option>{
    private Option selectedOption;

    @Override
    public Option getValue(){
        return selectedOption;
    }
}
