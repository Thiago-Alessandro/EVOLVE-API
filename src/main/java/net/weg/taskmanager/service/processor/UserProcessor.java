package net.weg.taskmanager.service.processor;

import net.weg.taskmanager.model.Equipe;
import net.weg.taskmanager.model.UserChat;
import net.weg.taskmanager.model.Usuario;

public class UserProcessor {

    private static Usuario resolvingUser;
    private static String objClassName;

    protected static void resolveUser(Usuario user, String objectClassName){

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
        if(resolvingUser.getEquipes() != null){
            if( objClassName.equals(Equipe.class.getSimpleName())){
                resolvingUser.setEquipes(null);
                return;
            }
            for(Equipe team : resolvingUser.getEquipes()){
                TeamProcessor.resolveTeam(team, objClassName);
            }
        }
    }

}
