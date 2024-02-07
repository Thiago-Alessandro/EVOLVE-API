package net.weg.taskmanager.service.processor;

import net.weg.taskmanager.model.Task;
import net.weg.taskmanager.model.Team;
import net.weg.taskmanager.model.UserChat;
import net.weg.taskmanager.model.User;

public class UserProcessor {

    private static User resolvingUser;
    private static String objClassName;
    private static String userClassName;

    public static void resolveUser(User user, String objectClassName){

        resolvingUser = user;
        objClassName = objectClassName;
        userClassName = User.class.getSimpleName();

        resolveUserChats();
        resolveUserTeams();
        resolveUserManagedTeams();
        resolveUserCreatedTasks();

        user.setProfilePicture(FileProcessor.addBase64Prefix(user.getProfilePicture()));

    }

    private static void resolveUserChats(){
        if(resolvingUser.getChats()!=null){
            if(objClassName.equals(UserChat.class.getSimpleName())){
                resolvingUser.setChats(null);
                return;
            }
            resolvingUser.getChats().stream()
                    .forEach(chat -> ChatProcessor.resolveChat(chat, userClassName));
        }
    }

    private static void resolveUserTeams(){
        if(resolvingUser.getTeams() != null){
            if( objClassName.equals(Team.class.getSimpleName())){
                resolvingUser.setTeams(null);
                return;
            }
            resolvingUser.getTeams().stream()
                    .forEach(team -> TeamProcessor.resolveTeam(team, userClassName));
        }
    }

    private static void resolveUserManagedTeams(){
        if(resolvingUser.getManagedTeams() != null){
            if( objClassName.equals(Team.class.getSimpleName())){
                resolvingUser.setManagedTeams(null);
                return;
            }
            resolvingUser.getManagedTeams().stream()
                    .forEach(team -> TeamProcessor.resolveTeam(team, userClassName));
        }
    }

    private static void resolveUserCreatedTasks(){
        if(resolvingUser.getCreatedTasks() != null){
            if(objClassName.equals(Team.class.getSimpleName())){
                resolvingUser.setCreatedTasks(null);
                return;
            }
            resolvingUser.getCreatedTasks().stream()
                    .forEach(task -> TaskProcessor.resolveTask(task, userClassName));
        }
    }

}
