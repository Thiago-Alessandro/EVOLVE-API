package net.weg.repository;

import net.weg.model.PermissaoDePropriedades;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissaoDePropriedadesRepository extends JpaRepository<PermissaoDePropriedades, Integer> {
}
