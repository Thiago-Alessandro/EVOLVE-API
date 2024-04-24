package net.weg.taskmanager.repository;

import net.weg.taskmanager.model.UserProject;
import net.weg.taskmanager.model.UserProjectId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserProjectRepository extends JpaRepository<UserProject, UserProjectId> {
//    boolean existsByUserIdAndProjectIdAndHierarchy_AuthsContaining(Long userId, Long projectId, Auth auth);
//    boolean existsByAuthorizations_Name()
//    boolean existsByAuthorizationsContaining_

    //Hierarchy findByUserIdAndProjectIdAndHierarchy_AuthsContaining(Long userId, Long projectId, Auth auth);
    UserProject findByUserIdAndProjectId(Long userId, Long projectId);
}