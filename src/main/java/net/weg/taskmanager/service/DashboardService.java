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
            Collection<Dashboard> dashboards = dashboardRepository.findAllByProject_Id(idProject);
            Project project = projectRepository.findById(idProject).get();
            for(Dashboard dashboard : dashboards){
                Collection<Chart> charts = new HashSet<>();
                for(Chart chart : dashboard.getCharts()){
                    chart.setLabels(chartService.updateLabelsValue(project, chart));
                    chartRepository.save(chart);
                    charts.add(chart);
                }
                dashboard.setCharts(charts);
                dashboardRepository.save(dashboard);
            }
            return dashboards;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void delete(Long idDashboard) {
        Dashboard dashboard = dashboardRepository.findById(idDashboard).get();
        dashboardRepository.delete(dashboard);
    }

    public Chart setChartToDash(Chart chart, Long idDash) {
        Dashboard dashboard = dashboardRepository.findById(idDash).get();
        Chart chart1 = chartService.createChart(chart, dashboard.getProject(), dashboard.getCharts().size());
        dashboard.getCharts().add(chart1);
        dashboardRepository.save(dashboard);
        return chart1;
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

    public Dashboard updateName(Long idDashboard, Dashboard dashboard) {
        Dashboard dashboard1 = dashboardRepository.findById(idDashboard).get();
        dashboard1.setName(dashboard.getName());
        return dashboardRepository.save(dashboard1);
    }
}
