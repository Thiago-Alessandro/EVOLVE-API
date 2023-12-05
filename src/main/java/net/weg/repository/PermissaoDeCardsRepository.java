package net.weg.repository;

import net.weg.model.PermissaoDeCards;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissaoDeCardsRepository extends JpaRepository<PermissaoDeCards, Integer> {
}