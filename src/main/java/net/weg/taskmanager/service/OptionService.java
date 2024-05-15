package net.weg.taskmanager.service;

import lombok.AllArgsConstructor;
import net.weg.taskmanager.model.property.Option;
import net.weg.taskmanager.repository.OptionRepository;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@AllArgsConstructor
public class OptionService {

    private final OptionRepository repository;

    public Option findOptionById(Long optionId){
        Optional<Option> optionalOption = repository.findById(optionId);
        if(optionalOption.isEmpty()) throw new NoSuchElementException("Option not found");
        return optionalOption.get();
    }

}
