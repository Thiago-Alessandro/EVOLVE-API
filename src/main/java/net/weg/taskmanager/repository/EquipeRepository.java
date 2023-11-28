package net.weg.taskmanager.repository;

import net.weg.taskmanager.model.Equipe;
import net.weg.taskmanager.model.Projeto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EquipeRepository extends JpaRepository<Equipe, Integer> {

    Equipe findEquipeByProjetosContaining(Projeto projeto);

}
