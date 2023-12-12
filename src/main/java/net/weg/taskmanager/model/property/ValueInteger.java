package net.weg.model.property;

import jakarta.persistence.Entity;
import lombok.*;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ValueInteger extends Value {

    private Integer value;

    @Override
    public void setValueEspecifico(Object value) {
        this.value = Integer.parseInt((String) value);
    }
}
