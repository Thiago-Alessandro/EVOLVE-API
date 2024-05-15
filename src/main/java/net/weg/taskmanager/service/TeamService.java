package net.weg.taskmanager.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import net.weg.taskmanager.model.dto.converter.Converter;
import net.weg.taskmanager.model.dto.converter.get.GetTeamConverter;
import net.weg.taskmanager.model.entity.User;

import net.weg.taskmanager.model.dto.UserTeamDTO2;
import net.weg.taskmanager.model.dto.get.GetTeamDTO;
import net.weg.taskmanager.model.dto.post.PostTeamDTO;
import net.weg.taskmanager.model.entity.*;
import net.weg.taskmanager.repository.TeamNotificationRepository;
import net.weg.taskmanager.repository.TeamRepository;
import net.weg.taskmanager.security.model.entity.Role;
import net.weg.taskmanager.security.service.RoleService;

import net.weg.taskmanager.repository.UserRepository;
import net.weg.taskmanager.utils.ColorUtils;
import net.weg.taskmanager.utils.FileUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.management.InvalidAttributeValueException;
import java.util.*;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import java.util.Objects;

@Service
@AllArgsConstructor
public class TeamService {

    private final TeamRepository teamRepository;

    private final UserTeamService userTeamService;
    private final RoleService roleService;

    private final UserRepository userRepository;
    private final TeamNotificationRepository teamNotificationRepository;

    private final Converter<GetTeamDTO, Team> converter = new GetTeamConverter();

    public GetTeamDTO findById(Long id){
        Team team = findTeamById(id);
        return converter.convertOne(team);
    }

    public Team findTeamById(Long teamId){
        Optional<Team> optionalTeam = teamRepository.findById(teamId);
        if(optionalTeam.isEmpty()) throw new NoSuchElementException("Could not find team with id: " + teamId);
        return optionalTeam.get();
    }

//    public Collection<GetTeamDTO> findAll(){
//        Collection<Team> teams = teamRepository.findAll();
//        return converter.convertAll(teams);
//    }

    public Team createTeam(PostTeamDTO teamDTO) {
        Team team =  new Team(teamDTO);
        Team createdTeam = teamRepository.save(team);

        setCreator(teamDTO.getCreator(), createdTeam);
        setDefaultRole(createdTeam);
//        updateTeamChat(createdTeam);
        setPossibleRoles(createdTeam);
        return teamRepository.save(createdTeam);
    }

    public GetTeamDTO create(PostTeamDTO teamDTO){
        return converter.convertOne(createTeam(teamDTO));
    }


    private void setPossibleRoles(Team team){
        Role teamCreator = roleService.getRoleByName("TEAM_CREATOR");
        Role teamAdm = roleService.getRoleByName("TEAM_ADM");
        Role teamColaborator = roleService.getRoleByName("TEAM_COLABORATOR");
        Role teamViewer = roleService.getRoleByName("TEAM_VIEWER");
        team.setRoles(new ArrayList<>(List.of(teamCreator, teamAdm, teamColaborator, teamViewer)));
    }

    private void setCreator(User creator, Team team){
        Role role = roleService.getRoleByName("TEAM_CREATOR");
        UserTeam userTeam = new UserTeam(creator.getId(), team.getId(), creator, team, role);
        userTeam.setManager(true);
        userTeamService.create(userTeam);
    }

    private void setDefaultRole(Team team){
        Role role = roleService.getRoleByName("TEAM_COLABORATOR");
        team.setDefaultRole(role);
    }

    protected Team save(Team team) {
        return teamRepository.save(team);
    }

    public void delete(Long id){
        teamRepository.deleteById(id);
    }



//    private Collection<Team> findTeamsByParticipants_User_Id(Long userId){
//        return userTeamService.findAllWithUserId(userId).stream().map(UserTeam::getTeam).toList();
//    }


    public GetTeamDTO patchName(Long teamId, String name) throws InvalidAttributeValueException {
        if (name == null) throw new InvalidAttributeValueException();
        Team team = findTeamById(teamId);
        team.setName(name);
        return new GetTeamDTO(teamRepository.save(team));
    }

    public GetTeamDTO patchImage(Long teamId, MultipartFile image) throws InvalidAttributeValueException {
        if (image == null) throw new InvalidAttributeValueException("Image on Team can not be null");
        Team team = findTeamById(teamId);
        File file = FileUtils.buildFileFromMultipartFile(image);
        team.setImage(file);
        return new GetTeamDTO(teamRepository.save(team));
    }

    public GetTeamDTO patchImageRemove(Long teamId) {
        Team team = findTeamById(teamId);
        team.setImage(null);
        return new GetTeamDTO(teamRepository.save(team));
    }

    public GetTeamDTO patchImageColor(Long teamId, String imageColor) throws InvalidAttributeValueException {
        if (imageColor == null || !ColorUtils.isHexColorValid(imageColor)) throw new InvalidAttributeValueException("ImageColor in Team can not be null");
        Team team = findTeamById(teamId);
        team.setImageColor(imageColor);
        return new GetTeamDTO(teamRepository.save(team));
    }

