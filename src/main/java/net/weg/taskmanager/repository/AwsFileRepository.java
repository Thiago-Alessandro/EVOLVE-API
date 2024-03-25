package net.weg.taskmanager.repository;

import net.weg.taskmanager.model.entity.AwsFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AwsFileRepository extends JpaRepository<AwsFile, Long> {
}