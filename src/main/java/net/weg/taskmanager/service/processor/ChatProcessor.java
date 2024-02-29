package net.weg.taskmanager.service.processor;

import net.weg.taskmanager.controller.TaskController;
import net.weg.taskmanager.model.*;

import java.util.ArrayList;

public class ChatProcessor {

    private static Chat chat;
    private static ArrayList<String> resolvingCascade;

    public static Chat resolveChatGeneric(Chat resolvingChat, String objClassName, ArrayList<String> _resolvingCascade){
        chat = resolvingChat;
        resolvingCascade = _resolvingCascade;
        resolvingCascade.add(objClassName);
        System.out.println("RESOLVENDO CHAT GENERICO");
        System.out.println(chat.getClass().getSimpleName());
//        if(chat.getClass().getSimpleName().equals(TeamChat.class.getSimpleName())){
        resolveChatTeam();
        resolveChatProject();
//        }
        resolveChatMessages();

        resolveChatUsers();
        System.out.println(chat);
        resolvingCascade.remove(objClassName);

        return chat;
    }

    public static Chat resolveChat(UserChat resolvingChat, String objClassName, ArrayList<String> _resolvingCascade){
        chat = resolvingChat;
        resolvingCascade = _resolvingCascade;
        resolvingCascade.add(objClassName);
        System.out.println("RESOLVENDO USERCHAT " + resolvingCascade);
        System.out.println(chat.getClass().getSimpleName());

        resolveChatMessages();
        resolveChatUsers();

        System.out.println(chat);
        resolvingCascade.remove(objClassName);
        System.out.println("TERMINANDO RESOLVENDO USERCHAT " + resolvingCascade);

        return chat;
    }

    public static Chat resolveChat(ProjectChat resolvingChat, String objClassName, ArrayList<String> _resolvingCascade){
        chat = resolvingChat;
        resolvingCascade = _resolvingCascade;
        resolvingCascade.add(objClassName);
        System.out.println("RESOLVENDO PROJECT CHAT " + resolvingCascade);
        System.out.println(chat.getClass().getSimpleName());

        resolveChatProject();

        resolveChatMessages();

        resolveChatUsers();

        System.out.println(chat);
        resolvingCascade.remove(objClassName);

        return chat;
    }

    public static Chat resolveChat(TeamChat resolvingChat, String objClassName, ArrayList<String> _resolvingCascade){
        chat = resolvingChat;
        resolvingCascade = _resolvingCascade;
        resolvingCascade.add(objClassName);
        System.out.println("RESOLVENDO TEAM CHAT " + resolvingCascade);
        System.out.println(chat.getClass().getSimpleName());
        resolveChatTeam();

        resolveChatMessages();

        resolveChatUsers();
        System.out.println(chat);
        resolvingCascade.remove(objClassName);

        return chat;
    }

    public static Chat resolveChat(UserChat resolvingChat){
        return resolveChat(resolvingChat, UserChat.class.getSimpleName(), new ArrayList<>());
    }

    public static Chat resolveChat(TeamChat resolvingChat){
        return resolveChat(resolvingChat, TeamChat.class.getSimpleName(), new ArrayList<>());
    }

    public static Chat resolveChat(ProjectChat resolvingChat){
        return resolveChat(resolvingChat, ProjectChat.class.getSimpleName(), new ArrayList<>());
    }


//    public static Chat resolveChat(Chat resolvingChat){
//        System.out.println(resolvingChat.getClass().getSimpleName());
//        if(resolvingChat instanceof UserChat){
//            System.out.println("sou instance de userchat");
//            return resolveChat(resolvingChat, UserChat.class.getSimpleName(), new ArrayList<>());
//        }else if (resolvingChat instanceof TeamChat){
//            return resolveChat(resolvingChat, TeamChat.class.getSimpleName(), new ArrayList<>());
//        } else if (resolvingChat instanceof ProjectChat) {
//            return resolveChat(resolvingChat, ProjectChat.class.getSimpleName(), new ArrayList<>());
//        }
//        System.out.println("aaaa");
//        return resolveChat(resolvingChat, Chat.class.getSimpleName(), new ArrayList<>());
//    }

    private static void resolveChatUsers(){
        if(chat.getUsers()!=null){
            if(resolvingCascade.contains(User.class.getSimpleName())){
                chat.setUsers(null);
                System.out.println("seteichat users null " + resolvingCascade);
                return;
            }

            for(User user : chat.getUsers()) {
                if (chat.getUsers() != null) {

                    if (chat instanceof UserChat) {
                        System.out.println("sou instancia de userchat");
                        System.out.println(resolvingCascade);
                        UserProcessor.resolveUser(user, UserChat.class.getSimpleName(), resolvingCascade);
                    } else if (chat instanceof TeamChat) {
                        System.out.println("pq1");
                        UserProcessor.resolveUser(user, TeamChat.class.getSimpleName(), resolvingCascade);
                    } else if (chat instanceof ProjectChat) {
                        System.out.println("pq2");
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

    private static void resolveChatProject(){
        if(chat instanceof ProjectChat){
            if(((ProjectChat) chat).getProject() != null){
                if(resolvingCascade.contains(Project.class.getSimpleName())){
                    ((ProjectChat) chat).setProject(null);
                    return;
                }
                ProjectProcessor.resolveProject(((ProjectChat) chat).getProject(), ProjectChat.class.getSimpleName(), resolvingCascade);
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
