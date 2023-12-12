package net.weg.repository;

import net.weg.model.Subtarefa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubtarefaRepository extends JpaRepository<Subtarefa, Integer> {
}
