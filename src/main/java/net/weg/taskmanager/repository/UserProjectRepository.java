package net.weg.taskmanager.repository;

import net.weg.taskmanager.model.UserProject;
import net.weg.taskmanager.model.UserProjectId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface UserProjectRepository extends JpaRepository<UserProject, UserProjectId> {
//    boolean existsByUserIdAndProjectIdAndHierarchy_AuthsContaining(Long userId, Long projectId, Auth auth);
//    boolean existsByAuthorizations_Name()
//    boolean existsByAuthorizationsContaining_

    //Hierarchy findByUserIdAndProjectIdAndHierarchy_AuthsContaining(Long userId, Long projectId, Auth auth);

    Optional<Collection<UserProject>> findUserProjectsByProject_Id(Long projectId);
    Optional<Collection<UserProject>> findUserProjectsByUser_Id(Long userId);

    UserProject findByUserIdAndProjectId(Long userId, Long projectId);
    Optional<UserProject> findUserProjectByProject_IdAndManagerIsTrue(Long projectId);

}