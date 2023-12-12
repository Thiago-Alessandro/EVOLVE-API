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
        Class<ValueInteger> getNewValue() {
            return ValueInteger.class;
        }
    },
    DATE {
        @Override
        Class<ValueDate> getNewValue() {
            return ValueDate.class;
        }
    },
    MULTISELECT {
        @Override
        Class<ValueMultiselect> getNewValue() {
            return ValueMultiselect.class;
        }
    },
    UNISELECT {
        @Override
        Class<ValueUniselect> getNewValue() {
            return ValueUniselect.class;
        }
    },
    ASSOCIATES {
        @Override
        Class<ValueAssociates> getNewValue() {
            return ValueAssociates.class;
        }
    };

    abstract Class<? extends Value> getNewValue();
}