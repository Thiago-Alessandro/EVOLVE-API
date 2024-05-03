package net.weg.taskmanager.service;

import lombok.AllArgsConstructor;
import net.weg.taskmanager.model.entity.UserTeam;
import net.weg.taskmanager.model.entity.UserTeamId;
import net.weg.taskmanager.repository.UserTeamRepository;
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

    public Collection<UserTeam> createAllIfNotExists(Collection<UserTeam> userTeams){
        return userTeams.stream().map(this::createIfNotExists).toList();
    }

    public UserTeam createIfNotExists(UserTeam userTeam){
        UserTeamId userTeamId = new UserTeamId(userTeam.getUserId(), userTeam.getTeamId());
        return !repository.existsById(userTeamId) ? create(userTeam) : findById(userTeamId);
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
