package net.weg.taskmanager.model.entity.Dashboard;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class ChartData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Chart chart;

    private String label;
    private Integer value;
    public ChartData(String name, int cont) {
        this.label = name;
        this.value = cont;
    }
}
