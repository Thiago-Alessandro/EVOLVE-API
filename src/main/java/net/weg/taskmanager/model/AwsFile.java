package net.weg.taskmanager.model;

import jakarta.persistence.*;
import jdk.jfr.Enabled;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AwsFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;
    @ManyToOne(optional = false)
    private Task task;
    @Column(nullable = false)
    private String awsKey;
//    private String awsKey;
//    private byte[] awsKey;
    private String type;

    public AwsFile(String name, Task task, String awsKey, String type) {
        this.name = name;
        this.task = task;
        this.awsKey = awsKey;
        this.type = type;
    }

}
