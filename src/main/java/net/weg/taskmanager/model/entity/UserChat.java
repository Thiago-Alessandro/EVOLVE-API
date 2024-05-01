package net.weg.taskmanager.model.entity;

import jakarta.persistence.Entity;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import net.weg.taskmanager.model.abstracts.Chat;

import java.util.Collection;

@Entity
//@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString(callSuper = true)
public class UserChat extends Chat {
}