package net.weg.taskmanager.model.entity.DashBoard;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Transactional
public class LabelsData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String label;

    private Integer value;

    @ManyToOne
    @JsonIgnore
    private Chart chart;


    @Override
    public int hashCode() {
        return Objects.hash(id, label, value, chart);
    }

    @Override
    public String toString() {
        return "LabelsData{" +
                "label='" + label + '\'' +
                ", value=" + value +
                '}';
    }
}