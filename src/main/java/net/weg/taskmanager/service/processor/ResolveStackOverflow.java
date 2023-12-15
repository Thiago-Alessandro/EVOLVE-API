package net.weg.taskmanager.service.processor;

import net.weg.taskmanager.model.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;


public class ResolveStackOverflow {

    private static final String messageClassName = "Message";
    private static final String userChatClassName = "UserChat";
    private static final String projectClassName = "Projeto";
    private static final String teamClassName = "Equipe";
    private static final String userClassName = "Usuario";
    private static final String persistentBagClassName = "PersistentBag";

    public static <T> T getObjectWithoutStackOverflow(T obj) {

        if (obj != null) {
            System.out.println("a");
            resolveObjectIfContainsGeneric(obj, Projeto.class, "getProjeto", null);

            System.out.println("b");
            resolveObjectIfContainsGeneric(obj, Chat.class, "getChat" , null);

            System.out.println("c");
            resolveObjectIfContainsGeneric(obj, Collection.class, "getMessages" , Message.class);

            System.out.println("d");
            resolveObjectIfContainsGeneric(obj, Collection.class ,"getProjetos", Projeto.class);

            System.out.println("e");
            resolveObjectIfContainsGeneric(obj, Collection.class ,"getParticipantes", Usuario.class);

            System.out.println("e2");
            resolveObjectIfContainsGeneric(obj, Collection.class, "getUsuarios", Usuario.class);

            System.out.println("e3");
            resolveObjectIfContainsGeneric(obj, Collection.class, "getUsers", Usuario.class);

            System.out.println("f");
            resolveObjectIfContainsGeneric(obj, Collection.class ,"getEquipes", Equipe.class);



//            resolveObjectIfContainsProject(obj);
//            resolveObjectIfContainsProjectCollection(obj);
        }
        return obj;
    }

    private static void resolveProject(Projeto projeto, String objClassName) {


        if(projeto.getTarefas()!=null){
            if(objClassName.equals(Tarefa.class.getSimpleName())){
                projeto.setTarefas(null);
            } else {
                for(Tarefa tarefa : projeto.getTarefas()){
                    tarefa.setProjeto(null);
                }
            }
        }

    }

    private static <T, OBJ, C> void resolveObjectIfContainsGeneric(OBJ obj, Class<T> expectedAtributeClass, String atributeGetMethod, Class<C> expectedCollectionClass){
        try {

            Method getMethod = obj.getClass().getMethod(atributeGetMethod);
            System.out.println(1);

            Object atributeObj = getMethod.invoke(obj);
            System.out.println(2);

            if (expectedAtributeClass.isInstance(atributeObj)) {
                System.out.println(3);
                T atribute = (T) atributeObj;
                resolveObject(atribute, expectedCollectionClass, obj.getClass().getSimpleName());
            }

        }catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException ignore){}
    }

    private static <T,C> void resolveObject(T atribute, Class<C> expectedCollectionClass, String objClassName){

        switch (atribute.getClass().getSimpleName()){

            case projectClassName -> resolveProject((Projeto) atribute, objClassName);

            case userChatClassName -> ChatProcessor.resolveChat( (Chat) atribute, objClassName);
//            case "Message" ->
            case persistentBagClassName -> resolveCollectionAtribute((Collection<C>) atribute, expectedCollectionClass, objClassName);

            default -> System.out.println("ResolveStackOverflow: O nome da classe não foi encontrado. Classe: " + atribute.getClass().getSimpleName());
        }
    }

    private static <C> void resolveCollectionAtribute(Collection<C> atribute, Class<C> expectedCollectionClass, String objClassName){
        switch (expectedCollectionClass.getSimpleName()) {
            case messageClassName -> {
                for (Message message : (Collection<Message>) atribute) {
                    MessageProcessor.resolveMessage(message, objClassName);
                }
            }
            case projectClassName -> {
                for(Projeto project:(Collection<Projeto>)atribute){
                    resolveProject(project, objClassName);
                }
            }
            case teamClassName -> {
                for (Equipe team : (Collection<Equipe>) atribute){
                    TeamProcessor.resolveTeam(team, objClassName);
                }
            }
            case userClassName -> {
                for(Usuario user : (Collection<Usuario>) atribute){
                    UserProcessor.resolveUser(user, objClassName);
                }
            }
        }
    }

}
