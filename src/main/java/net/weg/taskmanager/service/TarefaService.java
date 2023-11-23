package net.weg.taskmanager.service;

import lombok.AllArgsConstructor;
import net.weg.taskmanager.model.Tarefa;
import net.weg.taskmanager.model.property.TarefaProjetoPropriedade;
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
        return tarefaRepository.findById(id).get();
    }

    public Collection<Tarefa> findAll() {
        return tarefaRepository.findAll();
    }

    public void delete(Integer id) {
        tarefaRepository.deleteById(id);
    }

    public Tarefa create(Tarefa tarefa) {
        System.out.println("Oia, vou criar a tarefa hein");
        System.out.println(tarefa);
        tarefaRepository.save(tarefa);
        System.out.println("passei e criei a tarefa:");
        System.out.println(tarefa);
        return update(tarefa);
    }

    public Tarefa update(Tarefa tarefa) {
        propriedadeSetTarefa(tarefa);
        return tarefaRepository.save(tarefa);
    }

    private void propriedadeSetTarefa(Tarefa tarefa){
        //Verifica se há alguma propriedade na tarefa
        if(tarefa.getPropriedades() != null && tarefa.getPropriedades().size()>0){
            //Passa pela lista de propriedades da tarefa
            for(TarefaProjetoPropriedade propriedade : tarefa.getPropriedades()) {
                //Adiciona a referencia da tarefa na propriedade
                propriedade.setTarefa(tarefa);
                //Salva a propriedade atualizada com a referencia da tarefa
                tarefaProjetoPropriedadeRepository.save(propriedade);
            }
        }
    }

}