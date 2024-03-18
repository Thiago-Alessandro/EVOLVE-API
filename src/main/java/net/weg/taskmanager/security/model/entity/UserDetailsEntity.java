package net.weg.taskmanager.security.model.entity;

import jakarta.persistence.*;
import lombok.*;
import net.weg.taskmanager.model.User;
import net.weg.taskmanager.security.model.enums.Auth;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

//com a criacao de uma segunda entity é bom deixar as necessidades de autentificacao na impleentacao do userDetails,
//e os atributos necessarios para a aplicacao na classe usuario
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
//@ToString
public class UserDetailsEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false, updatable = false)
//    @Email
    private String username;
    @Column(nullable = false)
    @Length(min = 6)
    private String password;
    private boolean enabled = true;
//    @OneToMany
//    @Enumerated(EnumType.STRING)
    private Collection<Auth> authorities = null;
    private boolean accountNonExpired = true;
    private boolean accountNonLocked = true;
    private boolean credentialsNonExpired = true;
    @OneToOne(mappedBy = "userDetailsEntity")
//    @JsonIgnore
    private User user;

    @Override
    public String toString() {
        return "UsuarioDetailsEntity";
    }

}