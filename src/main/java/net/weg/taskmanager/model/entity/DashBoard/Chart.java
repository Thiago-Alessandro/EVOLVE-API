package net.weg.taskmanager.model.entity.DashBoard;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Transactional
public class Chart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String label;

    private String type;

    private Integer chartIndex;

    @OneToMany(mappedBy = "chart", cascade = CascadeType.ALL)
    private Collection<LabelsData> labels = new HashSet<>();


    @Override
    public int hashCode() {
        return Objects.hash(id, label);
    }

    @Override
    public String toString() {
        return "Chart{" +
                "id=" + id + '\'' +
                ", label='" + label + '\'' +
                ", chartIndex=" + chartIndex +
                '}';
    }
}


