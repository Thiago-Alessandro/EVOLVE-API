package net.weg.taskmanager.service;

import lombok.AllArgsConstructor;
import net.weg.taskmanager.model.Projeto;
import net.weg.taskmanager.model.Status;
import net.weg.taskmanager.model.Tarefa;
import net.weg.taskmanager.model.property.TaskProjectProperty;
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
    private final EquipeRepository equipeRepository;
    private final TarefaProjetoPropriedadeRepository tarefaProjetoPropriedadeRepository ;

    public Projeto findById(Integer id){
        Projeto projeto =  projetoRepository.findById(id).get();
        for(Tarefa tarefa : projeto.getTarefas()){
            tarefa.setProjeto(null);
        }


        return projeto ;}

    public Collection<Projeto> findAll() {
        Collection<Projeto> projetos =  projetoRepository.findAll();
        for (Projeto projeto : projetos) {
            for (Tarefa tarefa : projeto.getTarefas()) {
                tarefa.setProjeto(null);
            }
        }
        return projetos;
    }

    public void delete(Integer id){
        Projeto projeto = findById(id);
        tarefaRepository.deleteAll(projeto.getTarefas());
        statusRepository.deleteAll(projeto.getListaStatus());

        try {
            if(equipeRepository.findEquipeByProjetosContaining(projeto)!=null){
                //seria bom ter o atributo equipe no proprio projeto para não ter que pegar na service
                equipeRepository.findEquipeByProjetosContaining(projeto).getProjetos().remove(projeto);
            }
        } catch (Exception e) {
            System.out.println("Deu erro lá manin");
            throw new RuntimeException(e);
        }

        projetoRepository.deleteById(id);}

    public Projeto create(Projeto projeto){

        //Adiciona o projeto ao BD para que seja criado o seu Id
        projetoRepository.save(projeto);

        //Seta os status padrões do projeto
        projeto.setStatusPadrao();
        propriedadesSetProjeto(projeto);

        //Atualiza o projeto adicionando sua referencia nas suas propriedades
        return update(projeto);
    }
    public Projeto update(Projeto projeto){

//        for(Status status : projeto.getListaStatus()){
//            if(status.getId()==0){
//                status.setId(null);
//            }
//        }

        return projetoRepository.save(projeto);}
    private void propriedadesSetProjeto(Projeto projeto){
        //Verifica se há alguma propriedade no projeto
        if(projeto.getPropriedades() != null && projeto.getPropriedades().size()>0){
            //Passa pela lista de propriedades do projeto
            for(TaskProjectProperty propriedade : projeto.getPropriedades()) {
                //Adiciona a referencia do projeto na propriedade
                propriedade.setProject(projeto);
                //Salva a propriedade atualizada com a referencia do projeto
                tarefaProjetoPropriedadeRepository.save(propriedade);
            }
        }
    }



}
