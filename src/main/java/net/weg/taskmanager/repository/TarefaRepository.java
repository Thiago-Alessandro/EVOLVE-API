package net.weg.taskmanager.repository;

import net.weg.taskmanager.model.Projeto;
import net.weg.taskmanager.model.Tarefa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TarefaRepository  extends JpaRepository<Tarefa, Integer> {
}
