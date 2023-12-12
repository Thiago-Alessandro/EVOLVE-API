package net.weg.model.property;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Inheritance(strategy = InheritanceType.JOINED)
public class Property {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Integer id;

    private String name;

    @Enumerated(EnumType.STRING)
    private PropertyType type;

    //realmente deixar aq? confirmar com o prof
    @OneToMany
    private Collection<SelectOption> possibleOptions;
}