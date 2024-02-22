package net.weg.taskmanager.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Integer id;

//    @Enumerated(value = EnumType.ORDINAL)
//    private ChatType type;

//    @OneToMany(cascade = CascadeType.ALL)

    @OneToMany(mappedBy = "chat")
    private Collection<Message> messages;

    @ManyToMany
    @Column(nullable = false)
    private Collection<User> users;

}
