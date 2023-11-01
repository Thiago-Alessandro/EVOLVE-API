package net.weg.taskmanager.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@IdClass(UsuarioEquipe.class)
public class UsuarioEquipe {

    @Id
    private Integer usuarioId;
    @Id
    private Integer equipeId;

    @ManyToOne
    private PermissaoDePropriedades permissaoDePropriedades;
    @ManyToOne
    private PermissaoDeCards permissaoDeCards;

}
