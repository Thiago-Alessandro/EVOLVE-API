package net.weg.taskmanager.service.processor;

import net.weg.taskmanager.model.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;


public class ResolveStackOverflow {

    private static final String messageClassName = "Message";
    private static final String userChatClassName = "UserChat";
    private static final String teamChatClassName = "TeamChat";
    private static final String projectClassName = "Project";
    private static final String teamClassName = "Team";
    private static final String userClassName = "User";
    private static final String taskClassname = "Task";
    private static final String persistentBagClassName = "PersistentBag";


    public static <T> T getObjectWithoutStackOverflow(T obj) {

        if (obj != null) {

            //EN
            //OBS: The comments above the following methods are about wich models have the atribute to be resolved
            //     and the 'obj' parameter is an instance of the expected model wich will be tested inside the method
            //PT-BR
            //OBS: Os comentários acima dos seguintes métodos são a respeito de quais models possuem o atributo a ser resolvido
            //     e o parametro 'obj' é uma instancia da model esperada o que será testado dentro do método

//            System.out.println("a");
            //ProjectChat
            resolveObjectIfContainsGeneric(obj, Project.class, "getProject", null);

//            System.out.println("b");
            resolveObjectIfContainsGeneric(obj, Chat.class, "getChat" , null);

//            System.out.println("chat plural");
            resolveObjectIfContainsGeneric(obj, Chat.class, "getChats" , null);
//            System.out.println("b2");
            resolveObjectIfContainsGeneric(obj, UserChat.class, "getChat" , null);

//            System.out.println("chat plural");
            resolveObjectIfContainsGeneric(obj, UserChat.class, "getChats" , null);

//            System.out.println("dentro da hi lux");
            //TeamChat
            resolveObjectIfContainsGeneric(obj, Team.class ,"getTeam", null);

//            System.out.println("Ela vem no tustussts");
            resolveObjectIfContainsGeneric(obj, User.class, "getUser", null);
            System.out.println(obj.getClass());

//            System.out.println("ohoho");
            resolveObjectIfContainsGeneric(obj, User.class, "getAdministrator", null);

//            System.out.println("c");
            //Chat
            resolveObjectIfContainsGeneric(obj, Collection.class, "getMessages" , Message.class);

//            System.out.println("d");
            resolveObjectIfContainsGeneric(obj, Collection.class ,"getProjects", Project.class);

//            System.out.println("e");
            resolveObjectIfContainsGeneric(obj, Collection.class ,"getParticipants", User.class);

//            System.out.println("e2");
            //Chat,
            resolveObjectIfContainsGeneric(obj, Collection.class, "getUsers", User.class);

//            System.out.println("f");
            resolveObjectIfContainsGeneric(obj, Collection.class ,"getTeams", Team.class);
//            OBS: a collection seria do atributo do objeto a ser pego pelo metodo get

//            System.out.println("f2");
            resolveObjectIfContainsGeneric(obj, Collection.class ,"getManagedTeams", Team.class);

//            System.out.println("g");
            //Project
            resolveObjectIfContainsGeneric(obj, Collection.class, "getTasks", Task.class);

            resolveObjectIfContainsGeneric(obj, Collection.class, "getAssociates", User.class);
//            resolveObjectIfContainsProject(obj);
//            resolveObjectIfContainsProjectCollection(obj);
        }
        return obj;
    }

    private static <T, OBJ, C> void resolveObjectIfContainsGeneric(OBJ obj, Class<T> expectedAtributeClass, String atributeGetMethod, Class<C> expectedCollectionClass){
        try {

            Method getMethod = obj.getClass().getMethod(atributeGetMethod);
//            System.out.println(1);

            Object atributeObj = getMethod.invoke(obj);
//            System.out.println(2);

            if(atributeObj!=null){
                System.out.println(atributeObj.getClass());
                if (expectedAtributeClass.isInstance(atributeObj)) {
    //                System.out.println(3);
                    T atribute = (T) atributeObj;
                    System.out.println("foi");
                    resolveObject(atribute, expectedCollectionClass, obj.getClass().getSimpleName());
                }
            }

        }catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException ignore){}
    }

    private static <T,C> void resolveObject(T atribute, Class<C> expectedCollectionClass, String objClassName){

        switch (atribute.getClass().getSimpleName()){

            case projectClassName -> ProjectProcessor.resolveProject((Project) atribute, objClassName);

            case userChatClassName, teamChatClassName -> ChatProcessor.resolveChat( (Chat) atribute, objClassName);

            case persistentBagClassName -> resolveCollectionAtribute((Collection<C>) atribute, expectedCollectionClass, objClassName);

            case userClassName -> UserProcessor.resolveUser((User)atribute,objClassName);

            case teamClassName -> TeamProcessor.resolveTeam((Team) atribute, objClassName);

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
                for(Project project:(Collection<Project>)atribute){
                    ProjectProcessor.resolveProject(project, objClassName);
                }
            }
            case teamClassName -> {
                for (Team team : (Collection<Team>) atribute){
                    TeamProcessor.resolveTeam(team, objClassName);
                }
            }
            case userClassName -> {
                for(User user : (Collection<User>) atribute){
                    UserProcessor.resolveUser(user, objClassName);
                }
            }

            case taskClassname -> {
                for(Task task : (Collection<Task>) atribute){
                    TaskProcessor.resolveTask(task, objClassName);
                }
            }
        }
    }

}
