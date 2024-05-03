package net.weg.taskmanager.service;

import lombok.AllArgsConstructor;
import net.weg.taskmanager.model.entity.UserTeam;
import net.weg.taskmanager.model.entity.UserTeamId;
import net.weg.taskmanager.repository.UserTeamRepository;
import net.weg.taskmanager.security.model.entity.Role;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserTeamService {

    private final UserTeamRepository repository;


    public Collection<UserTeam> findAllWithTeamId(Long teamId){
        Optional<Collection<UserTeam>> optionalUserTeams = repository.findUserTeamsByTeam_Id(teamId);
        if(optionalUserTeams.isEmpty()) throw new NoSuchElementException();
        return optionalUserTeams.get();
    }
    public UserTeam create(UserTeam userTeam){
        return repository.save(userTeam);
    }

    public Collection<UserTeam> setRoleAndCreateAllIfNotExists(Collection<UserTeam> userTeams, Role role){
        return userTeams.stream().map(userTeam -> setRoleAndCreateIfNotExists(userTeam, role)).toList();
    }

    public UserTeam setRoleAndCreateIfNotExists(UserTeam userTeam, Role role){
        UserTeamId userTeamId = new UserTeamId(userTeam.getUserId(), userTeam.getTeamId());
        return !repository.existsById(userTeamId) ? setRoleAndCreate(userTeam, role) : findById(userTeamId);
    }

    public UserTeam setRoleAndCreate(UserTeam userTeam, Role role){
        userTeam.setRole(role);
        return create(userTeam);
    }

    public boolean existsById(UserTeamId id){
        return repository.existsById(id);
    }

    public void delete(UserTeam userTeam){
        repository.delete(userTeam);
    }

    public Collection<UserTeam> findAllWithUserId(Long userId){
        Optional<Collection<UserTeam>> optionalUserTeams = repository.findUserTeamsByUser_Id(userId);
        if(optionalUserTeams.isEmpty()) throw new NoSuchElementException();
        return optionalUserTeams.get();
    }

    public UserTeam findById(UserTeamId userTeamId){
        Optional<UserTeam> optionalUserTeam = repository.findById(userTeamId);
        if(optionalUserTeam.isEmpty()) throw new NoSuchElementException();
        return optionalUserTeam.get();
    }

}
