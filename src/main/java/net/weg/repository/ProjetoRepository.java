package net.weg.repository;

import net.weg.model.Projeto;
import net.weg.model.Tarefa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface ProjetoRepository extends JpaRepository<Projeto, Integer> {

}