package sections;

import memory.types.Staff;
import utilities.Logger;
import utilities.SectionName;

import java.io.IOException;
import java.io.InputStream;
import java.net.*;
import java.util.ArrayList;
import java.util.Scanner;

import static java.nio.charset.StandardCharsets.UTF_8;

public class StaffScraper {
    private final ArrayList<Staff> staffList = new ArrayList<>();
    private String cookies;

    public ArrayList<Staff> run(String cookies) throws IOException, URISyntaxException {
        Logger.log(SectionName.STAFF_SCRAPER, "Begin");

        int startID = Logger.askNumber(SectionName.STAFF_SCRAPER, "Enter starting staff ID (117)");
        int endID = Logger.askNumber(SectionName.STAFF_SCRAPER, "Enter ending staff ID (159)");

        this.cookies = cookies;

        for (int staffID = startID; staffID <= endID; staffID++) {
            Staff staff = scrape(staffID);
            if (!(staff == null)) staffList.add(staff);
        }

        Logger.log(SectionName.STAFF_SCRAPER, "Finish");
        return staffList;
    }

    private Staff scrape(int staffID) throws IOException, URISyntaxException {
        Logger.log(SectionName.STAFF_SCRAPER, "Scraping Staff #" + staffID, false);
        String baseURL = "https://shsthetorch.com/wp-admin/edit.php";
        String query = "author=" + URLEncoder.encode(String.valueOf(staffID), UTF_8);
        URL url = new URI(baseURL + "?" + query).toURL();

        String staffName;

        Scanner scanner = getScanner(url);
        String responseBody = scanner.useDelimiter("\\A").next();

        String tell = "No posts found.";
        if (!responseBody.contains(tell)) {
            String sep = "author=" + staffID + "\">";
            int sepPos = responseBody.indexOf(sep);
            staffName = responseBody.substring(sepPos + sep.length()).split("<", 2)[0];
            Logger.log(SectionName.SILENT, ": " + staffName);
            return new Staff(staffID, staffName);
        } else {
            Logger.log(SectionName.SILENT, ": No one found");
            return null;
        }
    }

    private Scanner getScanner(URL url) throws IOException {
        URLConnection connection = url.openConnection();
        connection.setRequestProperty("Accept-Charset", UTF_8.name());
        connection.setRequestProperty("Cookie", cookies);
        InputStream response = connection.getInputStream();
        return new Scanner(response);
    }
}