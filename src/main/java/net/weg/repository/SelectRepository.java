package net.weg.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SelectRepository extends JpaRepository<SelectProperty, Integer> {
}
