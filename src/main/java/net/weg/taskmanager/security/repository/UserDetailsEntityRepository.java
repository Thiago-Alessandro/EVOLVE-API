package net.weg.taskmanager.security.repository;

import net.weg.taskmanager.security.model.entity.UserDetailsEntity;
import net.weg.taskmanager.security.model.enums.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
//import java.util.List;
import java.util.Optional;

@Repository
public interface UserDetailsEntityRepository extends JpaRepository<UserDetailsEntity, Long> {
    UserDetailsEntity findByUser_Id(Long id);

    Optional<UserDetailsEntity> findByUsername(String name);

    Optional<UserDetailsEntity> findByUser_IdAndUser_Teams_Id(Long userId,Long teamId);

    boolean existsByUser_Id_AndUser_Teams_Id(Long userId, Long teamId);
    boolean existsByUser_IdAndUser_Email(Long userId, String email);
    boolean existsByUser_Id(Long userId);
}