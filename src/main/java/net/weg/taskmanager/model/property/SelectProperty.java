package net.weg.taskmanager.model.property;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SelectProperty {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(cascade = CascadeType.ALL)
    private Collection<SelectOption> possibleOptions;

//    @OneToOne(mappedBy = "select")
//    @JsonIgnore
//    private Propriedade propriedade;

    private Boolean uniqueValue;

}
