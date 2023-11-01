package net.weg.taskmanager.repository;

import net.weg.taskmanager.model.Projeto;
import net.weg.taskmanager.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository  extends JpaRepository<Usuario, Integer> {
}
