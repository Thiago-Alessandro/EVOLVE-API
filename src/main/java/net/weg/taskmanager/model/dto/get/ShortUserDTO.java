package net.weg.taskmanager.model.dto.get;

import net.weg.taskmanager.model.entity.Task;
import net.weg.taskmanager.model.entity.Team;
import net.weg.taskmanager.model.entity.UserChat;

import java.util.Collection;

public class ShortUserDTO {

    private Long id;
    private String name;
    private String password;
    private String email;
    private String imageColor;
    private Collection<UserChat> chats;
    private Collection<Task> createdTasks;
    private Collection<Team> managedTeams;
    private Collection<Team> teams;
}
