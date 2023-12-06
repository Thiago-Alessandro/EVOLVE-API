package net.weg.model.property;

public enum PropertyType {

    TEXT {
        @Override
        Class<ValueText> getNewValue() {
            return ValueText.class;
        }
    },
    DOUBLE {
        @Override
        Class<ValueDouble> getNewValue() {
            return ValueDouble.class;
        }
    },
    INTEGER {
        @Override
        Class<Value> getNewValue() {
            return null;
        }
    },
    DATE {
        @Override
        Class<Value> getNewValue() {
            return null;
        }
    },
    MULTISELECT {
        @Override
        Class<Value> getNewValue() {
            return null;
        }
    },
    UNISELECT {
        @Override
        Class<Value> getNewValue() {
            return null;
        }
    },
    ASSOCIATES {
        @Override
        Class<Value> getNewValue() {
            return null;
        }
    };

    abstract Class<? extends Value> getNewValue();
}