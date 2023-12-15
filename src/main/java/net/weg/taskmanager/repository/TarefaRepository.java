package net.weg.taskmanager.repository;

import net.weg.taskmanager.model.Projeto;
import net.weg.taskmanager.model.Status;
import net.weg.taskmanager.model.Tarefa;
import org.aspectj.weaver.patterns.PerObject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface TarefaRepository extends JpaRepository<Tarefa, Integer> {

//    Tarefa findByStatusAtual(Projeto projeto, Status status);
//
//    Tarefa findByDataFinal(Projeto projeto, String dataFinal);
//
//    Tarefa findByPrioridade(Tarefa tarefa);

    Collection<Tarefa> getTarefaByStatusAtual(Status status);

}
