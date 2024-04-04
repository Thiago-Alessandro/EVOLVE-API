package net.weg.taskmanager.repository;

import net.weg.taskmanager.model.UserHierarchyTeam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserHierarchyTeamRepository extends JpaRepository<UserHierarchyTeam, Long> {
//    boolean existsByUserIdAndTeamIdAndHierarchy_AuthsContaining(Long userId, Long teamId, Auth auth);

//    UserHierarchyTeam findByHierarchy_Auths(Collection<Auth> auths);
//    Hierarchy findByUserIdAndTeamIdAndHierarchy_AuthsContaining(Long userId, Long projectId, Auth auth);
}
