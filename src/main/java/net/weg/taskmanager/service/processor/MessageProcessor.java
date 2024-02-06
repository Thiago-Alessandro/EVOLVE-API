package net.weg.taskmanager.service.processor;

import net.weg.taskmanager.model.Message;
import net.weg.taskmanager.model.ProjectChat;
import net.weg.taskmanager.model.TeamChat;
import net.weg.taskmanager.model.UserChat;

public class MessageProcessor {

    private static String objClassName;
    private static Message resolvingMessage;
    
    protected static void resolveMessage(Message message, String objectClassName){

        resolvingMessage = message;
        objClassName = objectClassName;

        resolveMessageChat();

    }
    
    private static void resolveMessageChat(){
        //futuramente tirar isso daqui pois estara sendo tratado no proprio usuario
//        if(!resolvingMessage.getSender().getProfilePicture().contains(FileProcessor.getImageBase64Prefix())){
//            resolvingMessage.getSender().setProfilePicture(FileProcessor.addBase64Prefix(resolvingMessage.getSender().getProfilePicture()));
//        }
        
        if(resolvingMessage.getChat()!=null){
            
            if(objClassName.equals(UserChat.class.getSimpleName()) ||
                    objClassName.equals(TeamChat.class.getSimpleName()) ||
                    objClassName.equals(ProjectChat.class.getSimpleName())
            ){
                resolvingMessage.setChat(null);
                return;
            }
            ChatProcessor.resolveChat(resolvingMessage.getChat(), resolvingMessage.getClass().getSimpleName());
        }
    }
    
}
