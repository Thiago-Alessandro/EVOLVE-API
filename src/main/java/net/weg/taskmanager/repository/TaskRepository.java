package net.weg.taskmanager.repository;

import net.weg.taskmanager.model.Status;
import net.weg.taskmanager.model.Task;
import net.weg.taskmanager.model.User;
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

    boolean existsByIdAndAssociatesContaining(Long taskId, User user);
    boolean existsByCurrentStatus_IdAndAssociatesContaining(Long statusId, User user);
    Collection<Task> findAllByProject_Id(Long projectId);
}
