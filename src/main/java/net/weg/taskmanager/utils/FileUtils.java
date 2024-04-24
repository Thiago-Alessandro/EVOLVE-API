package net.weg.taskmanager.utils;

import net.weg.taskmanager.model.File;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public class FileUtils {

    private FileUtils(){};

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

}