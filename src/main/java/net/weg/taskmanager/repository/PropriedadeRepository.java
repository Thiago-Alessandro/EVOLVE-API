package net.weg.taskmanager.repository;

import net.weg.taskmanager.model.Projeto;
import net.weg.taskmanager.model.Propriedade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PropriedadeRepository  extends JpaRepository<Propriedade, Integer> {
}
