package net.weg.taskmanager.repository;

import net.weg.taskmanager.model.entity.DashBoard.Dashboard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DashboardRepository extends JpaRepository<Dashboard, Long> {
}
