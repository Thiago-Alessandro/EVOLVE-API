package net.weg.taskmanager.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Status {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE )
    private Integer id;
    private String nome;
    private String corFundo;
    private String corTexto;
    private Boolean enabled;

    public Status(String nome, String corFundo, String corTexto, Boolean enabled){
        this.nome = nome;
        this.corFundo = corFundo;
        this.corTexto = corTexto;
        this.enabled = enabled;
    }

}
