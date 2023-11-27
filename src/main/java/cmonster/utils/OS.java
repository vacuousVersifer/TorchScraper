package cmonster.utils;

public class OS {

    public static String getOperatingSystem() {
        return System.getProperty("os.name");
    }

    public static boolean isWindows() {
        return (getOperatingSystem().toLowerCase().contains("win"));
    }

    public static boolean isMac() {
        return (getOperatingSystem().toLowerCase().contains("mac"));
    }

    public static boolean isLinux() {
        return (getOperatingSystem().toLowerCase().contains("nix") || getOperatingSystem().toLowerCase().contains("nux") || getOperatingSystem().toLowerCase().indexOf("aix") > 0);
    }

}
