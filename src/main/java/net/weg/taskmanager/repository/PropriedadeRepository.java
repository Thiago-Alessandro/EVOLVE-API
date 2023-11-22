package net.weg.taskmanager.repository;

import net.weg.taskmanager.model.property.Propriedade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PropriedadeRepository  extends JpaRepository<Propriedade, Integer> {
}
