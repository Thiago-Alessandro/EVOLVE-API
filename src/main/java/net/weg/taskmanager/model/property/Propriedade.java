package net.weg.taskmanager.model.property;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.weg.taskmanager.model.Projeto;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Propriedade {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Integer id;

//    private T valor;

//    @ManyToOne
//    private Projeto projeto;

    private String nome;

}
