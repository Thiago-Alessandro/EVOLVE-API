package net.weg.taskmanager.controller;

import lombok.AllArgsConstructor;
import net.weg.taskmanager.model.entity.DashBoard.Dashboard;
import net.weg.taskmanager.service.DashboardService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/{idProject}/dashboard")
@AllArgsConstructor
public class DashboardController {

    private DashboardService dashboardService;
    @PostMapping
    public Dashboard create(@RequestBody Dashboard dashboard, @PathVariable Long idProject){
        return dashboardService.create(dashboard, idProject);
    }
}
