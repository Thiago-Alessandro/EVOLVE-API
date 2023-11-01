package net.weg.taskmanager.service;

import lombok.AllArgsConstructor;
import net.weg.taskmanager.model.Equipe;
import net.weg.taskmanager.model.PermissaoDeCards;
import net.weg.taskmanager.repository.EquipeRepository;
import net.weg.taskmanager.repository.PermissaoDeCardsRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@AllArgsConstructor
public class PermissaoDeCardsService {


    private final PermissaoDeCardsRepository permissaoDeCardsRepository;

    public PermissaoDeCards findById(Integer id){return permissaoDeCardsRepository.findById(id).get();}

    public Collection<PermissaoDeCards> findAll(){return permissaoDeCardsRepository.findAll();}

    public void delete(Integer id){permissaoDeCardsRepository.deleteById(id);}

    public PermissaoDeCards create(PermissaoDeCards permissaoDeCards){return permissaoDeCardsRepository.save(permissaoDeCards);}
    public PermissaoDeCards update(PermissaoDeCards permissaoDeCards){return permissaoDeCardsRepository.save(permissaoDeCards);}

}
