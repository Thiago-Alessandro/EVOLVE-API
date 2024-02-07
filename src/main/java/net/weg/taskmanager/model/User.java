package net.weg.taskmanager.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.Objects;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Integer id;
    private String name;
    private String password;
    private String email;
    @Lob
    @Column(length = 999999999)
//    private String testeImagem;
    private String profilePicture;

    @ManyToMany(mappedBy = "users")
//    @JsonIgnore
    private Collection<UserChat> chats;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @OneToMany(mappedBy = "creator")
    @JsonIgnore
    private Collection<Task> createdTasks;
    @OneToMany(mappedBy = "administrator")
//    @JsonIgnore
    private Collection<Team> managedTeams;
    @ManyToMany(mappedBy = "participants")
    //tava na equipe
//    @JsonIgnore
    private Collection<Team> teams;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
//                ", fotoPerfil='" + fotoPerfil.substring(0,100) + '\'' +
                '}';
    }
}