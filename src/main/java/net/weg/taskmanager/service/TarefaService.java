package net.weg.taskmanager.service;

import lombok.AllArgsConstructor;
import net.weg.taskmanager.service.processor.ResolveStackOverflow;
import net.weg.taskmanager.model.Tarefa;
import net.weg.taskmanager.model.property.TaskProjectProperty;
import net.weg.taskmanager.repository.StatusRepository;
import net.weg.taskmanager.repository.TarefaProjetoPropriedadeRepository;
import net.weg.taskmanager.repository.TarefaRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@AllArgsConstructor
public class TarefaService {

    private final TarefaRepository tarefaRepository;
    private final TarefaProjetoPropriedadeRepository tarefaProjetoPropriedadeRepository;

    public Tarefa findById(Integer id) {
        Tarefa task = tarefaRepository.findById(id).get();
        ResolveStackOverflow.getObjectWithoutStackOverflow(task);
        return task;
    }

    public Collection<Tarefa> findAll() {
        Collection<Tarefa> tasks = tarefaRepository.findAll();

        for(Tarefa task : tasks){
            ResolveStackOverflow.getObjectWithoutStackOverflow(task);
        }
        return tasks;
    }

    public void delete(Integer id) {
        tarefaRepository.deleteById(id);
    }

    public Tarefa create(Tarefa tarefa) {
        tarefaRepository.save(tarefa);
        setStatusListIndex(tarefa);
        return update(tarefa);
    }

    public Tarefa update(Tarefa task) {

        setStatusListIndex(task);
        propriedadeSetTarefa(task);

        Tarefa updatedTask = tarefaRepository.save(task);
        return ResolveStackOverflow.getObjectWithoutStackOverflow(updatedTask);
    }

    private void setStatusListIndex(Tarefa tarefa){
        Integer defaultIndex = -1;
        Integer firstIndex = 0;
        if(tarefa.getStatusAtual()!=null && tarefa.getStatusListIndex() != null){
            if(tarefa.getStatusListIndex() == defaultIndex){
                Collection<Tarefa> listaTarefas = getTarefasByStatus(tarefa.getStatusAtual().getId());
                if(listaTarefas != null){
                    tarefa.setStatusListIndex(listaTarefas.size());
                }else {
                    tarefa.setStatusListIndex(firstIndex);
                }
            }
        } else {
            tarefa.setStatusListIndex(defaultIndex);
        }
    }

    private void propriedadeSetTarefa(Tarefa tarefa){
        //Verifica se há alguma propriedade na tarefa
        if(tarefa.getPropriedades() != null && tarefa.getPropriedades().size()>0){
            //Passa pela lista de propriedades da tarefa
            for(TaskProjectProperty propriedade : tarefa.getPropriedades()) {
                //Adiciona a referencia da tarefa na propriedade
                propriedade.setTask(tarefa);
                //Salva a propriedade atualizada com a referencia da tarefa
                tarefaProjetoPropriedadeRepository.save(propriedade);
            }
        }
    }
    private final StatusRepository statusRepository;
    public Collection<Tarefa> getTarefasByStatus(Integer id){

        return tarefaRepository.getTarefaByStatusAtual(statusRepository.findById(id).get());
    }

}