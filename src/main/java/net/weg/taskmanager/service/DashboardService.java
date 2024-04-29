package net.weg.taskmanager.service;

import lombok.AllArgsConstructor;
import net.weg.taskmanager.model.dto.converter.Converter;
import net.weg.taskmanager.model.dto.converter.get.GetProjectConverter;
import net.weg.taskmanager.model.dto.get.GetProjectDTO;
import net.weg.taskmanager.model.entity.DashBoard.Chart;
import net.weg.taskmanager.model.entity.DashBoard.Dashboard;
import net.weg.taskmanager.model.entity.Project;
import net.weg.taskmanager.repository.ChartRepository;
import net.weg.taskmanager.repository.DashboardRepository;
import net.weg.taskmanager.repository.ProjectRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

@Service
@AllArgsConstructor
public class DashboardService {

    private ChartService chartService;
    private DashboardRepository dashboardRepository;
    private ProjectRepository projectRepository;
    private ChartRepository chartRepository;
    private ProjectService projectService;

    public Dashboard create(Dashboard dashboard, Long idProject) {
        try {
            Dashboard savedDashboard = new Dashboard();
            BeanUtils.copyProperties(dashboard, savedDashboard);
            Project project = projectRepository.findById(idProject).get();
            savedDashboard.setProject(project);
            dashboardRepository.save(savedDashboard);
            return savedDashboard;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Collection<Dashboard> getAll(Long idProject) {
        try {
            return dashboardRepository.findAllByProject_Id(idProject);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void delete(Long idDashboard) {
        Dashboard dashboard = dashboardRepository.findById(idDashboard).get();
        dashboardRepository.delete(dashboard);
    }

    public Dashboard setChartToDash(Chart chart, Long idDash) {
        Dashboard dashboard = dashboardRepository.findById(idDash).get();
        dashboard.getCharts().add(chartService.createChart(chart, dashboard.getProject(), dashboard.getCharts().size()));
        dashboardRepository.save(dashboard);
        return dashboard;
    }

    public void updateDashboardCharts(Collection<Chart> charts, Long idDashboard){
        Dashboard dashboard = dashboardRepository.findById(idDashboard).get();
        Collection<Chart> charts1 = dashboard.getCharts();
        for(Chart chart : charts){
            for(Chart chart1 : charts1){
                if(chart.getId().equals(chart1.getId())){
                    chart1.setChartIndex(chart.getChartIndex());
                    chartRepository.save(chart1);
                }
            }
        }
    }
}
