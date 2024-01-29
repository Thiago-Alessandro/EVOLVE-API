package net.weg.taskmanager.service.processor;

import net.weg.taskmanager.model.Team;
import net.weg.taskmanager.model.User;

public class TeamProcessor {

    private static Team resolvingTeam;
    private static String objClassName;

    protected static void resolveTeam(Team team, String objectClassName){

        resolvingTeam = team;
        objClassName = objectClassName;

    }

    private static void resolveTeamMembers(){
        if(resolvingTeam.getParticipants() != null){
            if(objClassName.equals(User.class.getSimpleName())){
                resolvingTeam.setParticipants(null);
                return;
            }
            for(User user : resolvingTeam.getParticipants()){
                UserProcessor.resolveUser(user, resolvingTeam.getClass().getSimpleName());
            }
        }
    }

}
