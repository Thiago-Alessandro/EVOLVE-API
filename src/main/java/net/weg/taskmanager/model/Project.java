package net.weg.taskmanager.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import lombok.ToString;

import net.weg.taskmanager.model.property.Property;
//import net.weg.taskmanager.security.model.CustomPermission;
import net.weg.taskmanager.security.model.entity.ProfileAcess;
import net.weg.taskmanager.security.model.enums.Permission;

import java.time.LocalDate;
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
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String description;

    @OneToOne(cascade = CascadeType.ALL)
    private File image;
    private String imageColor;
    @JoinColumn(nullable = false)
    @ManyToOne()
    private User creator;

    @Column(nullable = false)
    private LocalDate finalDate;
    @Column(nullable = false)
    private LocalDate creationDate;
    @Column(nullable = false)
    private LocalDateTime lastTimeEdited;

    //vai continuar msm?
//    @ManyToMany
//    @Column(nullable = false)
//    private Collection<User> administrators;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private Collection<Property> properties;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(nullable = false)
    private Collection<Status> statusList;
    @ManyToMany
    @JoinColumn(nullable = false)
    private Collection<User> members;
    @ManyToOne
    @JoinColumn(nullable = false)
    private Team team;

    @OneToOne(optional = false, cascade = CascadeType.ALL)
    private ProjectChat chat;

    @OneToMany(mappedBy = "project")
    private Collection<Task> tasks;
    //    private Collection<User> colo
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "project_id")
    private Collection<ProfileAcess> profileAcesses;
    @ManyToOne
    private ProfileAcess defaultProfileAcess;

//    private void setDefaultAcessProfile() {
//        Permission authDelete = Permission.DELETE;
//        Permission authPut = Permission.PUT;
//        Permission authGet = Permission.GET;
//        Permission authPost = Permission.POST;
//        Permission authPatch = Permission.PATCH;
//
//        Collection<Permission> lider = new HashSet<>();
//
//        lider.add(authDelete);
//        lider.add(authPost);
//        lider.add(authGet);
//        lider.add(authPut);
//        lider.add(authPatch);
//
//        Collection<ProfileAcess> defaultHierarchies = new HashSet<>();
//        defaultHierarchies.add(new ProfileAcess("LIDER", lider));
//        //criador
//
//        Collection<Permission> administrador = new HashSet<>();
//        //lista adms
//        administrador.add(authPost);
//        administrador.add(authGet);
//        administrador.add(authPut);
//        administrador.add(authPatch);
//
//       ProfileAcess administradorAcess = new ProfileAcess("ADMINISTRADOR", administrador);
//       defaultHierarchies.add(administradorAcess);
//
//        Collection<Permission> colaborador = new HashSet<>();
//
//        colaborador.add(authGet);
//        colaborador.add(authPut);
//        colaborador.add(authPatch);
//
//        defaultHierarchies.add(new ProfileAcess("COLABORADOR", colaborador));
//
//        Collection<Permission> convidado = new HashSet<>();
//
//        convidado.add(authGet);
//
//        defaultHierarchies.add(new ProfileAcess("CONVIDADO", convidado));
//
//        if (this.getProfileAcesses() != null) {
//            this.getProfileAcesses().addAll(defaultHierarchies);
//        } else {
//            this.setProfileAcesses(defaultHierarchies);
//        }
//    }

    private void setDefaultStatus() {
        Collection<Status> defaultStatus = new HashSet<>();
        defaultStatus.add(new Status("pendente", "#7CD5F4", "#000000", true));
        defaultStatus.add(new Status("em progresso", "#FCEC62", "#000000", true));
        defaultStatus.add(new Status("concluido", "#86C19F", "#000000", true));
        defaultStatus.add(new Status("não atribuido", "#9CA3AE", "#000000", true));
        if (this.getStatusList() != null) {
            this.getStatusList().addAll(defaultStatus);
        } else {
            this.setStatusList(defaultStatus);
        }
    }

    public void updateLastTimeEdited() {
        this.lastTimeEdited = LocalDateTime.now();
    }

    public Project() {
        this.chat = new ProjectChat();
        this.chat.setProject(this);
//        this.chat.setUsers(this.members);
        this.creationDate = LocalDate.now();
        updateLastTimeEdited();
        setDefaultStatus();
//        setDefaultAcessProfile();
    }
}