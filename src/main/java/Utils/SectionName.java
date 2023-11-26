package Utils;

public enum SectionName {
    PROGRAM("PROGRAM", 0),
    MEMORY("MEMORY", 1),
    ASSESSOR("ASSESSOR", 1),
    CONSTRUCTOR("CONSTRUCTOR", 1),
    COOKIE("COOKIE", 2),
    STAFF("STAFF", 2),
    STAFF_SCRAPER("STAFF SCRAPER", 3),
    STORY("STORY", 2),
    STORY_SCRAPER("STORY SCRAPER", 3),
    COUNTER("COUNTER", 2),
    COUNTER_PRINT("COUNTER PRINT", 3),
    SILENT("", 0);

    private final int level;
    private final String section;

    SectionName(String section, int level) {
        this.level = level;
        this.section = section;
    }

    public int size() {
        return name().length();
    }

    public int level() {
        return this.level;
    }

    public String section() {
        return this.section;
    }
}
