package net.weg.taskmanager.model.dto.converter.get;

import net.weg.taskmanager.model.dto.converter.Converter;
import net.weg.taskmanager.model.dto.get.GetFileDTO;
import net.weg.taskmanager.model.entity.File;
import org.springframework.beans.BeanUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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

    public static File buildFileFromMultipartFile(MultipartFile multipartFile){
        File file = new File();
        try {
            file.setData(multipartFile.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        file.setName(multipartFile.getOriginalFilename());
        file.setType(multipartFile.getContentType());
        return file;
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
