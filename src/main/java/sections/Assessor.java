package sections;

import cmonster.browsers.Browser;
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
import java.util.Set;

public class Assessor {
    private final DocumentManager documentManager = new DocumentManager();

    public Assessor() throws IOException, JDOMException {
    }

    public void run() throws IOException, URISyntaxException {
        retrieveCookies();
        if (Logger.askYesOrNo(SectionName.ASSESSOR, "Use cached staff?")) {
            if (!Logger.askYesOrNo(SectionName.ASSESSOR, "Use cached stories?")) {
                retrieveStories();
            }
        } else {
            retrieveStaff();
            retrieveStories();
        }

        Counter counter = new Counter(documentManager.getStaffList(), documentManager.getStoriesList());
        counter.run();
    }

    private void retrieveCookies() throws IOException {
        String cookieKey_sec = null, cookieValue_sec = null;
        String cookieKey_logged_in = null, cookieValue_logged_in = null;

        Logger.log(SectionName.COOKIE, "Make sure you have logged into The Torch within the past month on this computer");
        Browser browser = Logger.askBrowser(SectionName.COOKIE, "What browser do you use?");
        if (browser != null) {
            Set<cmonster.cookies.Cookie> cookies = browser.getCookiesForDomain("shsthetorch.com");
            for (cmonster.cookies.Cookie cookie : cookies) {
                String name = cookie.getName();
                if (name.contains("wordpress_sec_")) {
                    cookieKey_sec = name;
                    cookieValue_sec = cookie.getValue();
                } else if (name.contains("wordpress_logged_in_")) {
                    cookieKey_logged_in = name;
                    cookieValue_logged_in = cookie.getValue();
                }
            }
            if (
                    cookieKey_sec != null &&
                            cookieValue_sec != null &&
                            cookieKey_logged_in != null &&
                            cookieValue_logged_in != null
            ) {
                documentManager.clearCookies();
                documentManager.addCookie(new Cookie(cookieKey_sec, cookieValue_sec));
                documentManager.addCookie(new Cookie(cookieKey_logged_in, cookieValue_logged_in));
                documentManager.saveCookies();
            } else {
                Logger.log(SectionName.COOKIE, "Cookies not found in browser. Entering manual mode.");
                manualCookies();
            }
        } else {
            manualCookies();
        }
    }

    private void manualCookies() throws IOException {
        String cookieKey_sec = Logger.askString(SectionName.COOKIE, "Enter SEC cookie key");
        String cookieValue_sec = Logger.askString(SectionName.COOKIE, "Enter SEC cookie value");
        String cookieKey_logged_in = Logger.askString(SectionName.COOKIE, "Enter LOGGED_IN cookie key");
        String cookieValue_logged_in = Logger.askString(SectionName.COOKIE, "Enter LOGGED_IN cookie value");

        documentManager.clearCookies();
        documentManager.addCookie(new Cookie(cookieKey_sec, cookieValue_sec));
        documentManager.addCookie(new Cookie(cookieKey_logged_in, cookieValue_logged_in));
        documentManager.saveCookies();
    }

    private void retrieveStaff() throws IOException, URISyntaxException {
        StaffScraper staffScraper = new StaffScraper();
        ArrayList<Staff> staffList = staffScraper.run(documentManager.getCookieList());

        documentManager.clearStaff();
        documentManager.addStaffList(staffList);
        documentManager.saveStaff();
    }

    private void retrieveStories() throws URISyntaxException, IOException {
        StoryScraper storyScraper = new StoryScraper();
        ArrayList<Story> storyList = storyScraper.run(documentManager.getStaffList(), documentManager.getCookieList());

        documentManager.clearStories();
        documentManager.addStoryList(storyList);
        documentManager.saveStories();
    }

}