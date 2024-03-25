package net.weg.taskmanager.repository;

import net.weg.taskmanager.model.UserAuthTeam;
import net.weg.taskmanager.security.model.enums.Auth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAuthTeamRepository extends JpaRepository<UserAuthTeam, Long> {
    boolean existsByUserIdAndTeamIdAndAuthorizationsContaining(Long userId, Long teamId, Auth auth);
}
