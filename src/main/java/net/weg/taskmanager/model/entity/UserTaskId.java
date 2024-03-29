package net.weg.taskmanager.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserTaskId implements Serializable {

    private Long userId;
    private Long taskId;

}
