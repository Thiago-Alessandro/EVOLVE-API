package net.weg.model.property;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.weg.model.Usuario;

import java.util.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ValueAssociates extends Value {


    @OneToMany(cascade = CascadeType.ALL)
    private Collection<Usuario> values;

    @Override
    public void setValueEspecifico(Object value) {
        ObjectMapper om = new ObjectMapper();
        this.values = (Collection<Usuario>) om.convertValue(value, Collection.class);
    }

    @Override
    public Object getValue() {
        return null;
    }
}
