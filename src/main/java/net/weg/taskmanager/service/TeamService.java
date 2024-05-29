package net.weg.taskmanager.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import net.weg.taskmanager.model.dto.UserTeamDTO;
import net.weg.taskmanager.model.dto.converter.Converter;
import net.weg.taskmanager.model.dto.converter.get.GetTeamConverter;
import net.weg.taskmanager.model.dto.get.GetTeamNotificationDTO;
import net.weg.taskmanager.model.entity.User;

import net.weg.taskmanager.model.dto.UserTeamDTO2;
import net.weg.taskmanager.model.dto.get.GetTeamDTO;
import net.weg.taskmanager.model.dto.post.PostTeamDTO;
import net.weg.taskmanager.model.entity.*;
import net.weg.taskmanager.repository.*;
import net.weg.taskmanager.security.model.entity.Role;
import net.weg.taskmanager.security.repository.RoleRepository;
import net.weg.taskmanager.security.service.RoleService;

import net.weg.taskmanager.utils.ColorUtils;
import net.weg.taskmanager.utils.FileUtils;
import org.springframework.beans.BeanUtils;
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
        Team team = teamRepository.findById(id).get();
        return converter.convertOne(team);
    }

    public Collection<GetTeamNotificationDTO> getAllNotifications(Long teamId) {
        Collection<TeamNotification> teamNotifications = teamNotificationRepository.findAllByTeam_Id(teamId);
        return teamNotifications != null ? teamNotifications.stream().map(GetTeamNotificationDTO::new).toList() : new ArrayList<>();
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

    public Team createTeam(PostTeamDTO teamDTO) { //creates a team automaticatlly
        Team team =  new Team(teamDTO);
        Team createdTeam = teamRepository.save(team);
        setCreator(teamDTO.getCreator(), createdTeam);
        setDefaultRole(createdTeam);

//        updateTeamChat(createdTeam);
        createdTeam = findTeamById(createdTeam.getId());
        TeamChat teamChat = new TeamChat(createdTeam);
        teamChat.setUsers(new ArrayList<>(createdTeam.getParticipants().stream().map(UserTeam::getUser).toList()));
        createdTeam.setChat(teamChat);

        setPossibleRoles(createdTeam);
        UUID code = UUID.randomUUID();
        createdTeam.setCode(code.toString());
        return teamRepository.save(createdTeam);
    }

//    public GetTeamDTO create(PostTeamDTO teamDTO){
//        return converter.convertOne(createTeam(teamDTO));
//    }
    public GetTeamDTO create(Team team){
        return new GetTeamDTO(create2(team));
    }

    public Team create2(Team team){
        setDefaultRole(team);
//        updateTeamChat(createdTeam);
//        setPossibleRoles(team);

        Team team1 = teamRepository.save(team);
        team1.getParticipants().forEach(userTeam -> {
            userTeam.setRole(roleService.findRoleById(userTeam.getRole().getId()));
            userTeam.setTeamId(team1.getId());
            userTeam.setTeam(team1);
            userTeamService.save(userTeam);
        });

        TeamChat teamChat = new TeamChat(team1);
        teamChat.setUsers(new ArrayList<>(team1.getParticipants().stream().map(UserTeam::getUser).toList()));
        team1.setChat(teamChat);

        Team team2 = teamRepository.save(team1);

        return findTeamById(team2 .getId());
    }


    private void setPossibleRoles(Team team){
        Role teamCreator = roleService.getRoleByName("TEAM_CREATOR");
        Role teamAdm = roleService.getRoleByName("TEAM_ADM");
        Role teamColaborator = roleService.getRoleByName("TEAM_COLABORATOR");
        Role teamViewer = roleService.getRoleByName("TEAM_VIEWER");
        team.setRoles(List.of(teamCreator, teamAdm, teamColaborator, teamViewer));
    }

    private void setCreator(User creator, Team team){
        Role role = roleService.getRoleByName("TEAM_CREATOR");
        UserTeam userTeam = new UserTeam(creator.getId(), team.getId(), creator, team, role);
        userTeam.setManager(true);
        team.setParticipants(new ArrayList<>(List.of(userTeam)));
        userTeamService.create(userTeam);
    }

    private void setDefaultRole(Team team){
        Role role = roleService.getRoleByName("TEAM_COLABORATOR");
        team.setDefaultRole(role);
    }

    protected Team save(Team team) {
        return teamRepository.save(team);
    }

    private final RoleRepository roleRepository;

    public void delete(Long id){
        Team team = findTeamById(id);

        userTeamRepository.deleteAll(team.getParticipants());
        teamRepository.delete(team);
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

    public GetTeamDTO patchParticipantsLeaveTeam(Long teamId,Long userId){
        UserTeam userTeam = userTeamRepository.findByUserIdAndTeamId(userId, teamId).get();
        userTeamRepository.delete(userTeam);
        return converter.convertOne(teamRepository.findById(teamId).get());
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



//    private Team newTeam(User admin){
//        Team team = new Team();
//        team.setAdministrator(admin);
//        team.setParticipants(List.of(team.getAdministrator()));
//        team.setName("Nova Equipe");
//        team.setImageColor( ColorUtils.generateHexColor());
//        return team;
//    }


    public Collection<UserTeamDTO2> findTeamsByUserId(Long userId){
        System.out.println(userTeamService.findByUserId(userId).stream().findFirst().get().getTeam());
        System.out.println("findTeamsByUserId");
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


    private final UserTeamRepository userTeamRepository;

    public GetTeamDTO patchParticipants(Long teamId, Collection<UserTeamDTO> participantsDTO) throws InvalidAttributeValueException {
        Team team = findTeamById(teamId);
        if(participantsDTO == null ) throw new InvalidAttributeValueException("Participants in Team can not be null");

        Collection<UserTeam> participants = new ArrayList<>();
        participantsDTO.forEach(participant -> {
            UserTeam userTeam = new UserTeam();
            BeanUtils.copyProperties(participant, userTeam);
            userTeam.setUser(userRepository.findById(participant.getUserId()).get());
            participants.add(userTeam);
        });

        participants.forEach(userTeam -> userTeam.setRole(roleService.findRoleById(userTeam.getRole().getId())));
        participants.forEach(userTeam -> userTeam.setTeam(findTeamById(userTeam.getTeamId())));

        if(!hasManager(participants)) throw new InvalidAttributeValueException();
        team.setParticipants(userTeamRepository.saveAll(participants));
        team = teamRepository.save(team);
        updateTeamChat(team);


        return new GetTeamDTO(teamRepository.save(team));
    }

    public void updateTeamChat(Team team){
        HashSet<User> members = new HashSet<>();
        team.getParticipants().forEach(userProject -> members.add(userProject.getUser()));
        team.setChat(teamChatService.patchUsers(team.getChat().getId(), members));
    }

    private final TeamChatService teamChatService;

   public GetTeamDTO patchParticipantsByCode(Long teamId, Long userId ){
       Team team = findTeamById(teamId);
       User user = userRepository.findById(userId).get();
       UserTeam userTeam1 = new UserTeam();
       userTeam1.setTeamId(teamId);
       userTeam1.setTeam(team);
       userTeam1.setUserId(userId);
       userTeam1.setUser(user);
       userTeam1.setRole(roleService.getRoleByName("TEAM_COLABORATOR"));
       userTeamService.create(userTeam1);
       team.getParticipants().add(userTeam1);
       return  new GetTeamDTO(teamRepository.save(team));
   }

//    private void updateTeamChat(Long teamId) {
//        Team team = findTeamById(teamId);
//        if(team.getParticipants() == null) throw new NoSuchElementException();
//        ArrayList<User> users = new ArrayList<>(team.getParticipants().stream().map(UserTeam::getUser).toList());
//        team.getChat().setUsers(users);
//        team.getParticipants().forEach(userTeam -> System.out.println(userTeam.getUser()));
//        teamRepository.save(team);
//    }

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
    private boolean hasManager(Collection<UserTeam> userTeams){
        return !userTeams.stream().map(UserTeam::getManager).toList().isEmpty();
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

    public   GetTeamDTO findByCode(String code){
       return new GetTeamDTO(
               teamRepository.findTeamByCode(code)
       );
    }

}
