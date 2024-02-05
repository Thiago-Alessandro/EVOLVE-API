package net.weg.taskmanager.service.processor;

import net.weg.taskmanager.model.Chat;
import net.weg.taskmanager.model.Team;
import net.weg.taskmanager.model.TeamChat;
import net.weg.taskmanager.model.User;

public class TeamProcessor {

    private static Team resolvingTeam;
    private static String objClassName;

    protected static void resolveTeam(Team team, String objectClassName){

        resolvingTeam = team;
        objClassName = objectClassName;

        resolveTeamParticipants();
        resolveTeamAdministrator();
        resolveTeamChat();

    }

    private static void resolveTeamChat(){
        if(resolvingTeam.getChat() != null){
            if(objClassName.equals(TeamChat.class.getSimpleName()) ){
                resolvingTeam.setChat(null);
                return;
            }
            ChatProcessor.resolveChat(resolvingTeam.getChat(), TeamChat.class.getSimpleName());
        }
    }

    private static void resolveTeamParticipants(){
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

    private static void resolveTeamAdministrator(){
        if(resolvingTeam.getAdministrator() != null){
            if(objClassName.equals(User.class.getSimpleName())){
                resolvingTeam.setAdministrator(null);
                return;
            }
            UserProcessor.resolveUser(resolvingTeam.getAdministrator(), resolvingTeam.getClass().getSimpleName());
        }
    }

}
