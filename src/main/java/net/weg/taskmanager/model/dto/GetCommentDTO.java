package net.weg.taskmanager.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.weg.taskmanager.model.dto.converter.Converter;
import net.weg.taskmanager.model.dto.converter.get.GetUserConverter;
import net.weg.taskmanager.model.dto.get.GetUserDTO;
import net.weg.taskmanager.model.entity.Comment;
import net.weg.taskmanager.model.entity.Project;
import net.weg.taskmanager.model.entity.Task;
import net.weg.taskmanager.model.entity.User;
import org.springframework.beans.BeanUtils;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetCommentDTO {

    private Long id;
    private GetUserDTO user;
    private String value;
    private String timeHour;
    private String timeDayAndMonth;

    public GetCommentDTO(Comment comment){
        BeanUtils.copyProperties(comment, this);
        Converter<GetUserDTO, User> userConverter = new GetUserConverter();
        this.user = userConverter.convertOne(comment.getUser());
    }

}
