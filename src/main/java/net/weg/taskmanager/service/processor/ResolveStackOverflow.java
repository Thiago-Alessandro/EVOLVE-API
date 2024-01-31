package net.weg.taskmanager.service.processor;

import net.weg.taskmanager.model.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;


public class ResolveStackOverflow {

    private static final String messageClassName = "Message";
    private static final String userChatClassName = "UserChat";
    private static final String projectClassName = "Project";
    private static final String teamClassName = "Team";
    private static final String userClassName = "User";
    private static final String persistentBagClassName = "PersistentBag";

    public static <T> T getObjectWithoutStackOverflow(T obj) {

        if (obj != null) {
//            System.out.println("a");
            resolveObjectIfContainsGeneric(obj, Project.class, "getProject", null);

//            System.out.println("b");
            resolveObjectIfContainsGeneric(obj, Chat.class, "getChat" , null);

//            System.out.println("c");
            resolveObjectIfContainsGeneric(obj, Collection.class, "getMessages" , Message.class);

//            System.out.println("d");
            resolveObjectIfContainsGeneric(obj, Collection.class ,"getProjects", Project.class);

//            System.out.println("e");
            resolveObjectIfContainsGeneric(obj, Collection.class ,"getParticipants", User.class);

//            System.out.println("e2");
            resolveObjectIfContainsGeneric(obj, Collection.class, "getUsers", User.class);

//            System.out.println("f");
            resolveObjectIfContainsGeneric(obj, Collection.class ,"getTeams", Team.class);



//            resolveObjectIfContainsProject(obj);
//            resolveObjectIfContainsProjectCollection(obj);
        }
        return obj;
    }

    private static void resolveProject(Project project, String objClassName) {


        if(project.getTasks()!=null){
            if(objClassName.equals(Task.class.getSimpleName())){
                project.setTasks(null);
            } else {
                for(Task task : project.getTasks()){
                    task.setProject(null);
                }
            }
        }

    }

    private static <T, OBJ, C> void resolveObjectIfContainsGeneric(OBJ obj, Class<T> expectedAtributeClass, String atributeGetMethod, Class<C> expectedCollectionClass){
        try {

            Method getMethod = obj.getClass().getMethod(atributeGetMethod);
//            System.out.println(1);

            Object atributeObj = getMethod.invoke(obj);
//            System.out.println(2);

            if (expectedAtributeClass.isInstance(atributeObj)) {
//                System.out.println(3);
                T atribute = (T) atributeObj;
                resolveObject(atribute, expectedCollectionClass, obj.getClass().getSimpleName());
            }

        }catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException ignore){}
    }

    private static <T,C> void resolveObject(T atribute, Class<C> expectedCollectionClass, String objClassName){

        switch (atribute.getClass().getSimpleName()){

            case projectClassName -> resolveProject((Project) atribute, objClassName);

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
                for(Project project:(Collection<Project>)atribute){
                    resolveProject(project, objClassName);
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
        }
    }

}
