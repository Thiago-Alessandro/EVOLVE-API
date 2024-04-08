package net.weg.taskmanager.security.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.weg.taskmanager.model.Project;
import net.weg.taskmanager.security.model.enums.Auth;

import java.util.Collection;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfileAcess {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
//    @ManyToOne
//    @Enumerated(EnumType.ORDINAL)
    private Collection<Auth> auths;
    @ManyToOne
    @JsonIgnore
    private Project project;

    public ProfileAcess(String name, Collection<Auth> auths) {
        this.name = name;
        this.auths = auths;
    }
}