package net.weg.model.property;

import jakarta.persistence.Entity;
import lombok.*;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ValueText extends Value {

    private String value;

    @Override
    public void setValueEspecifico(Object value) {
        this.value = (String) value;
    }
}