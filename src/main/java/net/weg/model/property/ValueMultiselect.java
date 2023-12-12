package net.weg.model.property;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ValueMultiselect extends Value {


    @OneToMany(cascade = CascadeType.ALL)
    private Collection<SelectOption> values;

    @Override
    public void setValueEspecifico(Object value) {
        ObjectMapper om = new ObjectMapper();
        this.values = (Collection<SelectOption>) om.convertValue(value, Collection.class);
    }

    @Override
    public Object getValue() {
        return null;
    }
}