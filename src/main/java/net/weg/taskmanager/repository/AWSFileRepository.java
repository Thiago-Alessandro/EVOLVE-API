package net.weg.taskmanager.repository;

import net.weg.taskmanager.model.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AWSFileRepository extends JpaRepository<File, Long> {
}
