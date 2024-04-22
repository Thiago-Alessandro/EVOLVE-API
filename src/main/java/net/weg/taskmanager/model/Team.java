package net.weg.taskmanager.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import net.weg.taskmanager.security.model.entity.Role;

import java.util.ArrayList;
import java.util.Collection;

@Entity
@Data
@AllArgsConstructor
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
//    private String descricao;

    @OneToOne(cascade = CascadeType.ALL)
    private File image;
    private String imageColor;

    @ManyToOne
    @JoinColumn(nullable = false)
    private User administrator;
    @ManyToMany()
    @JoinColumn(nullable = false)
    private Collection<User> participants;

    @OneToMany(mappedBy = "team")
    private Collection<Project> projects;
    @OneToOne(optional = false, cascade = CascadeType.ALL)
    private TeamChat chat;

    @Column(nullable = false)
    private Boolean personalWorkspace = false;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private Collection<Role> roles;
    @ManyToOne
    private Role defaultRole;

//    private void setDefaultAcessProfile() {
//        Permission authDelete = Permission.DELETE;
//        Permission authPut = Permission.PUT;
//        Permission authGet = Permission.GET;
//        Permission authPost = Permission.POST;
//
//        Collection<Permission> lider = new HashSet<>();
//
//        lider.add(authDelete);
//        lider.add(authPost);
//        lider.add(authGet);
//        lider.add(authPut);
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
//
//        defaultHierarchies.add(new ProfileAcess("ADMINISTRADOR", administrador));
//
//        Collection<Permission> colaborador = new HashSet<>();
//
//        colaborador.add(authGet);
//        colaborador.add(authPut);
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

    public Team(User user) {
        this.name = user.getName() + " Workspace";
        this.personalWorkspace = true;
        this.administrator = user;
        this.imageColor = user.getImageColor();
        this.image = user.getImage();
        Collection<User> participants = new ArrayList<>();
        participants.add(user);
        this.participants = participants;
        this.chat = new TeamChat();
        this.chat.setUsers(this.participants);
    }

    public Team() {
        this.chat = new TeamChat();
        this.chat.setTeam(this);
//        setDefaultAcessProfile();
    }

}
