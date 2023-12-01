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
public class SelectPropriedade {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Integer id;

    @OneToMany(cascade = CascadeType.ALL)
    private Collection<Text> opcoesPossiveis;

//    @OneToOne(mappedBy = "select")
//    @JsonIgnore
//    private Propriedade propriedade;

    private Boolean valorUnico;

}
