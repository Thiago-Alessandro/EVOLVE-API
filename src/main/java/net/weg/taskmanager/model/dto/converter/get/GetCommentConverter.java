package net.weg.taskmanager.model.dto.converter.get;

import net.weg.taskmanager.model.dto.converter.Converter;
import net.weg.taskmanager.model.dto.get.GetCommentDTO;
import net.weg.taskmanager.model.dto.shortDTOs.ShortTeamDTO;
import net.weg.taskmanager.model.entity.Comment;
import net.weg.taskmanager.model.entity.Team;

import java.util.Collection;

public class GetCommentConverter implements Converter<GetCommentDTO, Comment> {

    @Override
    public GetCommentDTO convertOne(Comment obj) {
        return obj != null ? new GetCommentDTO(obj) : null;
    }

    @Override
    public Collection<GetCommentDTO> convertAll(Collection<Comment> objs) {
        return objs != null ? objs.stream().map(this::convertOne).toList() : null;
    }
}
