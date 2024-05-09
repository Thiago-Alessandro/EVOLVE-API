package net.weg.taskmanager.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Setter;
import net.weg.taskmanager.model.dto.converter.get.GetFileConverter;
import net.weg.taskmanager.utils.ColorUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;
import java.util.List;

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

    @Setter
    @OneToOne(cascade = CascadeType.ALL)
    private File image;
    private String imageColor;

    @ManyToOne(optional = false)

    private User administrator;
    @ManyToMany()
    @JoinColumn(nullable = false)
    private Collection<User> participants;

    @OneToMany(mappedBy = "team")
    private Collection<Project> projects;
    @OneToOne(optional = false, cascade = CascadeType.ALL)
    private TeamChat chat;

    @OneToMany
    private Collection<TeamNotification> notifications;

    @Column(nullable = false)
    private Boolean personalWorkspace = false;

    public Team(User user){
        this.name = user.getName() + " Workspace";
        this.personalWorkspace = true;
        this.administrator = user;
        this.imageColor = user.getImageColor();
        this.image = user.getImage();
        this.participants = List.of(user);
        setCreatedChatBasic();
        this.chat.setUsers(this.participants);
    }

    public Team(){
        setCreatedChatBasic();
    }

    public void setCreatedChatBasic(){
        this.chat = new TeamChat();
        this.chat.setTeam(this);
    }

    public void setImage(MultipartFile image) {
        this.image = GetFileConverter.buildFileFromMultipartFile(image);
    }


}
