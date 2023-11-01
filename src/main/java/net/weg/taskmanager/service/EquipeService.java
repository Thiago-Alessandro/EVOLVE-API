package net.weg.taskmanager.service;

import lombok.AllArgsConstructor;
import net.weg.taskmanager.model.Equipe;
import net.weg.taskmanager.repository.EquipeRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@AllArgsConstructor
public class EquipeService {

    private final EquipeRepository equipeRepository;

    public Equipe findById(Integer id){return equipeRepository.findById(id).get();}

    public Collection<Equipe> findAll(){return equipeRepository.findAll();}

    public void delete(Integer id){equipeRepository.deleteById(id);}

    public Equipe create(Equipe equipe){return equipeRepository.save(equipe);}
    public Equipe update(Equipe equipe){return equipeRepository.save(equipe);}

}
