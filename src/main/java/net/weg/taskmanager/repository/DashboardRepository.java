package net.weg.taskmanager.repository;

import net.weg.taskmanager.model.entity.DashBoard.Dashboard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

public interface DashboardRepository extends JpaRepository<Dashboard, Long> {
    Collection<Dashboard> findAllByProject_Id(Long id);

    @Transactional
    void deleteDashboardsByProject_Id(Long id);
}
