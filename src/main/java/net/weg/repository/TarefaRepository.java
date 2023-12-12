package net.weg.repository;

import net.weg.model.Projeto;
import net.weg.model.Tarefa;
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

    Collection<Tarefa> findTarefasByProjeto(Projeto projeto);
}