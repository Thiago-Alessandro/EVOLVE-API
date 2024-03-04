package net.weg.taskmanager.service.processor;

import net.weg.taskmanager.model.*;

public class ChatProcessor {

    private static Chat chat;
    private static String objClassName;

    protected static void resolveChat(Chat resolvingChat, String objectClassName){
        chat = resolvingChat;
        objClassName = objectClassName;

        resolveChatMessages();
        resolveChatUsers();
        resolveChatTeam();

    }

    private static void resolveChatUsers(){
        if(chat.getUsers()!=null){
            if(objClassName.equals(User.class.getSimpleName())){
                chat.setUsers(null);
                return;
            }
            for(User user : chat.getUsers()){
                if(chat instanceof UserChat){
                    UserProcessor.resolveUser(user, UserChat.class.getSimpleName());
                } else if(chat instanceof TeamChat){
                    UserProcessor.resolveUser(user, TeamChat.class.getSimpleName());
                } else if(chat instanceof ProjectChat){
                    UserProcessor.resolveUser(user, ProjectChat.class.getSimpleName());
                }
            }
        }
    }

    private static void resolveChatTeam(){
        if(chat instanceof TeamChat){
            if(((TeamChat) chat).getTeam() != null){
                if(objClassName.equals(Team.class.getSimpleName())){
                    ((TeamChat) chat).setTeam(null);
                }
                TeamProcessor.resolveTeam(((TeamChat) chat).getTeam(), TeamChat.class.getSimpleName());
            }
        }
    }

    private static void resolveChatMessages(){
        if(chat.getMessages() != null){
            if(objClassName.equals(Message.class.getSimpleName())){
                chat.setMessages(null);
                return;
            }
            for(Message message : chat.getMessages()){
                MessageProcessor.resolveMessage(message, chat.getClass().getSimpleName());
            }
        }
    }

}
