package net.weg.taskmanager.model.dto.get;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetChatDTO {

    private Long id;
    private Collection<GetMessageDTO> messages;
    private Collection<GetUserDTO> users;

}
