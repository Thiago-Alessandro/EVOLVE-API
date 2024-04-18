package net.weg.taskmanager.repository;

import net.weg.taskmanager.model.entity.DashBoard.ChartData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChartDataRepository extends JpaRepository<ChartData, Long> {
}
