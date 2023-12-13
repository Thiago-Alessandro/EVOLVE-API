package net.weg.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.w3c.dom.Text;

@Repository
public interface OpcaoRepository extends JpaRepository<Text, Integer> {
}