    public GetTeamDTO create(Team team){
        teamRepository.save(team);
        return saveAndGetDTO(team);}

//    private Team newTeam(User admin){
//        Team team = new Team();
//        team.setAdministrator(admin);
//        team.setParticipants(List.of(team.getAdministrator()));
//        team.setName("Nova Equipe");
//        team.setImageColor( ColorUtils.generateHexColor());
//        return team;
//    }


    public Collection<UserTeamDTO2> findTeamsByUserId(Long userId){
        return userTeamService.findByUserId(userId);
    }


    public GetTeamDTO patchTeamName(Long teamId, String name) throws InvalidAttributeValueException {
        if(name!=null){
            Team team = findTeamById(teamId);
            team.setName(name);
            return saveAndGetDTO(team);
        }
        throw new InvalidAttributeValueException("Name on Team can not be null");
    }


    @Transactional
    public GetTeamDTO patchParticipants(Long teamId, Collection<UserTeam> participants) throws InvalidAttributeValueException {
        Team team = findTeamById(teamId);
        if(participants == null ) throw new InvalidAttributeValueException("Participants in Team can not be null");
        Collection<UserTeam> createdParticipants = userTeamService.setRoleAndCreateAllIfNotExists(participants, team.getDefaultRole());
        deleteUserTeamIfUserIsNotParticipant(team);
        createdParticipants.forEach(userTeam -> userTeam.setUser(userRepository.findById(userTeam.getUserId()).get()));
        //team must have at least one manager (creator/owner)
        team.setParticipants(new ArrayList<>(createdParticipants.stream().map(userTeamService::save).toList()));
        if(!hasManager(team)) throw new InvalidAttributeValueException();
        updateTeamChat(team);
        return new GetTeamDTO(teamRepository.save(team));
    }

    private void updateTeamChat(Team team) {
        if(team.getParticipants() == null) return;
        ArrayList<User> users = new ArrayList<>(team.getParticipants().stream().map(UserTeam::getUser).toList());
        team.getChat().setUsers(users);
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
        User loggedUser = userRepository.findById(loggedUserId).get();
        loggedUser.getTeamRoles().stream()
            .map(UserTeam::getTeam)
                .forEach(team -> {
                team.getNotifications().forEach(notification -> {
                    notification.getNotificatedUsers().remove(loggedUser);
                });
            this.teamRepository.save(team);
        });
    }



    public GetTeamDTO patchRoles(Long teamId, Collection<Role> roles) throws InvalidAttributeValueException {
        if (roles == null) throw new InvalidAttributeValueException();
        Team team = findTeamById(teamId);
        team.setRoles(roles);
        return new GetTeamDTO(teamRepository.save(team));
    }

    public GetTeamDTO patchDefaultRole(Long teamId, Role DefaultRole) throws InvalidAttributeValueException {
        if (DefaultRole == null) throw new InvalidAttributeValueException();
        Team team = findTeamById(teamId);
        team.setDefaultRole(DefaultRole);
        return new GetTeamDTO(teamRepository.save(team));
    }

//    private void syncUserTeamTable(Team team) throws InvalidAttributeValueException {
//        if (team.getParticipants() == null) throw new InvalidAttributeValueException();
//        team.getParticipants().stream()
//                .filter(this::doesUserTeamTableNotExists)
//                .forEach(this::createDefaultUserTeam);
//        deleteUserTeamIfUserIsNotParticipant(team);
//    }
    private boolean hasManager(Team team){
        return !team.getParticipants().stream().map(UserTeam::isManager).toList().isEmpty();
    }

//    private void createDefaultUserTeam(UserTeam userTeam) {
//        Team team = findTeamById(userTeam.getTeamId());
//        Role defaultRole = team.getDefaultRole();
//        userTeam.setRole(defaultRole);
//        userTeamService.create(userTeam);
//    }
    private boolean doesUserTeamTableNotExists(UserTeam userTeam) {
        return !userTeamService.existsById(new UserTeamId(userTeam.getUserId(), userTeam.getTeamId()));
    }

    private void deleteUserTeamIfUserIsNotParticipant(Team team) {
        Collection<UserTeam> userTeams = userTeamService.findAllWithTeamId(team.getId());
        userTeams.stream()
                .filter(userTeam -> team.getParticipants().stream()
                        .noneMatch(participant -> participant.getUserId().equals(userTeam.getUserId())))
                .forEach(userTeamService::delete);
    }

//    private GetTeamDTO resolveAndGetDTO(Team team){
//        Team resolvedTeam = teamProcessor.resolveTeam(team);
//        return new GetTeamDTO(resolvedTeam);
//    }
//    private Collection<GetTeamDTO> resolveAndGetDTOs(Collection<Team> teams){
//        return teams.stream().map(this::resolveAndGetDTO).toList();
//    }

}
