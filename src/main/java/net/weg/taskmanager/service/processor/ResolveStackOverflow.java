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

            //ProjectChat
            resolveObjectIfContainsGeneric(obj, Project.class, "getProject", null);

            resolveObjectIfContainsGeneric(obj, Chat.class, "getChat" , null);

            resolveObjectIfContainsGeneric(obj, Chat.class, "getChats" , null);

            resolveObjectIfContainsGeneric(obj, UserChat.class, "getChat" , null);

            resolveObjectIfContainsGeneric(obj, UserChat.class, "getChats" , null);

            //TeamChat
            resolveObjectIfContainsGeneric(obj, Team.class ,"getTeam", null);

            resolveObjectIfContainsGeneric(obj, User.class, "getUser", null);
            System.out.println(obj.getClass());

            resolveObjectIfContainsGeneric(obj, User.class, "getAdministrator", null);

            //Chat
            resolveObjectIfContainsGeneric(obj, Collection.class, "getMessages" , Message.class);

            resolveObjectIfContainsGeneric(obj, Collection.class ,"getProjects", Project.class);

            resolveObjectIfContainsGeneric(obj, Collection.class ,"getParticipants", User.class);

            //Chat,
            resolveObjectIfContainsGeneric(obj, Collection.class, "getUsers", User.class);

            resolveObjectIfContainsGeneric(obj, Collection.class ,"getTeams", Team.class);
//            OBS: a collection seria do atributo do objeto a ser pego pelo metodo get

            resolveObjectIfContainsGeneric(obj, Collection.class ,"getManagedTeams", Team.class);

            //Project
            resolveObjectIfContainsGeneric(obj, Collection.class, "getTasks", Task.class);

//            resolveObjectIfContainsProject(obj);
//            resolveObjectIfContainsProjectCollection(obj);
        }
        return obj;
    }

    private static <T, OBJ, C> void resolveObjectIfContainsGeneric(OBJ obj, Class<T> expectedAtributeClass, String atributeGetMethod, Class<C> expectedCollectionClass){
        try {

            Method getMethod = obj.getClass().getMethod(atributeGetMethod);


            Object atributeObj = getMethod.invoke(obj);

            if(atributeObj!=null){
                if (expectedAtributeClass.isInstance(atributeObj)) {
                    T atribute = (T) atributeObj;
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
