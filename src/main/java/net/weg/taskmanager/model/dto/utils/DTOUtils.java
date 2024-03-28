package net.weg.taskmanager.model.dto.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import net.weg.taskmanager.model.abstracts.Chat;
import net.weg.taskmanager.model.dto.get.*;
import net.weg.taskmanager.model.dto.shortDTOs.*;
import net.weg.taskmanager.model.entity.*;
import net.weg.taskmanager.model.enums.Priority;
import net.weg.taskmanager.model.record.PriorityRecord;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;

import java.util.Base64;
import java.util.Collection;

@Data
@AllArgsConstructor
public class DTOUtils {

    private static final ModelMapper modelMapper = new ModelMapper();

    public static ShortUserDTO userToShortUserDTO(User user){
        if (user != null) {
            return new ShortUserDTO(user);
        }
        return null;
    }
    public static Collection<ShortUserDTO> usersToShortUserDTO(Collection<User> users){
        if(users != null){
            return users.stream()
                    .map(DTOUtils::userToShortUserDTO).toList();
        }
        return null;
    }

    public static GetUserDTO userToGetUserDTO(User user){
        if(user != null){
            return new GetUserDTO(user);
        }
        return null;
    }
    public static Collection<GetUserDTO> usersToGetUserDTOs(Collection<User> users){
        if(users != null) {
            return users.stream()
                    .map(DTOUtils::userToGetUserDTO).toList();
        }
        return null;
    }

    public static GetTaskDTO taskToTaskDTO(Task task){
        if(task != null){
            return new GetTaskDTO(task);
        }
        return null;
    }
    public static Collection<GetTaskDTO> tasksToGetTaskDTOS(Collection<Task> tasks){
        if(tasks != null){
            return tasks.stream()
                    .map(DTOUtils::taskToTaskDTO).toList();
        }
        return null;
    }

    public static GetTeamDTO teamToGetTeamDTO(Team team){
        if(team != null){
            return new GetTeamDTO(team);
        }
        return null;
    }
    public static Collection<GetTeamDTO> teamsToGetTeamDTOS(Collection<Team> teams){
        if(teams != null){
            return teams.stream()
                    .map(DTOUtils::teamToGetTeamDTO).toList();
        }
        return null;
    }

    public static GetMessageDTO messageToGetMessageDTO(Message message){
        if(message != null){
            return new GetMessageDTO(message);
        }
        return null;
    }
    public static Collection<GetMessageDTO> messageToGetMessageDTOS(Collection<Message> messages){
        if(messages != null){
            return messages.stream()
                    .map(DTOUtils::messageToGetMessageDTO).toList();
        }
        return null;
    }

    public static ShortMessageDTO messageToShortMessageDTO(Message message){
        if(message != null){
            return new ShortMessageDTO(message);
        }
        return null;
    }
    public static Collection<ShortMessageDTO> messageToShortMessageDTOS(Collection<Message> messages){
        if(messages != null){
            return messages.stream()
                    .map(DTOUtils::messageToShortMessageDTO).toList();
        }
        return null;
    }



    public static GetProjectDTO projectToGetProjectDTO(Project project){
        if(project != null){
            return new GetProjectDTO(project);
        }
        return null;
    }
    public static Collection<GetProjectDTO> projectToGetProjectDTOS(Collection<Project> projects){
        if(projects != null){
            return projects.stream()
                    .map(DTOUtils::projectToGetProjectDTO).toList();
        }
        return null;
    }

    public static ShortProjectDTO projectToShortProjectDTO(Project project){
        if(project != null){
            return new ShortProjectDTO(project);
        }
        return null;
    }
    public static Collection<ShortProjectDTO> projectToShortProjectDTOS(Collection<Project> projects){
        if(projects != null){
            return projects.stream()
                    .map(DTOUtils::projectToShortProjectDTO).toList();
        }
        return null;
    }


