package net.weg.taskmanager.repository;

import net.weg.taskmanager.model.property.Option;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PossibleOptionsRepository extends JpaRepository<Option,Integer> {
}
