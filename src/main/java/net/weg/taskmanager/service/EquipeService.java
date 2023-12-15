package net.weg.taskmanager.service;

import lombok.AllArgsConstructor;
import net.weg.taskmanager.model.Equipe;
import net.weg.taskmanager.service.processor.ResolveStackOverflow;
import net.weg.taskmanager.model.TeamChat;
import net.weg.taskmanager.model.Usuario;
import net.weg.taskmanager.repository.EquipeRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@AllArgsConstructor
public class EquipeService {

    private final EquipeRepository equipeRepository;

    public Equipe findById(Integer id){
        Equipe equipe = equipeRepository.findById(id).get();
        return ResolveStackOverflow.getObjectWithoutStackOverflow(equipe);}

    public Collection<Equipe> findAll(){

        Collection<Equipe> equipes = equipeRepository.findAll();
        for(Equipe equipe : equipes){
            ResolveStackOverflow.getObjectWithoutStackOverflow(equipe);
        }
        return equipes;
    }

    public void delete(Integer id){equipeRepository.deleteById(id);}

    public Equipe create(Equipe team){
        updateTeamChat(team);
        return equipeRepository.save(team);}

    public Equipe update(Equipe team){
        updateTeamChat(team);
        for(Usuario participante : team.getParticipantes()){
            if(!team.getChat().getUsers().contains(participante)){
                team.getChat().getUsers().add(participante);
            }
        }
        team.getChat().setUsers(team.getParticipantes());
        return equipeRepository.save(team);}

    private void updateTeamChat(Equipe team){
        if(team.getChat()==null){
            team.setChat(new TeamChat());
            team.getChat().setTeam(team);
        }
        team.getChat().setUsers(team.getParticipantes());
    }

}
