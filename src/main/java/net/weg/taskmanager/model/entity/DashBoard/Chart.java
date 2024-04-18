package net.weg.taskmanager.model.entity.DashBoard;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Chart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String label;

    @OneToMany(mappedBy = "chart", cascade = CascadeType.ALL)
    private Collection<ChartData> data = new ArrayList<>();

    public void setData(ChartData data) {
        this.data.add(data);
    }
}