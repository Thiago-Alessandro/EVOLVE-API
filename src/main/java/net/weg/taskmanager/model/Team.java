package net.weg.taskmanager.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Integer id;
    private String name;
    private String image;
//    private String descricao;
    @ManyToOne
    @JsonIgnore
    private User administrator;
    @OneToMany(mappedBy = "team")
    private Collection<Project> projects;
    @ManyToMany()
    @JsonIgnore
    //tirar eventualmente
    private Collection<User> participants;
    @OneToOne(cascade = CascadeType.ALL)
    @JsonIgnore
    private TeamChat chat;

}
