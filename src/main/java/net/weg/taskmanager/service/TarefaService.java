package net.weg.taskmanager.service;

import lombok.AllArgsConstructor;
import net.weg.taskmanager.model.Propriedade;
import net.weg.taskmanager.model.Tarefa;
import net.weg.taskmanager.repository.PropriedadeRepository;
import net.weg.taskmanager.repository.TarefaRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@AllArgsConstructor
public class TarefaService {

    private final TarefaRepository tarefaRepository;

    public Tarefa findById(Integer id){return tarefaRepository.findById(id).get();}

    public Collection<Tarefa> findAll(){return tarefaRepository.findAll();}

    public void delete(Integer id){tarefaRepository.deleteById(id);}

    public Tarefa create(Tarefa tarefa){return tarefaRepository.save(tarefa);}
    public Tarefa update(Tarefa tarefa){return tarefaRepository.save(tarefa);}

}
