package net.weg.taskmanager.service.processor;

import net.weg.taskmanager.model.Message;
import net.weg.taskmanager.model.ProjectChat;
import net.weg.taskmanager.model.TeamChat;
import net.weg.taskmanager.model.UserChat;

public class MessageProcessor {

    private static String objClassName;
    private static Message resovingMessage;
    
    protected static void resolveMessage(Message message, String objectClassName){

        resovingMessage = message;
        objClassName = objectClassName;

        resolveMessageChat();

    }
    
    private static void resolveMessageChat(){
        //futuramente tirar isso daqui pois estara sendo tratado no proprio usuario
        if(!resovingMessage.getSender().getFotoPerfil().contains(FileProcessor.getImageBase64Prefix())){
            resovingMessage.getSender().setFotoPerfil(FileProcessor.addBase64Prefix(resovingMessage.getSender().getFotoPerfil()));
        }
        
        if(resovingMessage.getChat()!=null){
            
            if(objClassName.equals(UserChat.class.getSimpleName()) ||
                    objClassName.equals(TeamChat.class.getSimpleName()) ||
                    objClassName.equals(ProjectChat.class.getSimpleName())
            ){
                resovingMessage.setChat(null);
                return;
            }
            ChatProcessor.resolveChat(resovingMessage.getChat(), resovingMessage.getClass().getSimpleName());
        }
    }
    
}
