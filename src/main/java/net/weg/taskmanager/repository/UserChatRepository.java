package net.weg.taskmanager.repository;

import net.weg.taskmanager.model.entity.UserChat;
import net.weg.taskmanager.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface UserChatRepository extends JpaRepository<UserChat, Long> {

//    Collection<Usuario> findByUsersContaining(Usuario user);

    Collection<UserChat> findUserChatsByUsersContaining(User user);

}
