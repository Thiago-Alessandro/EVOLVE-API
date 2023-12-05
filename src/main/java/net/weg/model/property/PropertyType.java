package net.weg.model.property;

public enum PropertyType {

    TEXT {
        @Override
        Value getNewValue() {
            return new ValueText();
        }
    },
    DOUBLE {
        @Override
        Value getNewValue() {
            return new ValueDouble();
        }
    },
    INTEGER {
        @Override
        Value getNewValue() {
            return null;
        }
    },
    DATE {
        @Override
        Value getNewValue() {
            return null;
        }
    },
    MULTISELECT {
        @Override
        Value getNewValue() {
            return null;
        }
    },
    UNISELECT {
        @Override
        Value getNewValue() {
            return null;
        }
    },
    ASSOCIATES {
        @Override
        Value getNewValue() {
            return null;
        }
    };

    abstract Value getNewValue();
}