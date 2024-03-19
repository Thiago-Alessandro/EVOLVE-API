package net.weg.taskmanager.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.web.multipart.MultipartFile;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class File {

    public File(MultipartFile multipartFile, String randomId){
        this.setName(multipartFile.getOriginalFilename());
        this.setType(multipartFile.getContentType());
        this.setChaveAWS(randomId);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String type;
    private String chaveAWS;

    public void setData(byte[] bytes) {
    }
}
