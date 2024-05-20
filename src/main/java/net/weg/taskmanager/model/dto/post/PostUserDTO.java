package net.weg.taskmanager.model.dto.post;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostUserDTO {

    private String name;
    private String email;
    private String password;
    private String imageColor;
    private boolean socialLogin;
//    @Lob
//    @Column(columnDefinition = "LONGBLOB")
//    private MultipartFile image;

}
