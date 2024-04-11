package net.weg.taskmanager.model.dto.shortDTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.weg.taskmanager.model.dto.converter.Converter;
import net.weg.taskmanager.model.dto.converter.get.GetFileConverter;
import net.weg.taskmanager.model.dto.get.GetFileDTO;
import net.weg.taskmanager.model.entity.File;
import net.weg.taskmanager.model.entity.User;
import org.springframework.beans.BeanUtils;

import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShortUserDTO {

    private Long id;
    private String name;
    private String email;
    private GetFileDTO image;
    private String imageColor;
    private Collection<ShortTaskDTO> createdTasks;
//    private Collection<UserChat> chats;
    public ShortUserDTO(User user){
        Converter<GetFileDTO, File> fileConverter = new GetFileConverter();
        BeanUtils.copyProperties(user, this);
        this.image = fileConverter.convertOne(user.getImage());
//        this.createdTasks = DTOUtils.tasksToShortGetTaskDTOS(user.getCreatedTasks());
//        this.createdTasks = DTOUtils.user.getCreatedTasks()
    }

}
