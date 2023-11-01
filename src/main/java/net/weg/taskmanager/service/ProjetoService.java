package net.weg.taskmanager.service;

import lombok.AllArgsConstructor;
import net.weg.taskmanager.model.PermissaoDePropriedades;
import net.weg.taskmanager.model.Projeto;
import net.weg.taskmanager.repository.PermissaoDePropriedadesRepository;
import net.weg.taskmanager.repository.ProjetoRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@AllArgsConstructor
public class ProjetoService {

    private final ProjetoRepository projetoRepository;

    public Projeto findById(Integer id){return projetoRepository.findById(id).get();}

    public Collection<Projeto> findAll(){return projetoRepository.findAll();}

    public void delete(Integer id){projetoRepository.deleteById(id);}

    public Projeto create(Projeto projeto){return projetoRepository.save(projeto);}
    public Projeto update(Projeto projeto){return projetoRepository.save(projeto);}

}
