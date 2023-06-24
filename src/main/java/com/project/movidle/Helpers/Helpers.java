package com.project.movidle.Helpers;
import java.util.regex.Pattern;

public class Helpers {
    public static boolean isNumber(String str) {
        // Regex pattern to match a valid number
        String numberPattern = "-?\\d+(\\.\\d+)?";

        return Pattern.matches(numberPattern, str);
    }
}
