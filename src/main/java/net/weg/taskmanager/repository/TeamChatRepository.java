package net.weg.taskmanager.repository;

import net.weg.taskmanager.model.TeamChat;
import net.weg.taskmanager.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@Repository
public interface TeamChatRepository extends JpaRepository<TeamChat, Integer> {

    TeamChat findTeamChatByTeam_Id(Integer id);

}
