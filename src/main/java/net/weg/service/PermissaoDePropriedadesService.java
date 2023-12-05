package net.weg.service;

import lombok.AllArgsConstructor;
import net.weg.model.PermissaoDePropriedades;
import net.weg.repository.PermissaoDePropriedadesRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@AllArgsConstructor
public class PermissaoDePropriedadesService {

    private final PermissaoDePropriedadesRepository permissaoDePropriedadesRepository;

    public PermissaoDePropriedades findById(Integer id) {
        return permissaoDePropriedadesRepository.findById(id).get();
    }

    public Collection<PermissaoDePropriedades> findAll() {
        return permissaoDePropriedadesRepository.findAll();
    }

    public void delete(Integer id) {
        permissaoDePropriedadesRepository.deleteById(id);
    }

    public PermissaoDePropriedades create(PermissaoDePropriedades permissaoDePropriedades) {
        return permissaoDePropriedadesRepository.save(permissaoDePropriedades);
    }

    public PermissaoDePropriedades update(PermissaoDePropriedades permissaoDePropriedades) {
        return permissaoDePropriedadesRepository.save(permissaoDePropriedades);
    }
}