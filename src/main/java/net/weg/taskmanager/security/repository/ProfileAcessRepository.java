package net.weg.taskmanager.security.repository;

import net.weg.taskmanager.security.model.entity.ProfileAcess;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileAcessRepository extends JpaRepository<ProfileAcess, Long> {
    ProfileAcess findByProject_IdAndName(Long projectId, String name);
}
