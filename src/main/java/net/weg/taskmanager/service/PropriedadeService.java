package net.weg.taskmanager.service;

import lombok.AllArgsConstructor;
import net.weg.taskmanager.model.Projeto;
import net.weg.taskmanager.model.Propriedade;
import net.weg.taskmanager.repository.ProjetoRepository;
import net.weg.taskmanager.repository.PropriedadeRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@AllArgsConstructor
public class PropriedadeService {

    private final PropriedadeRepository propriedadeRepository;

    public Propriedade findById(Integer id){return propriedadeRepository.findById(id).get();}

    public Collection<Propriedade> findAll(){return propriedadeRepository.findAll();}

    public void delete(Integer id){propriedadeRepository.deleteById(id);}

    public Propriedade create(Propriedade propriedade){return propriedadeRepository.save(propriedade);}
    public Propriedade update(Propriedade propriedade){return propriedadeRepository.save(propriedade);}

}
