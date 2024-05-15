package net.weg.taskmanager.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import net.weg.taskmanager.model.dto.post.PostTeamDTO;
import net.weg.taskmanager.security.model.entity.Role;
import net.weg.taskmanager.utils.ColorUtils;
import org.springframework.beans.BeanUtils;
import lombok.Setter;
import net.weg.taskmanager.model.dto.converter.get.GetFileConverter;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;
import java.util.Objects;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
//    private String descricao;

    @Setter
    @OneToOne(cascade = CascadeType.ALL)
    private File image;
    private String imageColor;

    @OneToMany(mappedBy = "team")
    private Collection<UserTeam> participants;


    @OneToMany(mappedBy = "team")
    private Collection<Project> projects;

    @OneToOne(optional = false, cascade = CascadeType.ALL)
    private TeamChat chat;

    @OneToMany
    private Collection<TeamNotification> notifications;

    @Column(nullable = false)
    private Boolean personalWorkspace = false;
    private String code;

    @ManyToMany(cascade = CascadeType.ALL)
    private Collection<Role> roles;

    @ManyToOne
    private Role defaultRole;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Team team = (Team) o;
        return Objects.equals(id, team.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }



    public Team(PostTeamDTO teamDTO) {
        BeanUtils.copyProperties(teamDTO, this);
        this.imageColor = ColorUtils.generateHexColor();
    }

//    public Team(User user){
//        this.name = user.getName() + " Workspace";
//        this.personalWorkspace = true;
//        this.administrator = user;
//        this.imageColor = user.getImageColor();
//        this.image = user.getImage();
//        this.participants = List.of(user);
//        setCreatedChatBasic();
//        this.chat.setUsers(this.participants);
//    }

    public Team(){
        setCreatedChatBasic();
    }

    public void setCreatedChatBasic(){
        this.chat = new TeamChat();
        this.chat.setTeam(this);
    }

    public void setImageFromMultipartFile(MultipartFile image) {
        this.image = GetFileConverter.buildFileFromMultipartFile(image);
    }


}
