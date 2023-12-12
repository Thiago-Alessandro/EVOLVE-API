package net.weg.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioTarefaId {

    private Integer usuarioId;
    private Integer tarefaId;

}
