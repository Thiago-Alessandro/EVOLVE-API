package net.weg.taskmanager.model.dto.converter.get;

import net.weg.taskmanager.model.dto.converter.Converter;
import net.weg.taskmanager.model.dto.get.GetFileDTO;
import net.weg.taskmanager.model.entity.File;

import java.util.Collection;

public class GetFileConverter implements Converter<GetFileDTO, File> {
    @Override
    public GetFileDTO convertOne(File obj) {
        return null;
    }

    @Override
    public Collection<GetFileDTO> convertAll(Collection<File> objs) {
        return null;
    }
}
