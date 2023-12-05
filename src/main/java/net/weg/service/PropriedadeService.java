package net.weg.service;

import lombok.AllArgsConstructor;
import net.weg.model.property.Property;
import net.weg.repository.PropriedadeRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@AllArgsConstructor
public class PropriedadeService implements IService<Property>{
    private final PropriedadeRepository propriedadeRepository;

    public Property findById(Integer id) {
        return propriedadeRepository.findById(id).get();
    }

    public Collection<Property> findAll() {
        return propriedadeRepository.findAll();
    }

    public void delete(Integer id) {
        propriedadeRepository.deleteById(id);
    }

    public Property create(Property property) {
        return update(property);
    }

    public Property update(Property property) {
        return propriedadeRepository.save(property);
    }
}