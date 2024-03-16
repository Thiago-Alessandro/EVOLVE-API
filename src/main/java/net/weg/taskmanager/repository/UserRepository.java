package net.weg.taskmanager.repository;

import net.weg.taskmanager.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);
//    Optional<User> findByUserDetailsEntity_Username(String nome);

}
