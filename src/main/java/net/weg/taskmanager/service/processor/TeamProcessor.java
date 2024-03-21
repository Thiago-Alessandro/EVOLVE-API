package net.weg.taskmanager.service.processor;

import lombok.NoArgsConstructor;
import net.weg.taskmanager.model.dto.get.GetTeamDTO;
import net.weg.taskmanager.model.entity.Project;
import net.weg.taskmanager.model.entity.Team;
import net.weg.taskmanager.model.entity.TeamChat;
import net.weg.taskmanager.model.entity.User;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

@NoArgsConstructor
public class TeamProcessor {

    private Team resolvingTeam;
    private String teamClassName = Team.class.getSimpleName();
    private ArrayList<String> resolvingCascade;
    private GetTeamDTO getTeamDTO = new GetTeamDTO();

    public static TeamProcessor getInstance(){
        return new TeamProcessor();
    }

    public Team resolveTeam(Team team, ArrayList<String> _resolvingCascade){
        resolvingCascade = _resolvingCascade;
        resolvingCascade.add(teamClassName);

        resolvingTeam = team;

        resolveTeamChat();
        resolveTeamParticipants();
        resolveTeamAdministrator();
        resolveTeamProjects();

        resolvingCascade.remove(teamClassName);

        return resolvingTeam;
    }

    public Team resolveTeam(Team team) {
        return resolveTeam(team, new ArrayList<>());
    }

    public Collection<Team> resolveTeams(Collection<Team> teams) {
        return resolveTeams(teams, new ArrayList<>());
    }

    public Collection<Team> resolveTeams(Collection<Team> teams, ArrayList<String> _resolvingCascade){
        teams.forEach(team -> resolveTeam(team, _resolvingCascade));
        return teams;
    }

//

    private void resolveTeamChat(){
        if(resolvingTeam.getChat() != null){
            if(resolvingCascade.contains(TeamChat.class.getSimpleName()) ){
                getTeamDTO.setChat(null);
                return;
            }
            ChatProcessor.getInstance().resolveChat(resolvingTeam.getChat(), resolvingCascade);
        }
    }

    private void resolveTeamParticipants(){
        if(resolvingTeam.getParticipants() != null){
            if(resolvingCascade.contains(User.class.getSimpleName())){
                resolvingTeam.setParticipants(null);
                return;
            }
            resolvingTeam.getParticipants()
                    .forEach(user -> UserProcessor.getInstance().resolveUser(user, resolvingCascade));
        }
    }

    private void resolveTeamAdministrator(){
        if(resolvingTeam.getAdministrator() != null){
            if(resolvingCascade.contains(User.class.getSimpleName())){
                resolvingTeam.setAdministrator(null);
                return;
            }
            UserProcessor.getInstance().resolveUser(resolvingTeam.getAdministrator(), resolvingCascade);
        }
    }

    private void resolveTeamProjects(){
        if(resolvingTeam.getProjects()!=null){
            if(resolvingCascade.contains(Project.class.getSimpleName())){
                resolvingTeam.setProjects(null);
                return;
            }
            resolvingTeam.getProjects()
                    .forEach(project -> ProjectProcessor.getInstance().resolveProject(project, resolvingCascade));
        }
    }

}
