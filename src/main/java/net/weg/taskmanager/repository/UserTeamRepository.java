package net.weg.taskmanager.repository;

import net.weg.taskmanager.model.UserTeam;
import net.weg.taskmanager.model.UserTeamId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.jmx.export.UnableToRegisterMBeanException;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface UserTeamRepository extends JpaRepository<UserTeam, UserTeamId> {
//    UserTeam findByUserIdAndTeamId(Long userId, Long teamId);

    Optional<Collection<UserTeam>> findUserTeamsByTeam_Id(Long teamId);
}
