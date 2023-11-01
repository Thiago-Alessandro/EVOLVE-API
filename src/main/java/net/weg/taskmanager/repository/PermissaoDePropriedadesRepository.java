package net.weg.taskmanager.repository;

import net.weg.taskmanager.model.PermissaoDePropriedades;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissaoDePropriedadesRepository extends JpaRepository<PermissaoDePropriedades, Integer> {
}
