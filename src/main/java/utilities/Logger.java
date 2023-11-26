package utilities;

import java.util.Scanner;

public class Logger {
    private static final Scanner scanner = new Scanner(System.in);

    public static void log(SectionName section, String message) {
        System.out.println(construct(section, message));
    }

    public static void log(SectionName section, String message, Boolean useNewLine) {
        if (useNewLine) {
            log(section, message);
        } else {
            System.out.print(construct(section, message));
        }
    }


    public static Boolean askYesOrNo(SectionName section, String question) {
        log(section, question + " (Y/N): ", false);
        String response = scanner.nextLine();
        if (response.equals("Y")) {
            return true;
        } else if (response.equals("N")) {
            return false;
        } else {
            Logger.log(section, "Invalid response. Lets try again!");
            return askYesOrNo(section, question);
        }
    }

    public static int askNumber(SectionName section, String question) {
        log(section, question + " (Enter a number): ", false);
        String response = scanner.nextLine();
        try {
            return Integer.parseInt(response);
        } catch (NumberFormatException e) {
            Logger.log(section, "Invalid response. Lets try again!");
            return askNumber(section, question);
        }
    }

    public static String askString(SectionName section, String question) {
        log(section, question + " (Enter text): ", false);
        return scanner.nextLine();
    }

    private static String construct(SectionName section, String message) {
        if (section == SectionName.SILENT) return message;

        StringBuilder builder = new StringBuilder();
        int sectionLevel = section.level();
        int sectionSize = section.size();
        String sectionName = section.section();

        for (int i = 0; i < sectionLevel; i++) {
            if (i == 0) {
                builder.append("└");
            } else {
                builder.append("─");
            }
        }

        builder.append("[");
        builder.append(sectionName);

        int spaceSize = 16 - sectionSize - sectionLevel;
        builder.append(" ".repeat(Math.max(0, spaceSize)));

        builder.append("] ");
        builder.append(message);

        return builder.toString();
    }
}
