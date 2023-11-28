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

    NENHUMA{
        @Override
        public Integer getValue() {
            return 0;
        }
    },
    BAIXISSIMA {
        @Override
        public Integer getValue() {
            return 20;
        }
    },
    BAIXA {
        @Override
        public Integer getValue() {
            return 40;
        }
    },
    MEDIA {
        @Override
        public Integer getValue() {
            return 60;
        }
    },
    ALTA {
        @Override
        public Integer getValue() {
            return 80;
        }
    },
    URGENTE {
        @Override
        public Integer getValue() {
            return 100;
        }
    };

    public abstract Integer getValue();

}
