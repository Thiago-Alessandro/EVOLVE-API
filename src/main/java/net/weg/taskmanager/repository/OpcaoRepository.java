package net.weg.taskmanager.repository;

import net.weg.taskmanager.model.property.Text;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OpcaoRepository extends JpaRepository<Text, Integer> {
}
