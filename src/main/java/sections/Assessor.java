package sections;

import memory.DocumentManager;
import memory.types.Cookie;
import memory.types.Staff;
import memory.types.Story;
import org.jdom2.JDOMException;
import utilities.Logger;
import utilities.SectionName;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;

public class Assessor {
    private final DocumentManager documentManager = new DocumentManager();

    public Assessor() throws IOException, JDOMException {
    }

    public void run() throws IOException, URISyntaxException {
        Logger.log(SectionName.ASSESSOR, "Begin");

        if (Logger.askYesOrNo(SectionName.ASSESSOR, "Use cached staff?")) {
            if (!Logger.askYesOrNo(SectionName.ASSESSOR, "Use cached stories?")) {
                Logger.log(SectionName.ASSESSOR, "Cookies expire " + documentManager.getCookieExpirationDate());
                if (!Logger.askYesOrNo(SectionName.ASSESSOR, "Use cached cookies?")) {
                    retrieveCookies();
                }
                retrieveStories();
            }
        } else {
            Logger.log(SectionName.ASSESSOR, "Cookies expire " + documentManager.getCookieExpirationDate());
            if (!Logger.askYesOrNo(SectionName.ASSESSOR, "Use cached cookies?")) {
                retrieveCookies();
            }
            retrieveStaff();
            retrieveStories();
        }

        Counter counter = new Counter(documentManager.getStaffList(), documentManager.getStoriesList());
        counter.run();

        Logger.log(SectionName.ASSESSOR, "Finish");
    }

    private void retrieveCookies() throws IOException {
        Logger.log(SectionName.COOKIE, "Begin");

        String cookieKey_sec = Logger.askString(SectionName.COOKIE, "Enter SEC cookie key");
        String cookieValue_sec = Logger.askString(SectionName.COOKIE, "Enter SEC cookie value");
        String cookieKey_logged_in = Logger.askString(SectionName.COOKIE, "Enter LOGGED_IN cookie key");
        String cookieValue_logged_in = Logger.askString(SectionName.COOKIE, "Enter LOGGED_IN cookie value");
        String cookie_login_expiration = Logger.askString(SectionName.COOKIE, "Enter login cookies expiration date");

        documentManager.clearCookies();
        documentManager.addCookie(new Cookie(cookieKey_sec, cookieValue_sec, cookie_login_expiration));
        documentManager.addCookie(new Cookie(cookieKey_logged_in, cookieValue_logged_in, cookie_login_expiration));
        documentManager.saveCookies();

        Logger.log(SectionName.COOKIE, "Finish");
    }

    private void retrieveStaff() throws IOException, URISyntaxException {
        Logger.log(SectionName.STAFF, "Begin");

        StaffScraper staffScraper = new StaffScraper();
        ArrayList<Staff> staffList = staffScraper.run(documentManager.getCookieList());

        documentManager.clearStaff();
        documentManager.addStaffList(staffList);
        documentManager.saveStaff();

        Logger.log(SectionName.STAFF, "Finish");
    }

    private void retrieveStories() throws URISyntaxException, IOException {
        Logger.log(SectionName.STORY, "Begin");

        StoryScraper storyScraper = new StoryScraper();
        ArrayList<Story> storyList = storyScraper.run(documentManager.getStaffList(), documentManager.getCookieList());

        documentManager.clearStories();
        documentManager.addStoryList(storyList);
        documentManager.saveStories();

        Logger.log(SectionName.STORY, "Finish");
    }

}