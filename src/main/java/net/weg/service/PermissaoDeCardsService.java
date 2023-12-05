package net.weg.service;

import lombok.AllArgsConstructor;
import net.weg.model.PermissaoDeCards;
import net.weg.repository.PermissaoDeCardsRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@AllArgsConstructor
public class PermissaoDeCardsService implements IService<PermissaoDeCards> {
    private final PermissaoDeCardsRepository permissaoDeCardsRepository;

    public PermissaoDeCards findById(Integer id) {
        return permissaoDeCardsRepository.findById(id).get();
    }

    public Collection<PermissaoDeCards> findAll() {
        return permissaoDeCardsRepository.findAll();
    }

    public void delete(Integer id) {
        permissaoDeCardsRepository.deleteById(id);
    }

    public PermissaoDeCards create(PermissaoDeCards permissaoDeCards) {
        return permissaoDeCardsRepository.save(permissaoDeCards);
    }

    public PermissaoDeCards update(PermissaoDeCards permissaoDeCards) {
        return permissaoDeCardsRepository.save(permissaoDeCards);
    }
}