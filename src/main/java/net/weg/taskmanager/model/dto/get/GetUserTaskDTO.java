package net.weg.taskmanager.model.dto.get;

import lombok.Data;
import net.weg.taskmanager.model.dto.converter.Converter;
import net.weg.taskmanager.model.dto.converter.shorts.ShortTaskConverter;
import net.weg.taskmanager.model.dto.converter.shorts.ShortUserConverter;
import net.weg.taskmanager.model.dto.shortDTOs.ShortTaskDTO;
import net.weg.taskmanager.model.dto.shortDTOs.ShortUserDTO;
import net.weg.taskmanager.model.entity.Task;
import net.weg.taskmanager.model.entity.User;
import net.weg.taskmanager.model.entity.UserTask;
import net.weg.taskmanager.model.record.PriorityRecord;
import org.springframework.beans.BeanUtils;

@Data
public class GetUserTaskDTO {
    private Long userId;
    private Long taskId;
    private ShortUserDTO user;
    private Integer workedHours;
    private Integer workedMinutes;
    private Integer workedSeconds;

    public GetUserTaskDTO(UserTask userTask) {
        if(userTask != null) {
            BeanUtils.copyProperties(userTask, this);
            Converter<ShortUserDTO, User> userConverter = new ShortUserConverter();
            this.user = userConverter.convertOne(userTask.getUser());
        } else {
            this.user = null;
        }
    }
}
