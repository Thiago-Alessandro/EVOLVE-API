package net.weg.taskmanager.service.processor;

import net.weg.taskmanager.model.*;

public class MessageProcessor {

    private static String objClassName;
    private static Message resolvingMessage;
    
    protected static void resolveMessage(Message message, String objectClassName){

        resolvingMessage = message;
        objClassName = objectClassName;

        resolveMessageChat();
        resolveMessageSender();

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
//                System.out.println("setei nulo"); 3
                return;
            }
            System.out.println("to indo resolver");
            ChatProcessor.resolveChat(resolvingMessage.getChat(), resolvingMessage.getClass().getSimpleName());
        }
    }
    private static void resolveMessageSender(){
        if(resolvingMessage.getSender()!=null){
            if(objClassName.equals(User.class.getSimpleName()) ||
                    objClassName.equals(UserChat.class.getSimpleName())){
                resolvingMessage.setSender(null);
                return;
            }
//            System.out.println("resolveMessageSender"); 4
//            System.out.println(objClassName); 5 (UserChat)
            UserProcessor.resolveUser(resolvingMessage.getSender(), resolvingMessage.getClass().getSimpleName());
        }
    }
    
}
