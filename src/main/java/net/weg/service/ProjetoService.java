package net.weg.service;

import lombok.AllArgsConstructor;
import net.weg.model.Projeto;
import net.weg.model.Status;
import net.weg.model.Tarefa;
import net.weg.repository.EquipeRepository;
import net.weg.repository.ProjetoRepository;
import net.weg.repository.StatusRepository;
import net.weg.repository.TarefaRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@AllArgsConstructor
public class ProjetoService implements IService<Projeto> {
    private final ProjetoRepository projetoRepository;
    private final TarefaRepository tarefaRepository;
    private final StatusRepository statusRepository;
    private final EquipeRepository equipeRepository;

    public Projeto findById(Integer id) {
        return projetoRepository.findById(id).get();
    }

    public Collection<Projeto> findAll() {
        return projetoRepository.findAll();
    }

    public void delete(Integer id) {
        Projeto projeto = findById(id);
        tarefaRepository.deleteAll(projeto.getTarefas());
        statusRepository.deleteAll(projeto.getListaStatus());

        try {
            if (equipeRepository.findEquipeByProjetosContaining(projeto) != null) {
                //seria bom ter o atributo equipe no proprio projeto para não ter que pegar na service
                equipeRepository.findEquipeByProjetosContaining(projeto).getProjetos().remove(projeto);
            }
//        } catch (NullPointerException e) {
        } catch (Exception e) {
            System.out.println("Deu erro lá manin");
            throw new RuntimeException(e);
        }
        projetoRepository.deleteById(id);
    }

    public Projeto create(Projeto projeto) {
        System.out.println("Acessou a Service");
        projetoRepository.save(projeto);
        projeto.setStatusStandard();
        projeto.propertySetProject();
        return update(projeto);
    }

    public Projeto update(Projeto projeto) {
        return projetoRepository.save(projeto);
    }

    public Collection<Tarefa> getTarefasProjeto(Projeto projeto) {
        return tarefaRepository.findTarefasByProjeto(projeto);
    }
}