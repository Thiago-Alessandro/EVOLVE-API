package net.weg.taskmanager.service;

import lombok.AllArgsConstructor;
import net.weg.taskmanager.model.entity.DashBoard.Dashboard;
import net.weg.taskmanager.model.entity.Project;
import net.weg.taskmanager.repository.DashboardRepository;
import net.weg.taskmanager.repository.ProjectRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DashboardService {

    private ChartService chartService;
    private DashboardRepository dashboardRepository;
    private ProjectRepository projectRepository;

    public Dashboard create(Dashboard dashboard, Long idProject) {
        Dashboard dashboard1 = new Dashboard();
        BeanUtils.copyProperties(dashboard, dashboard1);
        Project project = projectRepository.findById(idProject).get();
        project.getDashboards().add(dashboardRepository.save(dashboard1));
        chartService.getChartsValues(project);
        return dashboard;
    }
}
