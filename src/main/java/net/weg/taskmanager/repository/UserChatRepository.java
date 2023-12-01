package net.weg.taskmanager.repository;

import net.weg.taskmanager.model.Chat;
import net.weg.taskmanager.model.UserChat;
import net.weg.taskmanager.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface UserChatRepository extends JpaRepository<UserChat, Integer> {

//    Collection<Usuario> findByUsersContaining(Usuario user);

    Collection<UserChat> findUserChatsByUsersContaining(Usuario user);

}
