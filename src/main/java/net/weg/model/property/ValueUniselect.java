package net.weg.model.property;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)

public class ValueUniselect extends Value{
    @OneToOne
    private SelectOption value;
    @Override
    public void setValueEspecifico(Object value) {
        this.value = (SelectOption) value;
    }

    @Override
    public Object getValue() {
        return null;
    }
}
