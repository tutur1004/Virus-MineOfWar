package fr.milekat.virus.utils;

public class tools {
    public static boolean isInt(String string) {
        try
        {
            Integer.parseInt(string);
            return true;
        }
        catch (NumberFormatException ignore)
        {
            return false;
        }
    }
}
