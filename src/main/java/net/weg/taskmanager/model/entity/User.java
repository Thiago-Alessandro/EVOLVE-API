package net.weg.taskmanager.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
    @OneToMany(mappedBy = "administrator")
    private Collection<Team> managedTeams;
    @ManyToMany(mappedBy = "participants")
    private Collection<Team> teams;
    private String theme;
    private String primaryColor;
    private String secondaryColor;
    private String primaryDarkColor;
    private String secondaryDarkColor;



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }


    public void setImage(MultipartFile image) {
        if(image!=null){
            File file = new File();
            try {
                file.setData(image.getBytes());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            file.setName(image.getOriginalFilename());
            file.setType(image.getContentType());
            this.image = file;
        }
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