    public static ShortTeamDTO teamToShortTeamDTO(Team team){
        if (team != null){
            return new ShortTeamDTO(team);
        }
        return null;
    }
    public static Collection<ShortTeamDTO> teamToShortTeamDTOS(Collection<Team> teams){
        if (teams != null){
            return teams.stream().map(DTOUtils::teamToShortTeamDTO).toList();
        }
        return null;
    }

    public static PriorityRecord priorityToPriorityRecord(Priority priority){
        if(priority != null){
            PriorityRecord priorityRecord = new PriorityRecord(priority.name(), priority.backgroundColor);
            return priorityRecord;
        }
        return null;
//        Priority prioritySaved = Priority.valueOf(priority.name());
//        prioritySaved.backgroundColor = putTaskDTO.getPriority().backgroundColor();
    }


    public static GetChatDTO chatToGetChatDTO(Chat chat){
        if(chat != null){
            return new GetChatDTO(chat);
        }
        return null;
    }
    public static Collection<GetChatDTO> chatToGetChatDTOS(Collection<Chat> chats){
        if(chats != null){
            return chats.stream()
                    .map(DTOUtils::chatToGetChatDTO).toList();
        }
        return null;
    }

    public static ShortChatDTO chatToShortChatDTO(Chat chat){
        if(chat != null){
            return new ShortChatDTO(chat);
        }
        return null;
    }
    public static Collection<ShortChatDTO> chatToShortChatDTOS(Collection<Chat> chats){
        if(chats != null){
            return chats.stream()
                    .map(DTOUtils::chatToShortChatDTO).toList();
        }
        return null;
    }

    public static GetProjectChatDTO chatToGetProjectChatDTO(ProjectChat chat){
        if(chat != null){
            return new GetProjectChatDTO(chat);
        }
        return null;
    }
    public static Collection<GetProjectChatDTO> chatToGetProjectChatDTOS(Collection<ProjectChat> chats){
        if(chats != null){
            return chats.stream()
                    .map(DTOUtils::chatToGetProjectChatDTO).toList();
        }
        return null;
    }

    public static GetTeamChatDTO chatToGetTeamChatDTO(TeamChat chat){
        if(chat != null){
            return new GetTeamChatDTO(chat);
        }
        return null;
    }
    public static Collection<GetTeamChatDTO> chatToGetTeamChatDTOS(Collection<TeamChat> chats){
        if(chats != null){
            return chats.stream()
                    .map(DTOUtils::chatToGetTeamChatDTO).toList();
        }
        return null;
    }

    public static GetUserChatDTO chatToGetUserChatDTO(UserChat chat){
        if(chat != null){
            return new GetUserChatDTO(chat);
        }
        return null;
    }
    public static Collection<GetUserChatDTO> chatToGetUserChatDTOS(Collection<UserChat> chats){
        if(chats != null){
            return chats.stream()
                    .map(DTOUtils::chatToGetUserChatDTO).toList();
        }
        return null;
    }

    public static GetFileDTO fileToGetFileDTO(File file){
        if(file!=null){
            GetFileDTO getFileDTO = new GetFileDTO();
            BeanUtils.copyProperties(file, getFileDTO);
            getFileDTO.setData(formatFileDataBytesToBase64String(file));
            return getFileDTO;
        }
        return null;
    }
    public static Collection<GetFileDTO> fileToGetFileDTOS(Collection<File> files){
        if(files != null){
            return files.stream().map(DTOUtils::fileToGetFileDTO).toList();
        }
        return null;
    }

//    public GetTaskDTO

    private static String formatFileDataBytesToBase64String(File file){
        return getFileBase64prefix(file) + byteArrayToBase64String(file.getData());
    }

    private static String getFileBase64prefix(File file){
        return "data:" + file.getType() + ";base64,";
    }

    private static String byteArrayToBase64String(byte[] bytes){
        return Base64.getEncoder().encodeToString(bytes);
    }

}
