package net.weg.taskmanager.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@IdClass(UsuarioTarefaId.class)
public class UsuarioTarefa {

    @Id
    private Integer usuarioId;
    @Id
    private Integer tarefaId;

    @ManyToOne
    @JoinColumn(name = "usuarioId")
    private Usuario usuario;
    @ManyToOne
    @JoinColumn(name = "tarefaId")
    private Tarefa tarefa;

    private Double horasTrabalhadas;

}
