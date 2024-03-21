package net.weg.taskmanager.model.abstracts;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import net.weg.taskmanager.model.entity.Message;
import net.weg.taskmanager.model.entity.User;

import java.util.Collection;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@ToString
public abstract class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

//    @Enumerated(value = EnumType.ORDINAL)
//    private ChatType type;

//    @OneToMany(cascade = CascadeType.ALL)

    @OneToMany(mappedBy = "chat")
    private Collection<Message> messages;

    @ManyToMany
    @Column(nullable = false)
//    @JsonIgnore
    private Collection<User> users;

}
