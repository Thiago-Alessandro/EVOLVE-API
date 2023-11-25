package net.weg.taskmanager.model.property;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Inheritance(strategy = InheritanceType.JOINED)
public class Propriedade {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Integer id;

    private String nome;
    @OneToOne(cascade = CascadeType.ALL)
    private SelectPropriedade select;

}
