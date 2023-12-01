package net.weg.taskmanager.service;

import lombok.AllArgsConstructor;
import net.weg.taskmanager.model.property.Property;
import net.weg.taskmanager.repository.PropriedadeRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@AllArgsConstructor
public class PropriedadeService {

    private final PropriedadeRepository propriedadeRepository;

    public Property findById(Integer id){return propriedadeRepository.findById(id).get();}

    public Collection<Property> findAll(){return propriedadeRepository.findAll();}

    public void delete(Integer id){propriedadeRepository.deleteById(id);}

    public Property create(Property propriedade){return propriedadeRepository.save(propriedade);}
    public Property update(Property propriedade){return propriedadeRepository.save(propriedade);}

}
