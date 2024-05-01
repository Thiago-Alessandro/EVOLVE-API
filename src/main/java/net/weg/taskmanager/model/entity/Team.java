package net.weg.taskmanager.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import net.weg.taskmanager.model.dto.post.PostTeamDTO;
import net.weg.taskmanager.security.model.entity.Role;
import net.weg.taskmanager.utils.ColorUtils;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.Collection;

@Entity
@Data
@AllArgsConstructor
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
//    @Column(nullable = false)
    private String name;
//    private String descricao;

    @OneToOne(cascade = CascadeType.ALL)
    private File image;
    private String imageColor;

//    @ManyToOne
//    @JoinColumn(nullable = false)
//    private User administrator;
//    @JoinColumn(nullable = false)
    @OneToMany(mappedBy = "team")
    private Collection<UserTeam> participants;

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


    public Team() {
        this.chat = new TeamChat();
        this.chat.setTeam(this);
    }

    public Team(PostTeamDTO teamDTO){
        BeanUtils.copyProperties(teamDTO, this);
        this.imageColor = ColorUtils.generateHexColor();
        this.chat = new TeamChat();
        this.chat.setTeam(this);
    }

}
