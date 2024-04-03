package net.weg.taskmanager.model.dto.get;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import net.weg.taskmanager.model.dto.shortDTOs.ShortUserDTO;
import net.weg.taskmanager.model.dto.utils.DTOUtils;
import net.weg.taskmanager.model.entity.Comment;
import net.weg.taskmanager.model.entity.Project;
import net.weg.taskmanager.model.entity.Task;
import net.weg.taskmanager.model.entity.User;
import org.springframework.beans.BeanUtils;

@Data
public class GetCommentDTO {


    private Long id;
    private ShortUserDTO user;

    private String value;
    private String timeHour;
    private String timeDayAndMonth;

    public GetCommentDTO(Comment comment){
        BeanUtils.copyProperties(comment, this);
        this.user = DTOUtils.userToShortUserDTO(comment.getUser());
    }
}
