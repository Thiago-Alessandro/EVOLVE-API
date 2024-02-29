package net.weg.taskmanager.model;

import jakarta.persistence.Entity;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
//@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString(callSuper = true)
public class UserChat extends Chat{

}