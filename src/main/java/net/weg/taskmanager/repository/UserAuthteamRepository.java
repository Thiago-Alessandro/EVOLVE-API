package net.weg.taskmanager.repository;

import net.weg.taskmanager.model.UserAuthTeam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAuthteamRepository extends JpaRepository<UserAuthTeam, Long> {
}
