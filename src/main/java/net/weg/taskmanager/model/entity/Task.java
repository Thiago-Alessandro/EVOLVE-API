package net.weg.taskmanager.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import lombok.ToString;
import net.weg.taskmanager.model.enums.Priority;
import net.weg.taskmanager.model.property.Property;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name = "Sem Nome";
    @Column(nullable = false)
    private Boolean favorited = false;

    private LocalDate finalDate;
    private LocalDate creationDate = LocalDate.now();
    private LocalDateTime lastTimeEdited = LocalDateTime.now();
    private LocalDate scheduledDate;

    private String description;

    @OneToMany
    private Collection<Historic> historic;

    @ManyToOne()
    private Status currentStatus;

    @Enumerated(EnumType.STRING)
    private Priority priority ;

    @OneToMany(mappedBy = "task")
    private Collection<Comment> comments;

    @ManyToOne
    @JoinColumn(nullable = false)
    private User creator;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Project project;

    @OneToMany(cascade = CascadeType.ALL)
    private Collection<Property> properties;

    @OneToMany(cascade = CascadeType.ALL)
    private Collection<Subtask> subtasks;

    @ManyToMany()
    @Column(nullable = false)
    private Collection<User> associates;

    @OneToMany(cascade = CascadeType.ALL)
    private Collection<File> files;

    private Integer statusListIndex;
    private Double progress;

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    public void setFile(MultipartFile attachment) {
        File file = new File();
        try {
            file.setData(attachment.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        file.setName(attachment.getOriginalFilename());
        file.setType(attachment.getContentType());
        this.files.add(file);
    }
}
