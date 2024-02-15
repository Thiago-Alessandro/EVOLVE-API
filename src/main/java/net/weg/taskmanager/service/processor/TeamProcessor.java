package net.weg.taskmanager.service.processor;

import net.weg.taskmanager.model.*;

public class TeamProcessor {

    private static Team resolvingTeam;
    private static String objClassName;
    private static String teamClassName;

    protected static void resolveTeam(Team team, String objectClassName){

        resolvingTeam = team;
        objClassName = objectClassName;
        teamClassName = Team.class.getSimpleName();

//        System.out.println("1");
        resolveTeamParticipants();
//        System.out.println("2");
        resolveTeamAdministrator();
//        System.out.println("3 - Team: " + objClassName);
        resolveTeamChat();
//        System.out.println("4 - Team: " + objClassName);
        resolveTeamProjects();
//        System.out.println("5");

    }

    private static void resolveTeamChat(){
        if(resolvingTeam.getChat() != null){
            if(objClassName.equals(TeamChat.class.getSimpleName()) ){
                resolvingTeam.setChat(null);
                return;
            }
            ChatProcessor.resolveChat(resolvingTeam.getChat(), teamClassName);
        }
    }

    private static void resolveTeamParticipants(){
        if(resolvingTeam.getParticipants() != null){
            if(objClassName.equals(User.class.getSimpleName())){
                resolvingTeam.setParticipants(null);
                return;
            }
            resolvingTeam.getParticipants().stream()
                    .forEach(user -> UserProcessor.resolveUser(user, teamClassName));
        }
    }

    private static void resolveTeamAdministrator(){
        if(resolvingTeam.getAdministrator() != null){
            if(objClassName.equals(User.class.getSimpleName())){
                resolvingTeam.setAdministrator(null);
                return;
            }
            UserProcessor.resolveUser(resolvingTeam.getAdministrator(), teamClassName);
        }
    }

    private static void resolveTeamProjects(){
//        System.out.println("oioioioioio");
        if(resolvingTeam.getProjects()!=null){
            if(objClassName.equals(Project.class.getSimpleName())){
                resolvingTeam.setProjects(null);
                return;
            }
            resolvingTeam.getProjects().stream()
                    .forEach(project -> ProjectProcessor.resolveProject(project, teamClassName));
        }
    }

}
