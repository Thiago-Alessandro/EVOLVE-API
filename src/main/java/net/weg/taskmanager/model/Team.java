package net.weg.taskmanager.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    public Team(User user){
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

    public Team(){
        this.chat = new TeamChat();
        this.chat.setTeam(this);
    }

}
