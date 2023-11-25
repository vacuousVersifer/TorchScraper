public class Logger {
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
