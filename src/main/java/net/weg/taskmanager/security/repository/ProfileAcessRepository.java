package net.weg.taskmanager.security.repository;

import net.weg.taskmanager.security.model.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileAcessRepository extends JpaRepository<Role, Long> {

    Role findByName(String name);
}
