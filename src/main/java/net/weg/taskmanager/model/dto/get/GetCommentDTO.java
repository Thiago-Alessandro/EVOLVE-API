package net.weg.taskmanager.model.dto.get;

import lombok.Data;
import net.weg.taskmanager.model.dto.converter.Converter;
import net.weg.taskmanager.model.dto.converter.shorts.ShortUserConverter;
import net.weg.taskmanager.model.dto.shortDTOs.ShortUserDTO;
import net.weg.taskmanager.model.entity.Comment;
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
        Converter<ShortUserDTO, User> shortUserConverter = new ShortUserConverter();
        BeanUtils.copyProperties(comment, this);
        this.user = shortUserConverter.convertOne(comment.getUser());
    }
}
