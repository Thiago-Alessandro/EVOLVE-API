package net.weg.taskmanager.repository;

import net.weg.taskmanager.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);
    boolean existsByEmail(String email);
    boolean existsById(Long userId);
    Optional<User> findByUserDetailsEntity_Id(Long userId);
    Optional<User> findByUserDetailsEntity_Username(String username);

}
