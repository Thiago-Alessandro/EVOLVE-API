package net.weg.taskmanager.service;

import lombok.AllArgsConstructor;
import net.weg.taskmanager.model.ResolveStackOverflow;
import net.weg.taskmanager.model.Tarefa;
import net.weg.taskmanager.model.property.TaskProjectProperty;
import net.weg.taskmanager.repository.TarefaProjetoPropriedadeRepository;
import net.weg.taskmanager.repository.TarefaRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;

@Service
@AllArgsConstructor
public class TarefaService {

    private final TarefaRepository tarefaRepository;
    private final TarefaProjetoPropriedadeRepository tarefaProjetoPropriedadeRepository;

    public Tarefa findById(Integer id) {
        Tarefa tarefa = tarefaRepository.findById(id).get();
        ResolveStackOverflow.getObjectWithoutStackOverflow(tarefa);
        return tarefa;
    }

    public Collection<Tarefa> findAll() {
        Collection<Tarefa> tarefas = tarefaRepository.findAll();

        for(Tarefa tarefa : tarefas){
            ResolveStackOverflow.getObjectWithoutStackOverflow(tarefa);
        }


        return tarefas;
    }

    public void delete(Integer id) {
        tarefaRepository.deleteById(id);
    }

    public Tarefa create(Tarefa tarefa) {
        tarefaRepository.save(tarefa);
        return update(tarefa);
    }

    public Tarefa update(Tarefa tarefa) {

        propriedadeSetTarefa(tarefa);
//        System.out.println(tarefa);
        return tarefaRepository.save(tarefa);
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

}