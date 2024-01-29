package net.weg.taskmanager.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
//@Component
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Integer id;
//    @Lob
//    private MultipartFile content;
    // ?????
    @Lob
    @Column(length = 999999999)
    private String content;
    @ManyToOne
    private User sender;
    private Date date;
    private MessageStatus status;

    @ManyToOne
//    @JsonIgnore
    private Chat chat;

//    public void setChat(Object chat, ChatRepository chatRepository) {
//        System.out.println("setChat");
//        ObjectMapper om = new ObjectMapper();
//        JsonNode jsonNode = om.valueToTree(chat);
//        Integer id = jsonNode.get("id").asInt();
//        System.out.println(jsonNode);
//        this.chat = chatRepository.findById(id).get();
//        System.out.println(this.chat);
//    }
}
