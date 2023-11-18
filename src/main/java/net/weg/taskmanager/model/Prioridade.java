package net.weg.taskmanager.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//@Entity
//@AllArgsConstructor
@NoArgsConstructor
public enum Prioridade {

    NENHUMA,
    MiNIMA,
    BAIXA,
    MEDIA,
    ALTA,
    CRITICA

}
