package net.weg.model.property;

import jakarta.persistence.Entity;
import lombok.*;

import java.util.Date;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ValueDate extends Value{
    private Date value;
    @Override
    public void setValueEspecifico(Object value) {
        this.value = (Date) value;
    }

    @Override
    public Object getValue() {
        return null;
    }
}
