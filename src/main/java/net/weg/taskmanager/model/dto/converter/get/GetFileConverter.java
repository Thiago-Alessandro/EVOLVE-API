package net.weg.taskmanager.model.dto.converter.get;

import net.weg.taskmanager.model.dto.converter.Converter;
import net.weg.taskmanager.model.dto.get.GetFileDTO;
import net.weg.taskmanager.model.entity.File;
import org.springframework.beans.BeanUtils;

import java.util.Base64;
import java.util.Collection;

public class GetFileConverter implements Converter<GetFileDTO, File> {
    @Override
    public GetFileDTO convertOne(File obj) {
        return obj!=null && obj.getData().length>0 ? getFileDTOFormated(obj) : null;
    }

    @Override
    public Collection<GetFileDTO> convertAll(Collection<File> objs) {
        return objs != null ? objs.stream().map(this::convertOne).toList() : null;
    }

    public static GetFileDTO getFileDTOFormated(File file){
        GetFileDTO getFileDTO = new GetFileDTO();
        BeanUtils.copyProperties(file, getFileDTO);
        getFileDTO.setData(formatFileDataBytesToBase64String(file));
        return getFileDTO;
    }

    private static String formatFileDataBytesToBase64String(File file){
        return getFileBase64prefix(file) + byteArrayToBase64String(file.getData());
    }

    private static String getFileBase64prefix(File file){
        return "data:" + file.getType() + ";base64,";
    }

    private static String byteArrayToBase64String(byte[] bytes){
        return Base64.getEncoder().encodeToString(bytes);
    }
}
