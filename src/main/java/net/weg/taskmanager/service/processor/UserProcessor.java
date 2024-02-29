package net.weg.taskmanager.service.processor;

import net.weg.taskmanager.model.*;

import java.util.ArrayList;

public class UserProcessor {

    private static User resolvingUser;
    private static String userClassName = User.class.getSimpleName();
    private static ArrayList<String> resolvingCascade;


    public static User resolveUser(User user, String objClassName, ArrayList<String> _resolvingCascade){

        UserProcessor.resolvingCascade = _resolvingCascade;
        resolvingCascade.add(objClassName);

        resolvingUser = user;

        resolveUserTeams();

        resolveUserManagedTeams();
        resolveUserCreatedTasks();
        resolveUserChats();
        System.out.println("passei do user chats hein");

//        user.getImage().setData(FileProcessor.addBase64Prefix(user.getImage().getData()));

        resolvingCascade.remove(objClassName);

        return resolvingUser;
    }

    public static User resolveUser(User user){
        return resolveUser(user, userClassName, new ArrayList<>());
    }

    private static void resolveUserChats(){
        if(resolvingUser.getChats()!=null){
            if(resolvingCascade.contains(UserChat.class.getSimpleName())){
                resolvingUser.setChats(null);
                System.out.println("ESTOU setando null user chats" + resolvingCascade);
                System.out.println(resolvingUser.getChats());
                System.out.println(resolvingUser.getId());
                return;
            }
            System.out.println("n setei userchat null");
            resolvingUser.getChats().stream()
                    .forEach(chat -> ChatProcessor.resolveChat(chat, userClassName, resolvingCascade));
        }
    }

    private static void resolveUserTeams(){
        if(resolvingUser.getTeams() != null){
            if( resolvingCascade.contains(Team.class.getSimpleName())){
                resolvingUser.setTeams(null);
                return;
            }
            resolvingUser.getTeams().stream()
                    .forEach(team -> TeamProcessor.resolveTeam(team, userClassName, resolvingCascade));
        }
    }

    private static void resolveUserManagedTeams(){
        if(resolvingUser.getManagedTeams() != null){
            if( resolvingCascade.contains(Team.class.getSimpleName())){
                resolvingUser.setManagedTeams(null);
                return;
            }
            resolvingUser.getManagedTeams().stream()
                    .forEach(team -> TeamProcessor.resolveTeam(team, userClassName, resolvingCascade));
        }
    }

    private static void resolveUserCreatedTasks(){
        if(resolvingUser.getCreatedTasks() != null){
            if(resolvingCascade.contains(Team.class.getSimpleName())){
                resolvingUser.setCreatedTasks(null);
                return;
            }
            resolvingUser.getCreatedTasks().stream()
                    .forEach(task -> TaskProcessor.resolveTask(task, userClassName, resolvingCascade));
        }
    }

}
