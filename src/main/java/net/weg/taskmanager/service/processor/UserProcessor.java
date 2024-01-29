package net.weg.taskmanager.service.processor;

import net.weg.taskmanager.model.Team;
import net.weg.taskmanager.model.UserChat;
import net.weg.taskmanager.model.User;

public class UserProcessor {

    private static User resolvingUser;
    private static String objClassName;

    protected static void resolveUser(User user, String objectClassName){

        resolvingUser = user;
        objClassName = objectClassName;

        resolveUserChats();
        resolveUserTeams();

    }

    private static void resolveUserChats(){
        if(resolvingUser.getChats()!=null){
            if(objClassName.equals(UserChat.class.getSimpleName())){
                resolvingUser.setChats(null);
                return;
            }
            for(UserChat chat : resolvingUser.getChats()){
                ChatProcessor.resolveChat(chat, objClassName);
            }
        }
    }

    private static void resolveUserTeams(){
        if(resolvingUser.getTeams() != null){
            if( objClassName.equals(Team.class.getSimpleName())){
                resolvingUser.setTeams(null);
                return;
            }
            for(Team team : resolvingUser.getTeams()){
                TeamProcessor.resolveTeam(team, objClassName);
            }
        }
    }

}
