package net.weg.taskmanager.service.processor;

import lombok.NoArgsConstructor;
import net.weg.taskmanager.model.dto.get.GetFileDTO;
import net.weg.taskmanager.model.dto.get.GetUserDTO;
import net.weg.taskmanager.model.entity.Task;
import net.weg.taskmanager.model.entity.Team;
import net.weg.taskmanager.model.entity.User;
import net.weg.taskmanager.model.entity.UserChat;
import org.springframework.beans.BeanUtils;

import java.util.*;

@NoArgsConstructor
public class UserProcessor {

    private User resolvingUser;
    private final String userClassName = User.class.getSimpleName();
    private ArrayList<String> resolvingCascade;

    public static UserProcessor getInstance(){
        return new UserProcessor();
    }

    public User resolveUser(User user, ArrayList<String> _resolvingCascade){

        resolvingCascade = _resolvingCascade;
        resolvingCascade.add(userClassName);

        resolvingUser = user;

//        resolveUserTeams();
//        resolveUserManagedTeams();

        resolveUserCreatedTasks();
        resolveUserChats();

        resolvingCascade.remove(userClassName);

        return resolvingUser;
    }

    public User resolveUser(User user){
        return resolveUser(user, new ArrayList<>());
    }
//    public Collection<GetUserDTO> resolveUsers(Collection<User> users){
//        return resolveUsers(users, new ArrayList<>());
//    }
//    public Collection<GetUserDTO> resolveUsers(Collection<User> users, ArrayList<String> _resolvingCascade){
//        Collection<GetUserDTO> getUserDTOS = new HashSet<>();
//        users.forEach(user -> getUserDTOS.add(resolveUser(user,_resolvingCascade)));
//        return getUserDTOS;
//    }

    private void resolveUserChats(){
        if(resolvingUser.getChats()!=null){
            if(resolvingCascade.contains(UserChat.class.getSimpleName())){
                resolvingUser.setChats(null);
                return;
            }
            resolvingUser.getChats()
                    .forEach(chat -> ChatProcessor.getInstance().resolveChat(chat, resolvingCascade));
        }
    }

<<<<<<< HEAD
//    private void resolveUserTeams(){
//        if(resolvingUser.getTeams() != null){
//            if( resolvingCascade.contains(Team.class.getSimpleName())){
//                resolvingUser.setTeams(null);
//                return;
//            }
//
//            resolvingUser.getTeams()
//                    .forEach(team -> TeamProcessor.getInstance().resolveTeam(team, resolvingCascade));
//
//        }
//    }
//
//    private void resolveUserManagedTeams(){
//        if(resolvingUser.getManagedTeams() != null){
//            if( resolvingCascade.contains(Team.class.getSimpleName())){
//                resolvingUser.setManagedTeams(null);
//                return;
//            }
//            resolvingUser.getManagedTeams().stream()
//                    .forEach(team -> TeamProcessor.getInstance().resolveTeam(team, resolvingCascade));
//        }
//    }
=======
    private void resolveUserTeams(){
        if(resolvingUser.getTeams() != null){
            if( resolvingCascade.contains(Team.class.getSimpleName())){
                resolvingUser.setTeams(null);
                return;
            }

            resolvingUser.getTeams()
                    .forEach(team -> TeamProcessor.getInstance().resolveTeam(team, resolvingCascade));

        }
    }

    private void resolveUserManagedTeams(){
        if(resolvingUser.getManagedTeams() != null){
            if( resolvingCascade.contains(Team.class.getSimpleName())){
                resolvingUser.setManagedTeams(null);
                return;
            }
            resolvingUser.getManagedTeams()
                    .forEach(team -> TeamProcessor.getInstance().resolveTeam(team, resolvingCascade));
        }
    }
>>>>>>> feature/security-updated

    private void resolveUserCreatedTasks(){
        if(resolvingUser.getCreatedTasks() != null){
            if(resolvingCascade.contains(Task.class.getSimpleName())){
                resolvingUser.setCreatedTasks(null);
                return;
            }
            resolvingUser.getCreatedTasks()
                    .forEach(task -> TaskProcessor.getInstance().resolveTask(task, resolvingCascade));
        }
    }

//    private GetUserDTO getUserDTO(User user){
//        GetUserDTO getUserDTO = new GetUserDTO();
//        BeanUtils.copyProperties(resolvingUser, getUserDTO);
//        setUserDTOImage(user, getUserDTO);
//        return getUserDTO;
//    }
//
//    private void setUserDTOImage(User user, GetUserDTO getUserDTO){
//        if(user.getImage()!=null){
//            if(getUserDTO.getImage()==null){
//                getUserDTO.setImage(new GetFileDTO());
//            }
//            BeanUtils.copyProperties(user.getImage(), getUserDTO.getImage());
//
////            System.out.println(Base64.getEncoder().encodeToString(user.getImage().getData()));
//            System.out.println(FileProcessor.getImageBase64Prefix() + Base64.getEncoder().encodeToString(user.getImage().getData()));
//            getUserDTO.getImage().setData(FileProcessor.getImageBase64Prefix() + Base64.getEncoder().encodeToString(user.getImage().getData()));
//        }
//    }

}
