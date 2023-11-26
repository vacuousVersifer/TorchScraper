package Memory;

public enum ListType {
    COOKIELIST("CookieList"),
    STAFFLIST("StaffList"),
    STORYLIST("StoryList");

    private final String type;

    ListType(String type) {
        this.type = type;
    }

    public String type() {
        return this.type;
    }
}
