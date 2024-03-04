package net.weg.taskmanager.service.processor;

import net.weg.taskmanager.model.Team;
import net.weg.taskmanager.model.UserChat;
import net.weg.taskmanager.model.User;

public class UserProcessor {

    private static User resolvingUser;
    private static String objClassName;

    public static void resolveUser(User user, String objectClassName){

        resolvingUser = user;
        objClassName = objectClassName;

        resolveUserChats();
        resolveUserTeams();
        resolveUserManagedTeams();

        user.setProfilePicture(FileProcessor.addBase64Prefix(user.getProfilePicture()));

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

    private static void resolveUserManagedTeams(){
        if(resolvingUser.getManagedTeams() != null){
            if( objClassName.equals(Team.class.getSimpleName())){
                resolvingUser.setManagedTeams(null);
                return;
            }
            for(Team team : resolvingUser.getManagedTeams()){
                TeamProcessor.resolveTeam(team, objClassName);
            }
        }
    }

}
