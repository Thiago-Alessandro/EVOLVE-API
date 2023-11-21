package net.weg.taskmanager.service;

import lombok.AllArgsConstructor;
import net.weg.taskmanager.model.PermissaoDePropriedades;
import net.weg.taskmanager.model.Projeto;
import net.weg.taskmanager.model.Status;
import net.weg.taskmanager.repository.PermissaoDePropriedadesRepository;
import net.weg.taskmanager.repository.ProjetoRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;

@Service
@AllArgsConstructor
public class ProjetoService {

    private final ProjetoRepository projetoRepository;

    public Projeto findById(Integer id){return projetoRepository.findById(id).get();}

    public Collection<Projeto> findAll(){return projetoRepository.findAll();}

    public void delete(Integer id){projetoRepository.deleteById(id);}

    public Projeto create(Projeto projeto){
        projeto.setListaStatus(setStatusPadrao(projeto));//setando os status padrões do projeo
//        System.out.println(projeto);
        return projetoRepository.save(projeto);
    }
    public Projeto update(Projeto projeto){return projetoRepository.save(projeto);}

    private Collection<Status> setStatusPadrao(Projeto projeto){
        Collection<Status> statusPadrao = new HashSet<>();
        statusPadrao.add(new Status("pendente","#7CD5F4","#000000"));
        statusPadrao.add(new Status("em progresso","#FCEC62","#000000"));
        statusPadrao.add(new Status("concluido","#86C19F","#000000"));
        statusPadrao.add(new Status("não atribuido","#9CA3AE","#000000"));
        return statusPadrao;
    }

//    private Collection<Status> getAllStatus(){
//        return projetoRepository.;
//    }

}
