package net.weg.model.property;

import jakarta.persistence.Entity;
import lombok.*;

import java.lang.annotation.Annotation;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EqualsAndHashCode(callSuper = true)
public class ValueDouble extends Value {

    private Double value;

    @Override
    public void setValueEspecifico(Object value) {
        this.value = Double.parseDouble((String) value);
    }
}