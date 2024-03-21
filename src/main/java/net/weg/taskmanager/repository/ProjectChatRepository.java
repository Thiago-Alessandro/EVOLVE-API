package net.weg.taskmanager.repository;

import net.weg.taskmanager.model.entity.ProjectChat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectChatRepository extends JpaRepository<ProjectChat, Long> {

    ProjectChat findProjectChatByProject_Id(Long id);

}
