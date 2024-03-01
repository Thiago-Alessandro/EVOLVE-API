package net.weg.taskmanager.propertyTest;

import java.util.List;

public interface TValue <T>{
    T getValue();


    public class MultiSelect implements TValue<List<TOption>> {
        private List<TOption> selectedOptions;

        @Override
        public List<TOption> getValue() {
            return selectedOptions;
        }
    }


}
