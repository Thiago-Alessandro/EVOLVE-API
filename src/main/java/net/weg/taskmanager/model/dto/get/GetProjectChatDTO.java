package net.weg.taskmanager.model.dto.get;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.weg.taskmanager.model.dto.utils.DTOUtils;
import net.weg.taskmanager.model.entity.Project;
import net.weg.taskmanager.model.entity.ProjectChat;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetProjectChatDTO extends GetChatDTO{
    private GetProjectDTO project;
    public GetProjectChatDTO(ProjectChat projectChat){
        super(projectChat);
        this.project = DTOUtils.projectToGetProjectDTO(projectChat.getProject());
    }

}
