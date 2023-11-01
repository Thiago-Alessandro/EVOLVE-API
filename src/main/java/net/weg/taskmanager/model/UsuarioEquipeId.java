package net.weg.taskmanager.model;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioEquipeId {

    private Integer usuarioId;
    private Integer equipeId;

}
