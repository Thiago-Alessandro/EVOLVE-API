package net.weg.repository;

import net.weg.model.property.SelectOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OpcaoRepository extends JpaRepository<SelectOption, Integer> {
}