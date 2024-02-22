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

    public Team findById(Integer id){
        Team team = teamRepository.findById(id).get();
        return TeamProcessor.resolveTeam(team);}

    public Collection<Team> findAll(){

        Collection<Team> teams = teamRepository.findAll();
//        System.out.println(teams);
        for(Team team : teams){
            TeamProcessor.resolveTeam(team);
//            System.out.println(team);
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
        Team updatedTeam = teamRepository.save(team);
        return TeamProcessor.resolveTeam(updatedTeam);
    }

    private void updateTeamChat(Team team){
        if(team.getChat()==null){
            team.setChat(new TeamChat());
            team.getChat().setTeam(team);
        }
        team.getChat().setUsers(team.getParticipants());
    }

}
