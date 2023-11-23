package net.weg.taskmanager.model.property;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Opcao {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Integer id;

    private String valor;

}
