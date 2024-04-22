package net.weg.taskmanager.service;

import lombok.AllArgsConstructor;
import net.weg.taskmanager.model.entity.DashBoard.Chart;
import net.weg.taskmanager.model.entity.DashBoard.ChartData;
import net.weg.taskmanager.model.entity.DashBoard.Dashboard;
import net.weg.taskmanager.model.entity.Project;
import net.weg.taskmanager.model.entity.Status;
import net.weg.taskmanager.model.entity.Task;
import net.weg.taskmanager.repository.ChartDataRepository;
import net.weg.taskmanager.repository.ChartRepository;
import net.weg.taskmanager.repository.DashboardRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

@Service
@AllArgsConstructor
public class ChartService {

    private ChartRepository chartRepository;
    private ChartDataRepository chartDataRepository;
    private DashboardRepository dashboardRepository;

    public void getChartsValues(Project project){

        for (Dashboard dashboard : project.getDashboards()){
            if(dashboard.getCharts().isEmpty()){
                Chart chart = new Chart();
                chart.setLabel("Status mais utilizados");
                chartRepository.save(chart);
            }
        }

        Collection<Chart> charts = new HashSet<>();

        for (Dashboard dashboard : project.getDashboards()) {
            for (Chart chartFor : dashboard.getCharts()) {
                if (!chartFor.getData().isEmpty()) {
                    chartDataRepository.deleteAll(chartFor.getData());
                }
            }
        }

        for (Dashboard dashboard : project.getDashboards()) {
            for (Chart chartFor : dashboard.getCharts()) {
                if (chartFor.getLabel().equals("Status mais utilizados")) {
                    for (Status statusFor : project.getStatusList()) {
                        int cont = 0;
                        for (Task taskFor : project.getTasks()) {
                            if (taskFor.getCurrentStatus().equals(statusFor)) {
                                cont++;
                            }
                        }
                        chartFor.setData(chartDataRepository.save(new ChartData(statusFor.getName(), cont)));
                    }
                }
                dashboard.getCharts().add(chartRepository.save(chartFor));
            }
            dashboardRepository.save(dashboard);
        }
    }
}