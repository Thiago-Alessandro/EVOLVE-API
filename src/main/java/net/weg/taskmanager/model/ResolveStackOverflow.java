package net.weg.taskmanager.model;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class ResolveStackOverflow {

    public static <T> T getObjectWithoutStackOverflow(T obj) {

        if (obj != null) {
            resolveObjectIfContainsGeneric(obj,new Projeto(),"getProjeto");
            resolveObjectIfContainsGeneric(obj,new ArrayList<Projeto>(),"getProjetos");
//            resolveObjectIfContainsProject(obj);
//            resolveObjectIfContainsProjectCollection(obj);

//            resolveObjectIfContainsGeneric(obj, new UserChat(),"getChat");
            return obj;
        }
        return null;
    }

    private static void resolveProject(Projeto projeto, String objClassName) {
        System.out.println("estou qaqui");
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

    private static <T> void resolveObjectIfContainsGeneric(Object obj, T expectedAtribute, String atributeGetMethod){
        try {
            System.out.println(1);
            Method getChatMethod = obj.getClass().getMethod(atributeGetMethod);
            if (getChatMethod != null) {
                System.out.println(2);
                Object atributeObj = getChatMethod.invoke(obj);
                if (atributeObj != null) {
                    System.out.println(3);
                    T atribute = (T) atributeObj;
                    resolveObject(atribute,obj.getClass().getSimpleName());
                }
            }
        }catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e){

        }
    }

    private static <T> void resolveObject(T atribute, String objectClassName){
        switch (atribute.getClass().getSimpleName()){
            case "Projeto" -> resolveProject((Projeto) atribute, objectClassName);
//            case "PersistentBag" -> res
            default ->             System.out.println(atribute.getClass().getSimpleName());
        }
    }


//    private static void resolveObjectIfContainsProject(Object obj){
//        try {
//            // Supondo que o objeto genérico T possui um método getProjeto()
//            Method getProjetoMethod = obj.getClass().getMethod("getProjeto");
//            if (getProjetoMethod != null) {
//                Object projetoObj = getProjetoMethod.invoke(obj);
//                if (projetoObj instanceof Projeto) {
//                    Projeto projeto = (Projeto) projetoObj;
//                    ResolveProject(projeto,obj.getClass());
//                }
//            }
//        }catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e){
//
//        }
//    }
//
//    private static void resolveObjectIfContainsProjectCollection(Object obj){
//        try {
//            // Supondo que o objeto genérico T possui um método getListaProjetos()
//            Method getListaProjetosMethod = obj.getClass().getMethod("getProjetos");
//            if (getListaProjetosMethod != null) {
//                Object listaProjetosObj = getListaProjetosMethod.invoke(obj);
//                if (listaProjetosObj instanceof Collection<?>) {
//                    Collection<Projeto> listaProjetos =  (Collection<Projeto>) listaProjetosObj;
//                    for (Projeto projeto : listaProjetos) {
//                        ResolveProject(projeto,obj.getClass());
//                    }
//                }
//            }
//        }catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e){
//
//        }
//    }



}
