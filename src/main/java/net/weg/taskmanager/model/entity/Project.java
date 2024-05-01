package net.weg.taskmanager.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import lombok.ToString;

import net.weg.taskmanager.model.UserProject;
import net.weg.taskmanager.model.dto.post.PostProjectDTO;
import net.weg.taskmanager.model.property.Property;
//import net.weg.taskmanager.security.model.CustomPermission;
import net.weg.taskmanager.security.model.entity.Role;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;

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
    @Column()
    private String description;
    @Column(nullable = false)
    private Boolean favorited = false;

    @OneToOne(cascade = CascadeType.ALL)
    private File image;
    private String imageColor;

//    @JoinColumn(nullable = false)
//    @ManyToOne()
//    private User creator;

    @Column()
    private LocalDateTime finalDate;
    @Column(nullable = false)
    private LocalDateTime creationDate;
    @Column(nullable = false)
    private LocalDateTime lastTimeEdited;

//    @ManyToMany
//    @Column(nullable = false)
//    private Collection<User> administrators;


    @OneToMany(mappedBy = "project")
    private Collection<Comment> comments;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private Collection<Property> properties;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private Collection<Status> statusList;
    @ManyToOne(optional = false)
    private Team team;

    @OneToOne(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private ProjectChat chat;

    @OneToMany(mappedBy = "project")
    private Collection<Task> tasks;
    //    private Collection<User> colo
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
//    @JoinColumn(name = "project")
    private Collection<UserProject> members;
//    @ManyToMany
//    @JoinColumn(nullable = false)
//    private Collection<User> members;
    @ManyToOne
    private Role defaultRole;

//    private void setDefaultStatus() {
//        Collection<Status> defaultStatus = new HashSet<>();
//        defaultStatus.add(new Status("pendente", "#7CD5F4", "#000000", true));
//        defaultStatus.add(new Status("em progresso", "#FCEC62", "#000000", true));
//        defaultStatus.add(new Status("concluido", "#86C19F", "#000000", true));
//        defaultStatus.add(new Status("não atribuido", "#9CA3AE", "#000000", true));
//        if (this.getStatusList() != null) {
//            this.getStatusList().addAll(defaultStatus);
//        } else {
//            this.setStatusList(defaultStatus);
//        }
//    }

    public void updateLastTimeEdited() {
        this.lastTimeEdited = LocalDateTime.now();
    }

    public Project() {
        this.chat = new ProjectChat();
        this.chat.setProject(this);
//        this.chat.setUsers(this.members);
        this.creationDate = LocalDateTime.now();
        updateLastTimeEdited();
//        setDefaultStatus();
//        setDefaultAcessProfile();
    }

    public Project(PostProjectDTO projectDTO) {
        BeanUtils.copyProperties(projectDTO, this);
        this.creationDate = LocalDateTime.now();
//        setDefaultStatus();
        updateLastTimeEdited();
    }
}