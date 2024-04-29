package net.weg.taskmanager.service;

import lombok.AllArgsConstructor;
import net.weg.taskmanager.model.entity.DashBoard.Chart;
import net.weg.taskmanager.model.entity.DashBoard.LabelsData;
import net.weg.taskmanager.model.entity.DashBoard.Dashboard;
import net.weg.taskmanager.model.entity.Project;
import net.weg.taskmanager.model.entity.Status;
import net.weg.taskmanager.model.entity.Task;
import net.weg.taskmanager.repository.LabelsDataRepository;
import net.weg.taskmanager.repository.ChartRepository;
import net.weg.taskmanager.repository.DashboardRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

@Service
@AllArgsConstructor
public class ChartService {

    private ChartRepository chartRepository;
    private LabelsDataRepository labelsDataRepository;
    private ModelMapper modelMapper;

    public Collection<Chart> getChartsValues(Project project){
        if (project.getCharts().isEmpty()) {
            Chart chart = new Chart();
            chart.setLabel("Status das tarefas");
            Chart chart1 = new Chart();
            chart1.setLabel("Status das tarefa");
            project.setCharts(List.of(chart, chart1));
        }

        Collection<Chart> charts = new HashSet<>();

        if(!project.getCharts().isEmpty()){
            for (Chart chart : project.getCharts()){
                if(chart.getLabel().equals("Status das tarefas")){
                    labelsDataRepository.deleteAll(labelsDataRepository.findAll());
                    chart.setLabels(updateStatusValue(project, chart));
                }
                chartRepository.save(chart);
                charts.add(chart);
            }
        }

        return charts;
    }

    private Collection<LabelsData> updateStatusValue(Project project, Chart chart) {
        Collection<LabelsData> labelsDatas = new HashSet<>();

        for(Status status : project.getStatusList()){
            LabelsData labelsData = new LabelsData();
            int cont = 0;
            labelsData.setLabel(status.getName());
            for(Task task : project.getTasks()){
                if(task.getCurrentStatus().equals(status)){
                    cont++;
                }
            }
            labelsData.setValue(cont);
            labelsData.setChart(chart);
            labelsDataRepository.save(labelsData);
            labelsDatas.add(labelsData);
        }
        return labelsDatas;
    }

    public Chart createChart(Chart chart, Project project, Integer chartIndex){
        Chart chart1 = new Chart();
        chart1.setLabel(chart.getLabel());
        chart1.setChartIndex(chartIndex);
        chartRepository.save(chart1);
        chart1.setLabels(updateStatusValue(project, chart1));
        chartRepository.save(chart1);
        System.out.println(chart1);
        return chart1;
    }
}