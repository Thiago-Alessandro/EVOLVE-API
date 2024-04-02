package net.weg.taskmanager.repository;

import net.weg.taskmanager.model.AWSFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AWSFileRepository extends JpaRepository<AWSFile, Long> {
}
