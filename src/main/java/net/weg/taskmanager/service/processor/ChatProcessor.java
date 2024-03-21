package net.weg.taskmanager.service.processor;

import net.weg.taskmanager.model.abstracts.Chat;
import net.weg.taskmanager.model.entity.*;

import java.util.ArrayList;
public class ChatProcessor {

    private Chat chat;
    private String chatClassName;
    private ArrayList<String> resolvingCascade;

    public static ChatProcessor getInstance(){
        return new ChatProcessor();
    }

    private void setDefaultInfo(Chat resolvingChat, ArrayList<String> _resolvingCascade){
        chat = resolvingChat;
        chatClassName = chat.getClass().getSimpleName();
        resolvingCascade = _resolvingCascade;
        resolvingCascade.add(chatClassName);
    }

    private void genericResolve(Chat resolvingChat, ArrayList<String> _resolvingCascade){
        setDefaultInfo(resolvingChat, _resolvingCascade);
        resolveChatGenericAtributes();
    }

    private void resolveChatGenericAtributes(){
        resolveChatMessages();
        resolveChatUsersGeneric();
    }

    public Chat resolveChatGeneric(Chat resolvingChat, ArrayList<String> _resolvingCascade){
        genericResolve(resolvingChat, _resolvingCascade);

        resolveChatTeam();
        resolveChatProject();

        resolvingCascade.remove(chatClassName);

        return chat;
    }

    public Chat resolveChat(Chat resolvingChat,  ArrayList<String> _resolvingCascade) {

        genericResolve(resolvingChat, _resolvingCascade);
        resolveChatProject();
        resolveChatTeam();

        resolvingCascade.remove(chatClassName);

        return chat;
    }

    public Chat resolveChat(Chat resolvingChat){
        return resolveChat(resolvingChat, new ArrayList<>());
    }


    private void resolveChatTeam(){
        if(chat instanceof TeamChat){
            if(((TeamChat) chat).getTeam() != null){
                if(resolvingCascade.contains(Team.class.getSimpleName())){
                    ((TeamChat) chat).setTeam(null);
                    return;
                }
                TeamProcessor.getInstance().resolveTeam(((TeamChat) chat).getTeam(), resolvingCascade);
            }
        }
    }

    private void resolveChatProject(){
        if(chat instanceof ProjectChat){
            if(((ProjectChat) chat).getProject() != null){
                if(resolvingCascade.contains(Project.class.getSimpleName())){
                    ((ProjectChat) chat).setProject(null);
                    return;
                }
                ProjectProcessor.getInstance().resolveProject(((ProjectChat) chat).getProject(), resolvingCascade);
            }
        }
    }

    private void resolveChatMessages(){
        if(chat.getMessages() != null){
            if(resolvingCascade.contains(Message.class.getSimpleName())){
                chat.setMessages(null);
                return;
            }
            chat.getMessages()
                    .forEach(message -> MessageProcessor.getInstance().resolveMessage(message, resolvingCascade));
        }
    }





    private void resolveChatUsersGeneric(){
        if(chat.getUsers()!=null){
            if(resolvingCascade.contains(User.class.getSimpleName())){
                chat.setUsers(null);
                return;
            }
            chat.getUsers()
                    .forEach(user -> UserProcessor.getInstance().resolveUser(user, resolvingCascade));
        }
    }


}
