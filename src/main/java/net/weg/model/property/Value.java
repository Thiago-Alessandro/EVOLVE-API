package net.weg.model.property;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Value {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Integer id;
    public abstract void setValueEspecifico(Object value);
    public abstract Object getValue();
}