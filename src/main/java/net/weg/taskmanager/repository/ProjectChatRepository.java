package net.weg.taskmanager.repository;

import net.weg.taskmanager.model.Project;
import net.weg.taskmanager.model.ProjectChat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface ProjectChatRepository extends JpaRepository<ProjectChat, Long> {

    ProjectChat findProjectChatByProject_Id(Long id);

}
