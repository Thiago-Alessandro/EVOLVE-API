package net.weg.taskmanager.service.processor;

import net.weg.taskmanager.model.Equipe;
import net.weg.taskmanager.model.Usuario;

public class TeamProcessor {

    private static Equipe resolvingTeam;
    private static String objClassName;

    protected static void resolveTeam(Equipe team, String objectClassName){

        resolvingTeam = team;
        objClassName = objectClassName;

    }

    private static void resolveTeamMembers(){
        if(resolvingTeam.getParticipantes() != null){
            if(objClassName.equals(Usuario.class.getSimpleName())){
                resolvingTeam.setParticipantes(null);
                return;
            }
            for(Usuario user : resolvingTeam.getParticipantes()){
                UserProcessor.resolveUser(user, resolvingTeam.getClass().getSimpleName());
            }
        }
    }

}
