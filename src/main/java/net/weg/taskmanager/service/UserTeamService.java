package net.weg.taskmanager.service;

import lombok.AllArgsConstructor;
import net.weg.taskmanager.model.dto.UserTeamDTO2;
import net.weg.taskmanager.model.entity.UserTeam;
import net.weg.taskmanager.model.entity.UserTeamId;
import net.weg.taskmanager.repository.UserTeamRepository;
import net.weg.taskmanager.security.model.entity.Role;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserTeamService {

    private final UserTeamRepository repository;
    private final ModelMapper mapper;

    public Collection<UserTeamDTO2> findByUserId(Long userId){
        Collection<UserTeam> userTeams = findUserTeamsByUser_Id(userId);
        return userTeams.stream().map(UserTeamDTO2::new).toList();
    }

    public Collection<UserTeam> findUserTeamsByUser_Id(Long userId){
        Optional<Collection<UserTeam>> optionalUserTeams = repository.findUserTeamsByUser_Id(userId);
        if(optionalUserTeams.isEmpty()) throw new NoSuchElementException("Usuario não possui equipes");
        return optionalUserTeams.get();
    }

    public Collection<UserTeam> findAllWithTeamId(Long teamId){
        Optional<Collection<UserTeam>> optionalUserTeams = repository.findUserTeamsByTeam_Id(teamId);
        if(optionalUserTeams.isEmpty()) throw new NoSuchElementException();
        return optionalUserTeams.get();
    }
    public UserTeam create(UserTeam userTeam){
        return repository.save(userTeam);
    }

    public UserTeam save(UserTeam userTeam){
        if(userTeam.getRole()!=null) {
            return repository.save(userTeam);
        }
        return userTeam;
    }

    public Collection<UserTeam> setRoleAndCreateAllIfNotExists(Collection<UserTeam> userTeams, Role role){
        return userTeams.stream().map(userTeam -> setRoleAndCreateIfNotExists(userTeam, role)).toList();
    }

    public UserTeam findByUserIdAndTeamId(Long userId, Long teamId){
        Optional<UserTeam> optionalUserTeam = repository.findByUserIdAndTeamId(userId, teamId);
        if(optionalUserTeam.isEmpty()) throw new NoSuchElementException();
        return optionalUserTeam.get();
    }

    public UserTeam setRoleAndCreateIfNotExists(UserTeam userTeam, Role role){
        try{
            findByUserIdAndTeamId(userTeam.getUserId(), userTeam.getTeamId());
            return save(userTeam);
        } catch (Exception e){
            return setRoleAndCreate(userTeam, role);
        }
//        return !repository.existsById(userTeamId) ?  :);
//        UserTeamId userTeamId = new UserTeamId(userTeam.getUserId(), userTeam.getTeamId());
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
