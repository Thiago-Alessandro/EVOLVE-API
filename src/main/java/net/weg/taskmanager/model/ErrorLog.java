package net.weg.taskmanager.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.json.JSONParser;

import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class ErrorLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String error;
    private Long requestSenderId;
    private LocalDateTime dateTime;

    public ErrorLog(String title, String error, Long requestSenderId, LocalDateTime dateTime){
        this.title = title;
        this.error = error;
        this.requestSenderId = requestSenderId;
        this.dateTime = dateTime;
    }

    @Override
    public String toString() {
        //cpa tem q retornar o nome do objeto antes do JSON
        return String.format(
                "{id: %d, title: %s, error: %s, dateTime: %s, requestSenderId: %d}"
                ,id, title, error, dateTime, requestSenderId
        );
    }
}
