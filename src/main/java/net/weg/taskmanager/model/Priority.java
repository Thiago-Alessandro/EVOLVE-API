package net.weg.taskmanager.model;

import lombok.NoArgsConstructor;

//@Entity
//@AllArgsConstructor
@NoArgsConstructor
public enum Priority {

    NONE{
        @Override
        public Integer getValue() {
            return 0;
        }
    },
    VERY_LOW {
        @Override
        public Integer getValue() {
            return 20;
        }
    },
    LOW {
        @Override
        public Integer getValue() {
            return 40;
        }
    },
    AVERAGE {
        @Override
        public Integer getValue() {
            return 60;
        }
    },
    HIGH {
        @Override
        public Integer getValue() {
            return 80;
        }
    },
    URGENT {
        @Override
        public Integer getValue() {
            return 100;
        }
    };

    public abstract Integer getValue();

}
