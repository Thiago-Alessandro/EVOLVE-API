package net.weg.taskmanager.service.processor;

import lombok.NoArgsConstructor;
import net.weg.taskmanager.model.*;

import java.util.ArrayList;
@NoArgsConstructor
public class TeamProcessor {

    private Team resolvingTeam;
    private String teamClassName = Team.class.getSimpleName();
    private ArrayList<String> resolvingCascade;

    public static TeamProcessor getInstance(){
        return new TeamProcessor();
    }

    public Team resolveTeam(Team team, ArrayList<String> _resolvingCascade){

        resolvingCascade = _resolvingCascade;
        resolvingCascade.add(teamClassName);

        resolvingTeam = team;

        resolveTeamChat();
        resolveTeamParticipants();
//        resolveTeamAdministrator();
        resolveTeamProjects();
        resolvingCascade.remove(teamClassName);
        return team;
    }
    public Team resolveTeam(Team team) {
        return resolveTeam(team, new ArrayList<>());
    }


    private void resolveTeamChat(){
        if(resolvingTeam.getChat() != null){
            if(resolvingCascade.contains(TeamChat.class.getSimpleName()) ){
                resolvingTeam.setChat(null);
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
                    .forEach(user -> UserProcessor.getInstance().resolveUser(user.getUser(), resolvingCascade));
        }
    }

//    private void resolveTeamAdministrator(){
//        if(resolvingTeam.getAdministrator() != null){
//            if(resolvingCascade.contains(User.class.getSimpleName())){
//                resolvingTeam.setAdministrator(null);
//                return;
//            }
//            UserProcessor.getInstance().resolveUser(resolvingTeam.getAdministrator(), resolvingCascade);
//        }
//    }

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
