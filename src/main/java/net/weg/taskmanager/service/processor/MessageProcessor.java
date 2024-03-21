package net.weg.taskmanager.service.processor;

import net.weg.taskmanager.model.entity.*;

import java.util.ArrayList;

public class MessageProcessor {

    private Message resolvingMessage;
    private ArrayList<String> resolvingCascade;
    private String messageClassName = Message.class.getSimpleName();

    public static MessageProcessor getInstance(){
        return new MessageProcessor();
    }

    public void resolveMessage(Message message, ArrayList<String> _resolvingCascade){

        resolvingMessage = message;
        resolvingCascade = _resolvingCascade;
        resolvingCascade.add(messageClassName);

        resolveMessageChat();
        resolveMessageSender();

        resolvingCascade.remove(messageClassName);

    }

    public  Message resolveMessage(Message resolvingMessage){
        resolveMessage(resolvingMessage, new ArrayList<>());
        return resolvingMessage;
    }
    
    private  void resolveMessageChat(){
        
        if(resolvingMessage.getChat()!=null){

            if(resolvingCascade.contains(UserChat.class.getSimpleName()) ||
                    resolvingCascade.contains(TeamChat.class.getSimpleName()) ||
                    resolvingCascade.contains(ProjectChat.class.getSimpleName())
            ){
                resolvingMessage.setChat(null);
                return;
            }
            ChatProcessor.getInstance().resolveChatGeneric(resolvingMessage.getChat(), resolvingCascade);
        }
    }
    private  void resolveMessageSender(){
        if(resolvingMessage.getSender()!=null){
            if(resolvingCascade.contains(User.class.getSimpleName())){
                resolvingMessage.setSender(null);
                return;
            }
            UserProcessor.getInstance().resolveUser(resolvingMessage.getSender(), resolvingCascade);
        }
    }
    
}
