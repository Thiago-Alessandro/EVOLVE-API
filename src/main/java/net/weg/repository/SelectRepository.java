package net.weg.repository;

import net.weg.model.property.ValueMultiselect;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SelectRepository extends JpaRepository<ValueMultiselect, Integer> {
}