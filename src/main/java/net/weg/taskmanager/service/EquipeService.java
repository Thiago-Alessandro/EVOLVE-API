package net.weg.taskmanager.service;

import lombok.AllArgsConstructor;
import net.weg.taskmanager.model.Equipe;
import net.weg.taskmanager.model.ResolveStackOverflow;
import net.weg.taskmanager.model.TeamChat;
import net.weg.taskmanager.model.Usuario;
import net.weg.taskmanager.repository.EquipeRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@AllArgsConstructor
public class EquipeService {

    private final EquipeRepository equipeRepository;

    public Equipe findById(Integer id){return equipeRepository.findById(id).get();}

    public Collection<Equipe> findAll(){

        Collection<Equipe> equipes = equipeRepository.findAll();
        for(Equipe equipe : equipes){
            ResolveStackOverflow.getObjectWithoutStackOverflow(equipe);
        }
        return equipes;
    }

    public void delete(Integer id){equipeRepository.deleteById(id);}

    public Equipe create(Equipe equipe){
        TeamChat newChat = new TeamChat();
        newChat.setUsers(equipe.getParticipantes());
        equipe.setChat(newChat);
        return equipeRepository.save(equipe);}
    public Equipe update(Equipe equipe){
        for(Usuario participante : equipe.getParticipantes()){
            if(!equipe.getChat().getUsers().contains(participante)){
                equipe.getChat().getUsers().add(participante);
            }
        }
        return equipeRepository.save(equipe);}

}
