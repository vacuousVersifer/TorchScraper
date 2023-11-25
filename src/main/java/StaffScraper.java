import java.io.IOException;
import java.io.InputStream;
import java.net.*;
import java.util.ArrayList;
import java.util.Scanner;

import static java.nio.charset.StandardCharsets.UTF_8;

public class StaffScraper {
    private final int startID, endID;
    private final ArrayList<String> staffList = new ArrayList<>();
    private String cookies;

    public StaffScraper(int startID, int endID) {
        this.startID = startID;
        this.endID = endID;
    }

    public ArrayList<String> run(String cookies) throws IOException, URISyntaxException {
        Logger.log(SectionName.STAFF_SCRAPER, "Begin");

        this.cookies = cookies;

        for (int staffID = startID; staffID <= endID; staffID++) {
            String staff = scrape(staffID);
            if (!staff.isEmpty()) staffList.add(staff);
        }

        Logger.log(SectionName.STAFF_SCRAPER, "Finish");
        return staffList;
    }

    private String scrape(int staffID) throws IOException, URISyntaxException {
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
            return staffID + ":" + staffName;
        } else {
            Logger.log(SectionName.SILENT, ": No one found");
            return "";
        }
    }

    private Scanner getScanner(URL url) throws IOException {
        URLConnection connection = getUrlConnection(url);

        InputStream response = connection.getInputStream();
        return new Scanner(response);
    }

    private URLConnection getUrlConnection(URL url) throws IOException {
        URLConnection connection = url.openConnection();
        connection.setRequestProperty("Accept-Charset", UTF_8.name());
        connection.setRequestProperty("Cookie", cookies);
        return connection;
    }
}