package net.weg.taskmanager.service.processor;

import net.weg.taskmanager.model.Chat;
import net.weg.taskmanager.model.Message;
import net.weg.taskmanager.model.UserChat;
import net.weg.taskmanager.model.User;

public class ChatProcessor {

    private static Chat chat;
    private static String objClassName;

    protected static void resolveChat(Chat resolvingChat, String objectClassName){
        chat = resolvingChat;
        objClassName = objectClassName;

        System.out.println("resolvendo userChat");

        resolveChatMessages();
        resolveChatUsers();

    }

    private static void resolveChatUsers(){
        if(chat.getUsers()!=null){
            if(objClassName.equals(User.class.getSimpleName())){
                chat.setUsers(null);
                return;
            }
            for(User user : chat.getUsers()){
                UserProcessor.resolveUser(user, UserChat.class.getSimpleName());
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
