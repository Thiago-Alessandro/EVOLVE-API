package net.weg.taskmanager.utils;

import java.util.Random;

public class ColorUtils {
    // Private constructor to prevent instantiation
    private ColorUtils() {}

    // Method to generate a new random hexadecimal color
    public static String generateHexColor() {
        // Create a Random object
        Random random = new Random();

        // Generate random RGB values
        int red = random.nextInt(256);
        int green = random.nextInt(256);
        int blue = random.nextInt(256);

        // Convert RGB to hexadecimal color code

        return "#" + rgbToHex(red, green, blue);
    }

    // Method to convert RGB values to hexadecimal color code
    private static String rgbToHex(int red, int green, int blue) {
        // Convert each RGB component to hexadecimal and concatenate them
        String hexRed = Integer.toHexString(red);
        String hexGreen = Integer.toHexString(green);
        String hexBlue = Integer.toHexString(blue);

        // Make sure each hexadecimal string is two characters long
        hexRed = padZero(hexRed);
        hexGreen = padZero(hexGreen);
        hexBlue = padZero(hexBlue);

        // Concatenate the hexadecimal strings
        String hexColor = hexRed + hexGreen + hexBlue;

        return hexColor;
    }

    // Method to ensure each hexadecimal string is two characters long
    private static String padZero(String hex) {
        return hex.length() == 1 ? "0" + hex : hex;
    }
}
