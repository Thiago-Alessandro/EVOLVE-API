package net.weg.taskmanager.repository;

import net.weg.taskmanager.model.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Long> {

    Collection<Comment> findAllByProject_Id(Long id);
}
