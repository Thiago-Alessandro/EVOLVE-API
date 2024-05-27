package net.weg.taskmanager.repository;

import net.weg.taskmanager.model.entity.DashBoard.LabelsData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface LabelsDataRepository extends JpaRepository<LabelsData, Long> {

    @Transactional
    void deleteAllByChart_Id(Long id);

}
