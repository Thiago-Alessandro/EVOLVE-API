package net.weg.taskmanager.service;

import lombok.AllArgsConstructor;
import net.weg.taskmanager.model.PermissaoDePropriedades;
import net.weg.taskmanager.model.Projeto;
import net.weg.taskmanager.model.Status;
import net.weg.taskmanager.model.Tarefa;
import net.weg.taskmanager.model.property.TarefaProjetoPropriedade;
import net.weg.taskmanager.repository.*;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;

@Service
@AllArgsConstructor
public class ProjetoService {

    private final ProjetoRepository projetoRepository;
    private final TarefaRepository tarefaRepository;
    private final StatusRepository statusRepository;
    private final TarefaProjetoPropriedadeRepository tarefaProjetoPropriedadeRepository ;

    public Projeto findById(Integer id){return projetoRepository.findById(id).get();}

    public Collection<Projeto> findAll(){return projetoRepository.findAll();}

    public void delete(Integer id){
        Projeto projeto = findById(id);
        tarefaRepository.deleteAll(projeto.getTarefas());
        statusRepository.deleteAll(projeto.getListaStatus());
        System.out.println(projeto.getListaStatus());
        projetoRepository.deleteById(id);}

    public Projeto create(Projeto projeto){
        //Seta os status padrões do projeto
        projeto.setListaStatus(setStatusPadrao(projeto));
        //Adiciona o projeto ao BD para que seja criado o seu Id
        projetoRepository.save(projeto);
        //Atualiza o projeto adicionando sua referencia nas suas propriedades
        return update(projeto);
    }
    public Projeto update(Projeto projeto){
        propriedadesSetProjeto(projeto);
        return projetoRepository.save(projeto);}
    private void propriedadesSetProjeto(Projeto projeto){
        //Verifica se há alguma propriedade no projeto
        if(projeto.getPropriedades() != null && projeto.getPropriedades().size()>0){
            //Passa pela lista de propriedades do projeto
            for(TarefaProjetoPropriedade propriedade : projeto.getPropriedades()) {
                //Adiciona a referencia do projeto na propriedade
                propriedade.setProjeto(projeto);
                //Salva a propriedade atualizada com a referencia do projeto
                tarefaProjetoPropriedadeRepository.save(propriedade);
            }
        }
    }

    private Collection<Status> setStatusPadrao(Projeto projeto){
        Collection<Status> statusPadrao = new HashSet<>();
        statusPadrao.add(new Status("pendente","#7CD5F4","#000000"));
        statusPadrao.add(new Status("em progresso","#FCEC62","#000000"));
        statusPadrao.add(new Status("concluido","#86C19F","#000000"));
        statusPadrao.add(new Status("não atribuido","#9CA3AE","#000000"));
        return statusPadrao;
    }

}
