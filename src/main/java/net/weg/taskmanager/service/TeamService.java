package net.weg.taskmanager.service;

import lombok.AllArgsConstructor;
import net.weg.taskmanager.model.*;
import net.weg.taskmanager.repository.TeamRepository;
import net.weg.taskmanager.repository.UserTeamRepository;
import net.weg.taskmanager.service.processor.TeamProcessor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Objects;

@Service
@AllArgsConstructor
public class TeamService {

    private final TeamRepository teamRepository;
    private final UserTeamRepository userTeamRepository;

    public Team findById(Long id) {
        Team team = teamRepository.findById(id).get();
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

    public Team create(Team team) {
        updateTeamChat(team);
        Team createdTeam = teamRepository.save(team);
        return TeamProcessor.getInstance().resolveTeam(createdTeam);
    }

    public Team update(Team team) {
        updateTeamChat(team);
        Team updatedTeam = teamRepository.save(team);
        return TeamProcessor.getInstance().resolveTeam(updatedTeam);
    }

    private void updateTeamChat(Team team) {
        team.getChat().setUsers(team.getParticipants());
    }

    private void syncUserProjectTable(Team team) {
        if (team.getParticipants() != null) {

            team.getParticipants().stream()
                    .filter(member -> doesUserTeamTableExists(member, team))
                    .forEach(member -> userTeamRepository.save(createDefaultUserTeam(member, team)));

            deleteUserProjectIfUserIsNotAssociate(team);
        }
    }

    private UserTeam createDefaultUserTeam(User member, Team team) {
        return new UserTeam(member.getId(), team.getId(), team.getDefaultProfileAcess());
    }

    private boolean doesUserTeamTableExists(User member, Team team) {
        return !userTeamRepository.existsById(new UserTeamId(member.getId(), team.getId()));
    }

    private void deleteUserProjectIfUserIsNotAssociate(Team team) {
        userTeamRepository.findAll().stream()
                .filter(userTeam -> Objects.equals(userTeam.getTeamId(), team.getId()))
                .filter(userTeam -> !team.getParticipants().contains(userTeam.getUser()))
                .forEach(userTeam -> userTeamRepository.delete(userTeam));
    }
}
