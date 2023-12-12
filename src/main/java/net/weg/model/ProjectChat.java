package net.weg.model;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProjectChat extends Chat{

    @OneToOne(mappedBy = "chat")
    //ao inserir, atualizar ou remover um projeto terá de ser criado, atualizado ou excuido seu chat
    private Projeto project;

}
