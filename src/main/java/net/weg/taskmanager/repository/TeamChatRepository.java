package net.weg.taskmanager.repository;

import net.weg.taskmanager.model.entity.TeamChat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamChatRepository extends JpaRepository<TeamChat, Long> {

    TeamChat findTeamChatByTeam_Id(Long id);

}
