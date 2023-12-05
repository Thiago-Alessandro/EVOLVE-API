package net.weg.service;

import lombok.AllArgsConstructor;
import net.weg.repository.TarefaRepository;
import net.weg.model.Tarefa;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@AllArgsConstructor
public class TarefaService {

    private final TarefaRepository tarefaRepository;

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
        tarefaRepository.save(tarefa);
        tarefa.propertySetTask();
        return update(tarefa);
    }

    public Tarefa update(Tarefa tarefa) {
        return tarefaRepository.save(tarefa);
    }
}