package net.weg.taskmanager.model.dto.post;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.weg.taskmanager.model.Task;
import net.weg.taskmanager.model.Team;
import net.weg.taskmanager.model.User;
import net.weg.taskmanager.model.UserChat;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostUserDTO {

    private String name;
    private String email;
    private String password;
//    @Lob
//    @Column(columnDefinition = "LONGBLOB")
//    private MultipartFile image;

}
