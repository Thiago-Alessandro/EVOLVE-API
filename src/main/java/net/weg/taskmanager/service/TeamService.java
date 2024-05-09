package net.weg.taskmanager.service;

import lombok.AllArgsConstructor;
import net.weg.taskmanager.model.dto.converter.Converter;
import net.weg.taskmanager.model.dto.converter.get.GetTeamConverter;
import net.weg.taskmanager.model.entity.TeamNotification;
import net.weg.taskmanager.model.entity.User;

import net.weg.taskmanager.model.entity.Team;
import net.weg.taskmanager.model.dto.get.GetTeamDTO;
import net.weg.taskmanager.repository.TeamNotificationRepository;
import net.weg.taskmanager.repository.TeamRepository;
import net.weg.taskmanager.repository.UserRepository;
import net.weg.taskmanager.service.processor.TeamProcessor;
import net.weg.taskmanager.utils.ColorUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.management.InvalidAttributeValueException;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import java.util.HashSet;
import java.util.Objects;

@Service
@AllArgsConstructor
public class TeamService {

    private final TeamRepository teamRepository;
    private final UserRepository userRepository;
    private final TeamNotificationRepository teamNotificationRepository;

    public GetTeamDTO findById(Long id){
        Team team = findTeamById(id);
        return new GetTeamDTO(team);
    }

    public Team findTeamById(Long id){
        Optional<Team> optionalTeam = teamRepository.findById(id);
        if(optionalTeam.isPresent()){
            return optionalTeam.get();
        }
        throw new NoSuchElementException("Could not find team with id: " + id);
    }

    public Collection<GetTeamDTO> findAll(){
        Collection<Team> teams = teamRepository.findAll();
        return teams.stream().map(GetTeamDTO::new).toList();
    }

    public void delete(Long id){
        teamRepository.deleteById(id);
    }

    public GetTeamDTO create(Long adminId){
        User admin = userRepository.findById(adminId).get();
        Team team = newTeam(admin);
        updateTeamChat(team);
        return saveAndGetDTO(team);}

    private Team newTeam(User admin){
        Team team = new Team();
        team.setAdministrator(admin);
        team.setParticipants(List.of(team.getAdministrator()));
        team.setName("Nova Equipe");
        team.setImageColor( ColorUtils.generateHexColor());
        return team;
    }

    public GetTeamDTO update(Team team){
        updateTeamChat(team);
        return saveAndGetDTO(team);
    }

    private final Converter<GetTeamDTO, Team> converter = new GetTeamConverter();

    public Collection<GetTeamDTO> findTeamsByUserId(Long id){
        User user = userRepository.findById(id).get();
        Collection<Team> teams = teamRepository.findTeamsByParticipantsContaining(user);
        return converter.convertAll(teams);
    }



    public GetTeamDTO patchTeamName(Long teamId, String name) throws InvalidAttributeValueException {
        if(name!=null){
            Team team = findTeamById(teamId);
            team.setName(name);
            return saveAndGetDTO(team);
        }
        throw new InvalidAttributeValueException("Name on Team can not be null");
    }

    public GetTeamDTO patchParticipants(Long teamId, Collection<User> participants) throws InvalidAttributeValueException {
        if(participants!=null){
            Team team = findTeamById(teamId);
            team.setParticipants(participants);
            return saveAndGetDTO(team);
        }
        throw new InvalidAttributeValueException("Participants in Team can not be null");
    }

    public GetTeamDTO patchImageColor(Long teamId,String imageColor) throws InvalidAttributeValueException {
        if(imageColor!=null){
            Team team = findTeamById(teamId);
            team.setImageColor(imageColor);
            return saveAndGetDTO(team);
        }
        throw new InvalidAttributeValueException("ImageColor in Team can not be null");
    }

    public GetTeamDTO patchImage(Long teamId, MultipartFile image) throws InvalidAttributeValueException {
        if(image != null){
            Team team = findTeamById(teamId);
            team.setImage(image);
            return saveAndGetDTO(team);
        }
        throw new InvalidAttributeValueException("Image on Team can not be null");
    }


    private void updateTeamChat(Team team){
        team.getChat().setUsers(team.getParticipants());
    }

    private GetTeamDTO saveAndGetDTO(Team team){
        Team savedTeam = teamRepository.save(team);
        return new GetTeamDTO(savedTeam);
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

    public void cleanAllUserNotifications(Long loggedUserId) {
        User loggedUser = this.userRepository.findById(loggedUserId).get();
        loggedUser.getTeams().forEach(team -> {
            team.getNotifications().forEach(notification -> {
                notification.getNotificatedUsers().remove(loggedUser);
            });
            this.teamRepository.save(team);
        });
    }

}
