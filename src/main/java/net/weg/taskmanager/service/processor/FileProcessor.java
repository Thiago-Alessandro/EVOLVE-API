package net.weg.taskmanager.service.processor;

public class FileProcessor {

    private static final String imageBase64Prefix = "data:image/png;base64,";

    public static String addBase64Prefix(String base64Image) {

        if(base64Image != null && !base64Image.contains(imageBase64Prefix)){
            return imageBase64Prefix + base64Image;
        }
        return base64Image;
    }

    public static boolean isStringAHashColor(String base64Image){
        if(base64Image != null){
            return base64Image.startsWith("#") && base64Image.length() == 7;
        }
        return false;
    }

    public static String getImageBase64Prefix() {
        return imageBase64Prefix;
    }

}
