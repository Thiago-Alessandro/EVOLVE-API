package net.weg.taskmanager.repository;

import net.weg.taskmanager.model.PermissaoDeCards;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissaoDeCardsRepository extends JpaRepository<PermissaoDeCards, Integer> {
}
