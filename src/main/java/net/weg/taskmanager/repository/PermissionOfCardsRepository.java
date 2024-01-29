package net.weg.taskmanager.repository;

import net.weg.taskmanager.model.PermissionOfCards;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionOfCardsRepository extends JpaRepository<PermissionOfCards, Integer> {
}
