package net.weg.taskmanager.model;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;


public class ResolveStackOverflow {

    public static <T> void getObjectWithoutStackOverflow(T obj) {

        if (obj != null) {
            resolveObjectIfContainsGeneric(obj,Projeto.class,"getProjeto");
            resolveObjectIfContainsGeneric(obj, Collection.class ,"getProjetos");
//            resolveObjectIfContainsProject(obj);
//            resolveObjectIfContainsProjectCollection(obj);

//            resolveObjectIfContainsGeneric(obj, new UserChat(),"getChat");
        }
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
                for(Projeto projeto:(Collection<Projeto>)atribute){
                    resolveProject(projeto, objectClassName);
                }
            }
            default -> System.out.println(atribute.getClass().getSimpleName());
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
