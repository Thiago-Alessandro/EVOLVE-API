package net.weg.taskmanager.security.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.weg.taskmanager.security.model.enums.Permission;

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
    private Collection<Permission> auths;

    public ProfileAcess(String name, Collection<Permission> auths) {
        this.name = name;
        this.auths = auths;
    }
}