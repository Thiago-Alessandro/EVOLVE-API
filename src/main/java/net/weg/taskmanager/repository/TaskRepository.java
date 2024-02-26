package net.weg.taskmanager.repository;

import net.weg.taskmanager.model.Status;
import net.weg.taskmanager.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Locale;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

//    Tarefa findByStatusAtual(Projeto projeto, Status status);
//
//    Tarefa findByDataFinal(Projeto projeto, String dataFinal);
//
//    Tarefa findByPrioridade(Tarefa tarefa);

    Collection<Task> getTaskByCurrentStatus(Status status);

}
