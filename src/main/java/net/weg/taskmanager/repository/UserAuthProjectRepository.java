package net.weg.taskmanager.repository;

import net.weg.taskmanager.model.UserAuthProject;
import net.weg.taskmanager.security.model.enums.Auth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAuthProjectRepository extends JpaRepository<UserAuthProject, Long> {
    boolean existsByUserIdAndProjectIdAndAuthorizationsContaining(Long userId, Long projectId, Auth auth);
//    boolean existsByAuthorizations_Name()
//    boolean existsByAuthorizationsContaining_
}