package net.weg.taskmanager.service;

import lombok.AllArgsConstructor;
import net.weg.taskmanager.model.entity.DashBoard.Chart;
import net.weg.taskmanager.model.entity.DashBoard.LabelsData;
import net.weg.taskmanager.model.entity.DashBoard.Dashboard;
import net.weg.taskmanager.model.entity.Project;
import net.weg.taskmanager.model.entity.Status;
import net.weg.taskmanager.model.entity.Task;
import net.weg.taskmanager.model.entity.User;
import net.weg.taskmanager.model.enums.Priority;
import net.weg.taskmanager.repository.LabelsDataRepository;
import net.weg.taskmanager.repository.ChartRepository;
import net.weg.taskmanager.repository.DashboardRepository;
import net.weg.taskmanager.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@AllArgsConstructor
public class ChartService {

    private ChartRepository chartRepository;
    private LabelsDataRepository labelsDataRepository;
    private DashboardRepository dashboardRepository;
    private UserRepository userRepository;
    private ModelMapper modelMapper;

    public Collection<Chart> getChartsValues(Project project){
        if (project.getCharts().isEmpty()) {
            Chart chart = new Chart();
            chart.setLabel("Status das tarefas");
            Chart chart2 = new Chart();
            chart2.setLabel("Tarefa por associado");
            Chart chart3 = new Chart();
            chart3.setLabel("Prioridade das tarefas");
            project.setCharts(List.of(chart, chart2, chart3));
        }

        Collection<Chart> charts = new HashSet<>();

        if(!project.getCharts().isEmpty()){

            for (Chart chart : project.getCharts()){
                if(!chart.getLabels().isEmpty()){
                    chart.getLabels().stream().forEach(labelsData -> {
                        labelsDataRepository.deleteById(labelsData.getId());
                    });
                }
                if(chart.getLabel().equals("Status das tarefas")){
                    chart.setLabels(updateLabelsValue(project, chart));
                }
                else if(chart.getLabel().equals("Tarefa por associado")){
                    chart.setLabels(updateTarefaAssociado(project, chart));
                }
                else if(chart.getLabel().equals("Prioridade das tarefas")){
                    chart.setLabels(updatePrioridadeTarefas(project, chart));
                }
                chartRepository.save(chart);
                charts.add(chart);
            }
        }

        return charts;
    }

    private Collection<LabelsData> updatePrioridadeTarefas(Project project, Chart chart) {
        Collection<LabelsData> labelsDatas = new HashSet<>();
        List<Priority> priorities = List.of(Priority.values());

        priorities.stream().forEach(priority -> {
            LabelsData labelsData = new LabelsData();
            int cont = 0;
            labelsData.setLabel(priority.name());
            for(Task task : project.getTasks()){
                if(task.getPriority().equals(priority)){
                    cont++;
                }
                labelsData.setValue(cont);
                labelsData.setChart(chart);
                labelsDataRepository.save(labelsData);
                labelsDatas.add(labelsData);
            };
        });
        return labelsDatas;
    }

    public Collection<LabelsData> updateLabelsValue(Project project, Chart chart) {
        if(chart.getLabel().equals("Status das tarefas")){
            return updateStatusValue(project, chart);
        }
        else if(chart.getLabel().equals("Tarefa por associado")){
            return updateTarefaAssociado(project, chart);
        }
        else if(chart.getLabel().equals("Prioridade das tarefas")){
            return updatePrioridadeTarefas(project, chart);
        }

        return null;
    }

    private Collection<LabelsData> updateTarefaAssociado(Project project, Chart chart) {
        Collection<LabelsData> labelsDatas = new HashSet<>();

        project.getMembers().stream().forEach(member -> {
            User user = userRepository.findById(member.getUserId()).get();
            LabelsData labelsData = new LabelsData();
            labelsData.setLabel(user.getName());
            int cont = 0;
            for(Task task : project.getTasks()){
                if(task.getAssociates().contains(user)){
                    cont++;
                }
            };
            labelsData.setValue(cont);
            labelsData.setChart(chart);
            labelsDataRepository.save(labelsData);
            labelsDatas.add(labelsData);
        });
        return labelsDatas;
    }

    private Collection<LabelsData> updateStatusValue(Project project, Chart chart){
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
        chart1.setType(chart.getType());
        chartRepository.save(chart1);
        chart1.setLabels(updateLabelsValue(project, chart1));
        chartRepository.save(chart1);
        return chart1;
    }

    public void delete(Long idChart, Long idDashboard){
        labelsDataRepository.deleteAllByChart_Id(idChart);
        Dashboard dashboard = dashboardRepository.findById(idDashboard).get();
        Chart chart = chartRepository.findById(idChart).get();
        dashboard.getCharts().remove(chart);
        dashboard.setCharts(dashboard.getCharts());
        dashboardRepository.save(dashboard);
        try {
            chartRepository.delete(chart);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}