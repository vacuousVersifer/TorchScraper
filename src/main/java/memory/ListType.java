package memory;

public enum ListType {
    COOKIE_LIST("CookieList"),
    STAFF_LIST("StaffList"),
    STORY_LIST("StoryList");

    private final String type;

    ListType(String type) {
        this.type = type;
    }

    public String type() {
        return this.type;
    }
}
