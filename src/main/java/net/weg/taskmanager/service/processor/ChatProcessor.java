package net.weg.taskmanager.service.processor;

import net.weg.taskmanager.controller.TaskController;
import net.weg.taskmanager.model.*;

import java.util.ArrayList;

public class ChatProcessor {

    private static Chat chat;
    private static ArrayList<String> resolvingCascade;

    public static Chat resolveChat(Chat resolvingChat, String objClassName, ArrayList<String> _resolvingCascade){
        chat = resolvingChat;
        resolvingCascade = _resolvingCascade;
        resolvingCascade.add(objClassName);

        resolveChatMessages();
        resolveChatUsers();
        resolveChatTeam();

        return chat;
    }

    public static Chat resolveChat(Chat resolvingChat){
        if(resolvingChat instanceof UserChat){
            return resolveChat(resolvingChat, UserChat.class.getSimpleName(), new ArrayList<>());
        }else if (resolvingChat instanceof TeamChat){
            return resolveChat(resolvingChat, TeamChat.class.getSimpleName(), new ArrayList<>());
        } else if (resolvingChat instanceof ProjectChat) {
            return resolveChat(resolvingChat, ProjectChat.class.getSimpleName(), new ArrayList<>());
        }
        return resolveChat(resolvingChat, Chat.class.getSimpleName(), new ArrayList<>());
    }

    private static void resolveChatUsers(){
        if(chat.getUsers()!=null){
            if(resolvingCascade.contains(User.class.getSimpleName())){
                chat.setUsers(null);
                return;
            }

                for(User user : chat.getUsers()) {
                    if (chat.getUsers() != null) {

                        if (chat instanceof UserChat) {
                            UserProcessor.resolveUser(user, UserChat.class.getSimpleName(), resolvingCascade);
                        } else if (chat instanceof TeamChat) {
                            UserProcessor.resolveUser(user, TeamChat.class.getSimpleName(), resolvingCascade);
                        } else if (chat instanceof ProjectChat) {
                            UserProcessor.resolveUser(user, ProjectChat.class.getSimpleName(), resolvingCascade);
                        }
                    }
                }


        }
    }

    private static void resolveChatTeam(){
        if(chat instanceof TeamChat){
            if(((TeamChat) chat).getTeam() != null){
                if(resolvingCascade.contains(Team.class.getSimpleName())){
                    ((TeamChat) chat).setTeam(null);
                    return;
                }
                TeamProcessor.resolveTeam(((TeamChat) chat).getTeam(), TeamChat.class.getSimpleName(), resolvingCascade);
            }
        }
    }

    private static void resolveChatMessages(){
        if(chat.getMessages() != null){
            if(resolvingCascade.contains(Message.class.getSimpleName())){
                chat.setMessages(null);
                return;
            }
            for(Message message : chat.getMessages()){
                MessageProcessor.resolveMessage(message, chat.getClass().getSimpleName(), resolvingCascade);
            }
        }
    }

}
