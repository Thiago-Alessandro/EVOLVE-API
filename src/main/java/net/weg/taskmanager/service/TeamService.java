package net.weg.taskmanager.service;

import lombok.AllArgsConstructor;
import net.weg.taskmanager.model.*;
import net.weg.taskmanager.model.dto.get.GetTeamDTO;
import net.weg.taskmanager.model.dto.post.PostTeamDTO;
import net.weg.taskmanager.repository.TeamRepository;
import net.weg.taskmanager.repository.UserTeamRepository;
import net.weg.taskmanager.security.model.entity.Role;
import net.weg.taskmanager.security.model.enums.Permission;
import net.weg.taskmanager.security.service.RoleService;
import net.weg.taskmanager.service.processor.TeamProcessor;
import net.weg.taskmanager.utils.ColorUtils;
import net.weg.taskmanager.utils.FileUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.management.InvalidAttributeValueException;
import java.util.*;

@Service
@AllArgsConstructor
public class TeamService {

    private final TeamRepository teamRepository;

    private final UserTeamService userTeamService;

    public Team findById(Long id) {
        Team team = findTeamById(id);
        return TeamProcessor.getInstance().resolveTeam(team);
    }

    public Collection<Team> findAll() {

        Collection<Team> teams = teamRepository.findAll();
        for (Team team : teams) {
            TeamProcessor.getInstance().resolveTeam(team);
        }
        return teams;
    }

    public void delete(Long id) {
        teamRepository.deleteById(id);
    }

    private final RoleService roleService;
    public Team create(PostTeamDTO teamDTO) {
        Team team =  new Team(teamDTO);
        Team createdTeam = teamRepository.save(team);

        setCreator(teamDTO.getCreator(), createdTeam);
        setDefaultRole(createdTeam);
//        updateTeamChat(createdTeam);
        setPossibleRoles(createdTeam);
        Team teamSaved = teamRepository.save(createdTeam);
        return TeamProcessor.getInstance().resolveTeam(teamSaved);
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
        UserTeam userTeam = new UserTeam(creator.getId(), team.getId(), role);
        userTeam.setManager(true);
        userTeamService.create(userTeam);
    }

    private void setDefaultRole(Team team){
        Role role = roleService.getRoleByName("TEAM_COLABORATOR");
        team.setDefaultRole(role);
    }

    protected Team save(Team team){
        return teamRepository.save(team);
    }

//    public Team update(Team team) {
//        updateTeamChat(team);
//        Team updatedTeam = teamRepository.save(team);
//        return TeamProcessor.getInstance().resolveTeam(updatedTeam);
//    }

    public Team findTeamById(Long teamId){
        Optional<Team> optionalTeam = teamRepository.findById(teamId);
        if(optionalTeam.isEmpty()) throw new NoSuchElementException();
        return optionalTeam.get();
    }

    public GetTeamDTO patchName(Long teamId, String name) throws InvalidAttributeValueException {
        if (name == null) throw new InvalidAttributeValueException();
        Team team = findById(teamId);
        team.setName(name);
        return new GetTeamDTO(teamRepository.save(team));
    }

    public GetTeamDTO patchImage(Long teamId, MultipartFile image) throws InvalidAttributeValueException {
        if (image == null) throw new InvalidAttributeValueException();
        Team team = findById(teamId);
        File file = FileUtils.buildFileFromMultipartFile(image);
        team.setImage(file);
        return new GetTeamDTO(teamRepository.save(team));
    }

    public GetTeamDTO patchImage(Long teamId, File image) throws InvalidAttributeValueException {
        if (image == null) throw new InvalidAttributeValueException();
        Team team = findById(teamId);
        team.setImage(image);
        return new GetTeamDTO(teamRepository.save(team));
    }

    public GetTeamDTO patchImageRemove(Long teamId) {
        Team team = findById(teamId);
        team.setImage(null);
        return new GetTeamDTO(teamRepository.save(team));
    }

    public GetTeamDTO patchImageColor(Long teamId, String imageColor) throws InvalidAttributeValueException {
        if (imageColor == null || !ColorUtils.isHexColorValid(imageColor)) throw new InvalidAttributeValueException();
        Team team = findById(teamId);
        team.setImageColor(imageColor);
        return new GetTeamDTO(teamRepository.save(team));
    }

    public GetTeamDTO patchParticipants(Long teamId, Collection<UserTeam> participants) throws InvalidAttributeValueException {
        Team team = findById(teamId);
        //team must have at least one manager (creator/owner)
        if(participants == null || hasManager(team)) throw new InvalidAttributeValueException();
        team.setParticipants(participants);

        updateTeamChat(team);
        syncUserTeamTable(team);
        return new GetTeamDTO(teamRepository.save(team));
    }

    public GetTeamDTO patchRoles(Long teamId, Collection<Role> roles) throws InvalidAttributeValueException {
        if (roles == null) throw new InvalidAttributeValueException();
        Team team = findById(teamId);
        team.setRoles(roles);
        return new GetTeamDTO(teamRepository.save(team));
    }

    public GetTeamDTO patchDefaultRole(Long teamId, Role DefaultRole) throws InvalidAttributeValueException {
        if (DefaultRole == null) throw new InvalidAttributeValueException();
        Team team = findById(teamId);
        team.setDefaultRole(DefaultRole);
        return new GetTeamDTO(teamRepository.save(team));
    }










    private void updateTeamChat(Team team) {
        if(team.getParticipants() != null){
            ArrayList<User> users = new ArrayList<>(team.getParticipants().stream().map(UserTeam::getUser).toList());
            team.getChat().setUsers(users);
        }
    }

    private void syncUserTeamTable(Team team) throws InvalidAttributeValueException {
        if (team.getParticipants() == null) throw new InvalidAttributeValueException();
        team.getParticipants().stream()
                .filter(this::doesUserTeamTableNotExists)
                .forEach(this::createDefaultUserTeam);
        deleteUserTeamIfUserIsNotParticipant(team);
    }
    private boolean hasManager(Team team){
        return !team.getParticipants().stream().map(UserTeam::isManager).toList().isEmpty();
    }

    private void createDefaultUserTeam(UserTeam userTeam) {
        Role defaultRole = userTeam.getTeam().getDefaultRole();
        userTeam.setRole(defaultRole);
        userTeamService.create(userTeam);
    }
    private boolean doesUserTeamTableNotExists(UserTeam userTeam) {
        return !userTeamService.existsById(new UserTeamId(userTeam.getUserId(), userTeam.getTeamId()));
    }

    private void deleteUserTeamIfUserIsNotParticipant(Team team) {
        Collection<UserTeam> userTeams = userTeamService.findAllWithTeamId(team.getId());
        userTeams.stream()
                .filter(userTeam -> !team.getParticipants().contains(userTeam.getUser()))
                .forEach(userTeamService::delete);
    }
}
