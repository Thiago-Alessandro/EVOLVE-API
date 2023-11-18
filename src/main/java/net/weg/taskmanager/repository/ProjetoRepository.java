package net.weg.taskmanager.repository;

import net.weg.taskmanager.model.Projeto;
import net.weg.taskmanager.model.Status;
import net.weg.taskmanager.model.Tarefa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjetoRepository extends JpaRepository<Projeto, Integer> {
}
