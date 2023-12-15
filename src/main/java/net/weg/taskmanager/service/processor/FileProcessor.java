package net.weg.taskmanager.service.processor;

public class FileProcessor {

    private static final String imageBase64Prefix = "data:image/png;base64,";

    public static String addBase64Prefix(String base64Image) {

        return imageBase64Prefix + base64Image;
    }

    public static String getImageBase64Prefix() {
        return imageBase64Prefix;
    }
}
