package net.weg.taskmanager.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.*;
import net.weg.taskmanager.model.abstracts.Chat;


@EqualsAndHashCode(callSuper = true)
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString(onlyExplicitlyIncluded = true)
public class ProjectChat extends Chat {

    @OneToOne(optional = false)
    @JoinColumn(unique = true)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    //ao inserir, atualizar ou remover um projeto terá de ser criado, atualizado ou excuido seu chat
    private Project project;



}
