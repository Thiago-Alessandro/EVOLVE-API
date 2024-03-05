package net.weg.taskmanager.service.processor;

import lombok.NoArgsConstructor;
import net.weg.taskmanager.model.*;

import java.util.ArrayList;
@NoArgsConstructor
public class UserProcessor {

    private User resolvingUser;
    private String userClassName = User.class.getSimpleName();
    private ArrayList<String> resolvingCascade;

    public static UserProcessor getInstance(){
        return new UserProcessor();
    }

    public User resolveUser(User user, ArrayList<String> _resolvingCascade){

        resolvingCascade = _resolvingCascade;
        resolvingCascade.add(userClassName);

        resolvingUser = user;

        resolveUserTeams();

        resolveUserManagedTeams();
        resolveUserCreatedTasks();
        resolveUserChats();
//        FileProcessor.addBase64Prefix(user.getImage().getData().);
//        System.out.println("passei do user chats hein");

//        user.getImage().setData(FileProcessor.addBase64Prefix(user.getImage().getData()));

        resolvingCascade.remove(userClassName);

        return resolvingUser;
    }

    public User resolveUser(User user){
        return resolveUser(user, new ArrayList<>());
    }

    private void resolveUserChats(){
        if(resolvingUser.getChats()!=null){
            if(resolvingCascade.contains(UserChat.class.getSimpleName())){
                resolvingUser.setChats(null);
                return;
            }
            resolvingUser.getChats().stream()
                    .forEach(chat -> ChatProcessor.getInstance().resolveChat(chat, resolvingCascade));
        }
    }

    private void resolveUserTeams(){
        if(resolvingUser.getTeams() != null){
            if( resolvingCascade.contains(Team.class.getSimpleName())){
                resolvingUser.setTeams(null);
                return;
            }
            resolvingUser.getTeams().stream()
                    .forEach(team -> TeamProcessor.getInstance().resolveTeam(team, resolvingCascade));
        }
    }

    private void resolveUserManagedTeams(){
        if(resolvingUser.getManagedTeams() != null){
            if( resolvingCascade.contains(Team.class.getSimpleName())){
                resolvingUser.setManagedTeams(null);
                return;
            }
            resolvingUser.getManagedTeams().stream()
                    .forEach(team -> TeamProcessor.getInstance().resolveTeam(team, resolvingCascade));
        }
    }

    private void resolveUserCreatedTasks(){
        if(resolvingUser.getCreatedTasks() != null){
            if(resolvingCascade.contains(Team.class.getSimpleName())){
                resolvingUser.setCreatedTasks(null);
                return;
            }
            resolvingUser.getCreatedTasks().stream()
                    .forEach(task -> TaskProcessor.getInstance().resolveTask(task, resolvingCascade));
        }
    }

}
