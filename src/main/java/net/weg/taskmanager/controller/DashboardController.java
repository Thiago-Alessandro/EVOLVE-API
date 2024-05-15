package net.weg.taskmanager.controller;

import lombok.AllArgsConstructor;
import net.weg.taskmanager.model.entity.DashBoard.Chart;
import net.weg.taskmanager.model.entity.DashBoard.Dashboard;
import net.weg.taskmanager.repository.ProjectRepository;
import net.weg.taskmanager.service.ChartService;
import net.weg.taskmanager.service.DashboardService;
import net.weg.taskmanager.service.TeamNotificationService;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/{idProject}/dashboard")
@AllArgsConstructor
public class DashboardController {

    private DashboardService dashboardService;
    private ChartService chartService;
    private ProjectRepository projectRepository;
    private TeamNotificationService teamNotificationService;

    @PostMapping("/{userActionId}")
    public Dashboard create(@RequestBody Dashboard dashboard, @PathVariable Long idProject, @PathVariable Long userActionId){
        teamNotificationService.createDashboardNotification(idProject, userActionId, dashboard);
        return dashboardService.create(dashboard, idProject);
    }

    @GetMapping
    public Collection<Dashboard> getAll(@PathVariable Long idProject){
        return dashboardService.getAll(idProject);
    }

    @PatchMapping("/getCharts")
    public Collection<Chart> getCharts(@PathVariable Long idProject){
        return chartService.getChartsValues(projectRepository.findById(idProject).get());
    }

    @DeleteMapping("/{idDashboard}/{idActionUser}")
    public void delete(@PathVariable Long idDashboard, @PathVariable Long idActionUser){
        try {
            teamNotificationService.deleteDashboardNotification(idDashboard, idActionUser);
            dashboardService.delete(idDashboard);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PatchMapping("/{idDashboard}")
    public Chart setChartToDash(@RequestBody Chart chart, @PathVariable Long idDashboard){
       return dashboardService.setChartToDash(chart, idDashboard);
    }

    @PatchMapping("/{idDashboard}/updateChartList")
    public void updateDashboardCharts(@RequestBody Collection<Chart> charts, @PathVariable Long idDashboard){
        dashboardService.updateDashboardCharts(charts, idDashboard);
    }

    @DeleteMapping(("/{idDashboard}/delete-chart/{idChart}"))
    public void deleteChart(@PathVariable Long idChart, @PathVariable Long idDashboard){
        chartService.delete(idChart, idDashboard);
    }

    @PutMapping("/{idDashboard}/updateName")
    public Dashboard updateName(@PathVariable Long idDashboard, @RequestBody Dashboard dashboard){
        return dashboardService.updateName(idDashboard, dashboard);
    }
}
