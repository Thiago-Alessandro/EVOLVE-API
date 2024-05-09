package net.weg.taskmanager.repository;

import net.weg.taskmanager.model.entity.DashBoard.Chart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChartRepository extends JpaRepository<Chart, Long> {
}
