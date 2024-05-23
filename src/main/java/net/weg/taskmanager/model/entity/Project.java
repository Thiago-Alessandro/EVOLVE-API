package net.weg.taskmanager.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import lombok.ToString;

import net.weg.taskmanager.model.UserProject;
import net.weg.taskmanager.model.dto.post.PostProjectDTO;
import net.weg.taskmanager.model.property.Property;
import net.weg.taskmanager.security.model.entity.Role;
import org.springframework.beans.BeanUtils;
import net.weg.taskmanager.model.entity.DashBoard.Chart;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;

@Entity
@Data
@AllArgsConstructor
@ToString()
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column()
    private String name;
    private String description;
    private Boolean favorited = false;

    @OneToOne(cascade = CascadeType.ALL)
    private File image;
    private String imageColor;
    private Double progress;

    @Column()
    private LocalDate finalDate;
    @Column(nullable = false)
    private LocalDateTime creationDate;
    @Column(nullable = false)
    private LocalDateTime lastTimeEdited;


    @OneToMany(mappedBy = "project")
    @JsonIgnore
    private Collection<Comment> comments;

    @OneToMany(cascade = CascadeType.ALL)
    private Collection<Property> properties;
//    @JoinColumn(nullable = false)
    @OneToMany()
    private Collection<Status> statusList;
    @ManyToOne(optional = false)
    private Team team;

    @OneToOne(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
//    @OneToOne(optional = false)
    private ProjectChat chat;

    @OneToMany(cascade = CascadeType.PERSIST)
    private Collection<Chart> charts;

    @OneToMany(mappedBy = "project")
    private Collection<Task> tasks;

    @OneToMany(mappedBy = "project")
//    @JoinColumn(name = "project")
    private Collection<UserProject> members;

    @ManyToOne
    private Role defaultRole;


    public void updateLastTimeEdited() {
        this.lastTimeEdited = LocalDateTime.now();
    }

    public Project() {
        this.chat = new ProjectChat();
        this.chat.setProject(this);
//        this.chat.setUsers(this.members);
        setCreationDate(LocalDateTime.now());
        updateLastTimeEdited();
//        setDefaultStatus();

//        setDefaultAcessProfile();

    }

    public Project(PostProjectDTO projectDTO) {
        BeanUtils.copyProperties(projectDTO, this);
        setCreationDate(LocalDateTime.now());
//        setDefaultStatus();
        updateLastTimeEdited();
    }
}