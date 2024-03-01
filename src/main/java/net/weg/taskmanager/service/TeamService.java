package net.weg.taskmanager.service;

import lombok.AllArgsConstructor;
import net.weg.taskmanager.model.Team;
import net.weg.taskmanager.model.TeamChat;
import net.weg.taskmanager.repository.TeamRepository;
import net.weg.taskmanager.service.processor.TeamProcessor;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@AllArgsConstructor
public class TeamService {

    private final TeamRepository teamRepository;

    public Team findById(Long id){
        Team team = teamRepository.findById(id).get();
        return TeamProcessor.getInstance().resolveTeam(team);}

    public Collection<Team> findAll(){

        Collection<Team> teams = teamRepository.findAll();
        for(Team team : teams){
            TeamProcessor.getInstance().resolveTeam(team);
        }
        return teams;
    }

    public void delete(Long id){
        teamRepository.deleteById(id);}

    public Team create(Team team){
        updateTeamChat(team);
        Team createdTeam = teamRepository.save(team);
        return TeamProcessor.getInstance().resolveTeam(createdTeam);}

    public Team update(Team team){
        updateTeamChat(team);
        Team updatedTeam = teamRepository.save(team);
        return TeamProcessor.getInstance().resolveTeam(updatedTeam);
    }

    private void updateTeamChat(Team team){
        team.getChat().setUsers(team.getParticipants());
    }

}
