package net.weg.taskmanager.service;

import lombok.AllArgsConstructor;
import net.weg.taskmanager.model.entity.DashBoard.Chart;
import net.weg.taskmanager.model.entity.DashBoard.ChartData;
import net.weg.taskmanager.model.entity.Project;
import net.weg.taskmanager.model.entity.Status;
import net.weg.taskmanager.model.entity.Task;
import net.weg.taskmanager.repository.ChartDataRepository;
import net.weg.taskmanager.repository.ChartRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

@Service
@AllArgsConstructor
public class ChartService {

    private ChartRepository chartRepository;
    private ChartDataRepository chartDataRepository;
    public Collection<Chart> getChartsValues(Project project){

        if(project.getCharts().isEmpty()){
            Chart chart = new Chart();
            chart.setLabel("Status mais utilizados");
            chartRepository.save(chart);
        }

        Collection<Chart> charts = new HashSet<>();

        for(Chart chartFor : project.getCharts()){
            if(!chartFor.getData().isEmpty()){
                chartDataRepository.deleteAll(chartFor.getData());
            }
        }

        for(Chart chartFor : project.getCharts()){
            if(chartFor.getLabel().equals("Status mais utilizados")){
                for(Status statusFor : project.getStatusList()){
                    int cont = 0;
                    for(Task taskFor : project.getTasks()){
                        if(taskFor.getCurrentStatus().equals(statusFor)){
                            cont++;
                        }
                    }
                    chartFor.setData(chartDataRepository.save(new ChartData(statusFor.getName(), cont)));
                }
            }
            charts.add(chartRepository.save(chartFor));

        }

        return charts;
    }
}