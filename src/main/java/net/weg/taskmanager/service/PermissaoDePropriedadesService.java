package net.weg.taskmanager.service;

import lombok.AllArgsConstructor;
import net.weg.taskmanager.model.PermissaoDeCards;
import net.weg.taskmanager.model.PermissaoDePropriedades;
import net.weg.taskmanager.repository.PermissaoDeCardsRepository;
import net.weg.taskmanager.repository.PermissaoDePropriedadesRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@AllArgsConstructor
public class PermissaoDePropriedadesService {

    private final PermissaoDePropriedadesRepository permissaoDePropriedadesRepository;

    public PermissaoDePropriedades findById(Integer id){return permissaoDePropriedadesRepository.findById(id).get();}

    public Collection<PermissaoDePropriedades> findAll(){return permissaoDePropriedadesRepository.findAll();}

    public void delete(Integer id){permissaoDePropriedadesRepository.deleteById(id);}

    public PermissaoDePropriedades create(PermissaoDePropriedades permissaoDePropriedades){return permissaoDePropriedadesRepository.save(permissaoDePropriedades);}
    public PermissaoDePropriedades update(PermissaoDePropriedades permissaoDePropriedades){return permissaoDePropriedadesRepository.save(permissaoDePropriedades);}

}
