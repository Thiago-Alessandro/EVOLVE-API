package net.weg.taskmanager.service;

import lombok.AllArgsConstructor;
import net.weg.taskmanager.model.entity.TeamNotification;
import net.weg.taskmanager.model.entity.User;

import net.weg.taskmanager.model.entity.Team;
import net.weg.taskmanager.model.dto.get.GetTeamDTO;
import net.weg.taskmanager.repository.TeamNotificationRepository;
import net.weg.taskmanager.repository.TeamRepository;
import net.weg.taskmanager.repository.UserRepository;
import net.weg.taskmanager.service.processor.TeamProcessor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;

@Service
@AllArgsConstructor
public class TeamService {

    private final TeamRepository teamRepository;
    private final UserRepository userRepository;
    private final TeamNotificationRepository teamNotificationRepository;
    private final TeamProcessor teamProcessor = TeamProcessor.getInstance();

    public GetTeamDTO findById(Long id){
        Team team = teamRepository.findById(id).get();
//        teamDTO.image()
        return resolveAndGetDTO(team);
    }

    public Collection<GetTeamDTO> findAll(){
        Collection<Team> teams = teamRepository.findAll();
        return resolveAndGetDTOs(teams);
    }

    public void delete(Long id){
        teamRepository.deleteById(id);
    }

    public GetTeamDTO create(Team team){
        updateTeamChat(team);
        Team createdTeam = teamRepository.save(team);
        return resolveAndGetDTO(createdTeam);}

    public GetTeamDTO update(Team team){
        updateTeamChat(team);
        Team updatedTeam = teamRepository.save(team);
        return resolveAndGetDTO(updatedTeam);
    }

    public Collection<Team> findTeamsByUserId(Long id){
        User user = userRepository.findById(id).get();
        Collection<Team> teams = teamRepository.findTeamsByParticipantsContaining(user);
        teams.forEach(team -> TeamProcessor.getInstance().resolveTeam(team));
        return teams;
    }

    public GetTeamDTO patchTeamName(Long teamId, String name){
        Team team = teamRepository.findById(teamId).get();
        team.setName(name);
        Team savedTeam = teamRepository.save(team);
        return new GetTeamDTO(savedTeam);
    }

    private void updateTeamChat(Team team){
        team.getChat().setUsers(team.getParticipants());
    }

    private GetTeamDTO resolveAndGetDTO(Team team){
        Team resolvedTeam = teamProcessor.resolveTeam(team);
        return new GetTeamDTO(resolvedTeam);
    }
    private Collection<GetTeamDTO> resolveAndGetDTOs(Collection<Team> teams){
        return teams.stream().map(this::resolveAndGetDTO).toList();
    }

    public GetTeamDTO patchReadedNotification(Long teamId, Long notificationId) {
        Team team = this.teamRepository.findById(teamId).get();

        team.getNotifications().forEach(notificationFor -> {
            if(Objects.equals(notificationFor.getId(), notificationId)) {
                notificationFor.setReaded(true);
                this.teamNotificationRepository.save(notificationFor);
            }
        });
        Team teamSaved = this.teamRepository.save(team);

        return new GetTeamDTO(teamSaved);
    }

}
