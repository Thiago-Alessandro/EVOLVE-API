package net.weg.taskmanager.service.processor;

import net.weg.taskmanager.model.*;
import org.springframework.jdbc.core.PreparedStatementSetter;

import java.util.ArrayList;

public class TeamProcessor {

    private static Team resolvingTeam;
    private static String teamClassName = Team.class.getSimpleName();
    private static ArrayList<String> resolvingCascade;

    public static Team resolveTeam(Team team, String objClassName, ArrayList<String> _resolvingCascade){

        resolvingCascade = _resolvingCascade;
        resolvingCascade.add(objClassName);

        resolvingTeam = team;

        resolveTeamParticipants();
        resolveTeamAdministrator();
        resolveTeamChat();
        resolveTeamProjects();
        resolvingCascade = null;
        return team;
    }
    public static Team resolveTeam(Team team) {
        return resolveTeam(team, teamClassName, new ArrayList<>());
    }


    private static void resolveTeamChat(){
        if(resolvingTeam.getChat() != null){
            if(resolvingCascade.contains(TeamChat.class.getSimpleName()) ){
                resolvingTeam.setChat(null);
                return;
            }
            ChatProcessor.resolveChat(resolvingTeam.getChat(), teamClassName, resolvingCascade);
        }
    }

    private static void resolveTeamParticipants(){
        if(resolvingTeam.getParticipants() != null){
            if(resolvingCascade.contains(User.class.getSimpleName())){
                resolvingTeam.setParticipants(null);
                return;
            }
            resolvingTeam.getParticipants().stream()
                    .forEach(user -> UserProcessor.resolveUser(user, teamClassName, resolvingCascade));
        }
    }

    private static void resolveTeamAdministrator(){
        if(resolvingTeam.getAdministrator() != null){
            if(resolvingCascade.contains(User.class.getSimpleName())){
                resolvingTeam.setAdministrator(null);
                return;
            }
            UserProcessor.resolveUser(resolvingTeam.getAdministrator(), teamClassName, resolvingCascade);
        }
    }

    private static void resolveTeamProjects(){
        if(resolvingTeam.getProjects()!=null){
            if(resolvingCascade.contains(Project.class.getSimpleName())){
                resolvingTeam.setProjects(null);
                return;
            }
            resolvingTeam.getProjects().stream()
                    .forEach(project -> ProjectProcessor.resolveProject(project, teamClassName, resolvingCascade));
        }
    }

}
