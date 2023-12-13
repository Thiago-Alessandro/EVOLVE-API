package net.weg.repository;

import net.weg.model.UserChat;
import net.weg.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface UserChatRepository extends JpaRepository<UserChat, Integer> {

//    Collection<Usuario> findByUsersContaining(Usuario user);

    Collection<UserChat> findUserChatsByUsersContaining(Usuario user);

}
