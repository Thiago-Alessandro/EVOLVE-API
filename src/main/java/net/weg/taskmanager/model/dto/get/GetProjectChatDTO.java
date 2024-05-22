package net.weg.taskmanager.model.dto.get;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import net.weg.taskmanager.model.dto.converter.Converter;
import net.weg.taskmanager.model.dto.converter.shorts.ShortProjectConverter;
import net.weg.taskmanager.model.dto.shortDTOs.ShortProjectDTO;
import net.weg.taskmanager.model.entity.Project;
import net.weg.taskmanager.model.entity.ProjectChat;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetProjectChatDTO extends GetChatDTO{
    private ShortProjectDTO project;
    public GetProjectChatDTO(ProjectChat projectChat){
        super(projectChat);
        Converter<ShortProjectDTO, Project> shortProjectConverter = new ShortProjectConverter();
        this.project = shortProjectConverter.convertOne(projectChat.getProject());
    }

}
