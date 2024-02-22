package net.weg.taskmanager.service.processor;

import net.weg.taskmanager.model.*;

import java.util.ArrayList;

public class MessageProcessor {

    private static Message resolvingMessage;
    private static ArrayList<String> resolvingCascade;
    private static String messageClassName = Message.class.getSimpleName();
    
    public static Message resolveMessage(Message message, String objClassName, ArrayList<String> _resolvingCascade){

        resolvingMessage = message;
        resolvingCascade = _resolvingCascade;
        resolvingCascade.add(objClassName);

        resolveMessageChat();
        resolveMessageSender();

        resolvingCascade.remove(objClassName);

        return resolvingMessage;
    }

    public static Message resolveMessage(Message resolvingMessage){
        return resolveMessage(resolvingMessage, messageClassName, new ArrayList<>());
    }
    
    private static void resolveMessageChat(){
        
        if(resolvingMessage.getChat()!=null){

            if(resolvingCascade.contains(UserChat.class.getSimpleName()) ||
                    resolvingCascade.contains(TeamChat.class.getSimpleName()) ||
                    resolvingCascade.contains(ProjectChat.class.getSimpleName())
            ){
                resolvingMessage.setChat(null);
                return;
            }
            ChatProcessor.resolveChat(resolvingMessage.getChat(), messageClassName, resolvingCascade);
        }
    }
    private static void resolveMessageSender(){
        if(resolvingMessage.getSender()!=null){
            if(resolvingCascade.contains(User.class.getSimpleName())){
                resolvingMessage.setSender(null);
                return;
            }
            UserProcessor.resolveUser(resolvingMessage.getSender(), messageClassName, resolvingCascade);
        }
    }
    
}
