package net.weg.taskmanager.repository;

import net.weg.taskmanager.model.entity.Dashboard.ChartData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ValueChartRepository extends JpaRepository<ChartData, Long> {
}
