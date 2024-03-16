package net.weg.taskmanager.security.repository;

import net.weg.taskmanager.security.model.entity.UserDetailsEntity;
import net.weg.taskmanager.security.model.enums.Auth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserDetailsEntityRepository extends JpaRepository<UserDetailsEntity, Long> {
    UserDetailsEntity findByUser_Id(Long id);
    Optional<UserDetailsEntity> findByUsername(String name);
     default Collection<Auth> findAllById(Long id){
        UserDetailsEntity userDE = findById(id).get();
       return userDE.getAuthorities();
    }
}