package net.weg.taskmanager.model.property.values;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.weg.taskmanager.model.property.Option;

import java.util.List;


public interface Value <T>{

    T getValue();

    public class MultiSelect implements Value<List<Option>> {
        private List<Option> selectedOptions;

        @Override
        public List<Option> getValue() {
            return selectedOptions;
        }
    }

    public class UniSelect implements Value<Option> {
        private Option selectedOption;

        @Override
        public Option getValue() {
            return selectedOption;
        }
    }

}
