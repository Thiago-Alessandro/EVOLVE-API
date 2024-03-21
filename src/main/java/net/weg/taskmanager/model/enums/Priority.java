package net.weg.taskmanager.model.enums;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

//@Entity
//@AllArgsConstructor
//@NoArgsConstructor
public enum Priority {
        NENHUMA("#cccccc"),
        MUITO_BAIXA("#6bbcfa"),
        BAIXA("#4db339"),
        MEDIA("#f5e020"),
        ALTA("#f57520"),
        URGENTE("#e32910");

//    @Id
//    @GeneratedValue(strategy = GenerationType.TABLE)
//    private Integer id;
//    String name;
    public String backgroundColor;

    Priority(String backgroundColor) {
//        this.name = name;
        this.backgroundColor = backgroundColor;
    }
}
