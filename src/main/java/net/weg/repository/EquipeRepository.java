package net.weg.repository;

import net.weg.model.Equipe;
import net.weg.model.Projeto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EquipeRepository extends JpaRepository<Equipe, Integer> {

    Equipe findEquipeByProjetosContaining(Projeto projeto);
}