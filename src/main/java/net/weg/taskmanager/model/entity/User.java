package net.weg.taskmanager.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.weg.taskmanager.model.UserProject;
import net.weg.taskmanager.security.model.entity.UserDetailsEntity;
import net.weg.taskmanager.model.dto.converter.get.GetFileConverter;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;
import java.util.Objects;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)

    private String password;
    @Column(nullable = false, unique = true)

    private String email;

    @OneToOne(cascade = CascadeType.ALL)
    private File image;
    private String imageColor;

    @ManyToMany(mappedBy = "users")
    private Collection<UserChat> chats;
    @OneToMany(mappedBy = "creator")
    private Collection<Task> createdTasks;

    @OneToOne(cascade = CascadeType.ALL)
    @JsonIgnore
    private UserDetailsEntity userDetailsEntity;

    @OneToMany(mappedBy = "user")
//    @JoinColumn(name = "user")
    private Collection<UserProject> projectRoles;
    @OneToMany(mappedBy = "user")
//    @JsonIgnore
    private Collection<UserTeam> teamRoles;

    private String theme;
    private String primaryColor;
    private String secondaryColor;
    private String primaryDarkColor;
    private String secondaryDarkColor;
    private Integer fontSize = 16;

    @OneToOne(mappedBy = "user")
    private NotificationsConfig notificationsConfig;

    private boolean socialLogin = false;

//    public void setImage(File image){
//        if(image!=null){
//            File fi = new File();
//            fi.setLink(image.getLink());
//            if(socialLogin) this.image = fi;
//        }
//    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }


    public void setImageFromMultipartFile(MultipartFile image) {
        this.image = GetFileConverter.buildFileFromMultipartFile(image);

    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
//                ", chats =" + chats +
//                ", teams =" + teams +
                '}';
    }
}
