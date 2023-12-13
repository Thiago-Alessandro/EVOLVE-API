package net.weg.taskmanager.model;

import org.hibernate.collection.spi.PersistentBag;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.TypeVariable;
import java.util.Collection;


public class ResolveStackOverflow {

    public static <T> T getObjectWithoutStackOverflow(T obj) {

        if (obj != null) {
            System.out.println("a");
            resolveObjectIfContainsGeneric(obj,Projeto.class,"getProjeto");

            System.out.println("b");
            resolveObjectIfContainsGeneric(obj, Collection.class ,"getProjetos");

            System.out.println("c");
            resolveObjectIfContainsGeneric(obj, Collection.class, "getMessages" );

            System.out.println("d");
            resolveObjectIfContainsGeneric(obj, Chat.class, "getChat" );

//            resolveObjectIfContainsProject(obj);
//            resolveObjectIfContainsProjectCollection(obj);
        }
        return obj;
    }

    private static void resolveProject(Projeto projeto, String objClassName) {


        if(projeto.getTarefas()!=null){
            if(objClassName.equals("Tarefa")){
                projeto.setTarefas(null);
            } else {
                for(Tarefa tarefa : projeto.getTarefas()){
                    tarefa.setProjeto(null);
                }
            }
        }

    }

    private static <T> void resolveObjectIfContainsGeneric(Object obj, Class<T> expectedAtributeClass, String atributeGetMethod){
        try {
            System.out.println(1);
            Method getChatMethod = obj.getClass().getMethod(atributeGetMethod);

            System.out.println(2);
            Object atributeObj = getChatMethod.invoke(obj);
            if (expectedAtributeClass.isInstance(atributeObj)) {
                System.out.println(3);
                T atribute = (T) atributeObj;
                resolveObject(atribute,obj.getClass().getSimpleName());
            }

        }catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException ignore){}
    }

    private static <T> void resolveObject(T atribute, String objectClassName){
        switch (atribute.getClass().getSimpleName()){
            case "Projeto" -> resolveProject((Projeto) atribute, objectClassName);
            //aparentemente PersistentBag é para toda Collection entao não necessariamente seria projeto, ou seja, tem que pensar em alguma lógica para garantir que seja uma colleção de projetos
            case "PersistentBag" -> {
                for(Message message:(Collection<Message>)atribute){
                    resolveMessage(message, objectClassName);
                }
//                for(Projeto projeto:(Collection<Projeto>)atribute){
//                    resolveProject(projeto, objectClassName);
//                }
            }
            case "UserChat" -> resolveChat( (Chat) atribute, objectClassName);
//            case "Message" ->
            default -> System.out.println(atribute.getClass().getSimpleName());
        }
    }


    private static void resolveChat(Chat chat, String objClassName){
        chat.setMessages(null);
    }

    private static void resolveMessage(Message message,String objClassName){
        if(message.getChat()!=null){
            if(objClassName.equals("UserChat")){
                message.setChat(null);
            } else {
                message.getChat().setMessages(null);
            }

            if(!message.getSender().getFotoPerfil().contains("data:image/png;base64,")){
                message.getSender().setFotoPerfil(addBase64Prefix(message.getSender().getFotoPerfil()));
                System.out.println(message);
            }
        }
    }




    //tratar o usuario
    private static String addBase64Prefix(String base64Image) {
        return "data:image/png;base64," + base64Image;
    }

}
