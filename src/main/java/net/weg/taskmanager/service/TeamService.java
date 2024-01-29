package net.weg.taskmanager.service;

import lombok.AllArgsConstructor;
import net.weg.taskmanager.model.Team;
import net.weg.taskmanager.service.processor.ResolveStackOverflow;
import net.weg.taskmanager.model.TeamChat;
import net.weg.taskmanager.model.User;
import net.weg.taskmanager.repository.TeamRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@AllArgsConstructor
public class TeamService {

    private final TeamRepository teamRepository;

    public Team findById(Integer id){
        Team team = teamRepository.findById(id).get();
        return ResolveStackOverflow.getObjectWithoutStackOverflow(team);}

    public Collection<Team> findAll(){

        Collection<Team> teams = teamRepository.findAll();
        for(Team team : teams){
            ResolveStackOverflow.getObjectWithoutStackOverflow(team);
        }
        return teams;
    }

    public void delete(Integer id){
        teamRepository.deleteById(id);}

    public Team create(Team team){
        updateTeamChat(team);
        return teamRepository.save(team);}

    public Team update(Team team){
        updateTeamChat(team);
        for(User participante : team.getParticipants()){
            if(!team.getChat().getUsers().contains(participante)){
                team.getChat().getUsers().add(participante);
            }
        }
        team.getChat().setUsers(team.getParticipants());
        return teamRepository.save(team);}

    private void updateTeamChat(Team team){
        if(team.getChat()==null){
            team.setChat(new TeamChat());
            team.getChat().setTeam(team);
        }
        team.getChat().setUsers(team.getParticipants());
    }

}
