package Sections;

import Utils.Logger;
import Utils.SectionName;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Assessor {
    private final boolean retrieveStaff;
    private final String staffFilePath = "staff.txt";
    private final File staffFile = new File(staffFilePath);

    private final boolean retrieveStories;
    private final String storyFilePath = "stories.txt";
    private final File storyFile = new File(storyFilePath);

    private final boolean retrieveCookies;
    private final String cookieFilePath = "cookies.txt";
    private final File cookieFile = new File(cookieFilePath);

    private final Scanner scanner = new Scanner(System.in);
    private int startID, endID;

    public Assessor() throws IOException {
        Logger.log(SectionName.CONSTRUCTOR, "Begin Construction");

        if (staffFile.createNewFile()) {
            Logger.log(SectionName.CONSTRUCTOR, "Staff file created");
        }
        if (storyFile.createNewFile()) {
            Logger.log(SectionName.CONSTRUCTOR, "Story file created");
        }
        if (cookieFile.createNewFile()) {
            Logger.log(SectionName.CONSTRUCTOR, "Cookie file created");
        }

        retrieveStaff = askStaff();
        if (retrieveStaff) {
            retrieveCookies = askCookies();
            retrieveStories = true;
        } else {
            retrieveStories = askStories();
            if (retrieveStories) {
                retrieveCookies = askCookies();
            } else {
                retrieveCookies = false;
            }
        }

        Logger.log(SectionName.CONSTRUCTOR, "Finish Construction");
    }

    public boolean askStaff() {
        Logger.log(SectionName.CONSTRUCTOR, "Use cached staff? (Y/N): ", false);
        String staffResponse = scanner.nextLine();
        if (staffResponse.equals("Y")) {
            return false;
        } else if (staffResponse.equals("N")) {
            Logger.log(SectionName.CONSTRUCTOR, "Enter starting ID (117): ", false);
            String startIDResponse = scanner.nextLine();
            startID = Integer.parseInt(startIDResponse);

            Logger.log(SectionName.CONSTRUCTOR, "Enter ending ID (159): ", false);
            String endIDResponse = scanner.nextLine();
            endID = Integer.parseInt(endIDResponse);

            return true;
        } else {
            Logger.log(SectionName.CONSTRUCTOR, "Invalid response. Defaulting to yes");
            return false;
        }
    }

    public boolean askStories() {
        Logger.log(SectionName.CONSTRUCTOR, "Use cached stories? (Y/N): ", false);
        String storyResponse = scanner.nextLine();
        if (storyResponse.equals("Y")) {
            return false;
        } else if (storyResponse.equals("N")) {
            return true;
        } else {
            Logger.log(SectionName.CONSTRUCTOR, "Invalid response. Defaulting to yes");
            return false;
        }
    }

    public boolean askCookies() {
        Logger.log(SectionName.CONSTRUCTOR, "Use cached cookies? (Y/N): ", false);
        String cookieResponse = scanner.nextLine();
        if (cookieResponse.equals("Y")) {
            return false;
        } else if (cookieResponse.equals("N")) {
            return true;
        } else {
            Logger.log(SectionName.CONSTRUCTOR, "Invalid response. Defaulting to yes");
            return false;
        }
    }

    public void run() throws IOException, URISyntaxException {
        Logger.log(SectionName.ASSESSOR, "Begin");

        String cookieList = cookieRetrieve();
        ArrayList<String> staffList = staffRetrieve(cookieList);
        ArrayList<String> storyList = storyRetrieve(cookieList, staffList);


        Counter counter = new Counter(staffList, storyList);
        counter.run();

        Logger.log(SectionName.ASSESSOR, "Finish");
    }

    private String cookieRetrieve() throws IOException {
        Logger.log(SectionName.COOKIE, "Begin");

        StringBuilder cookieListBuilder = new StringBuilder();
        String cookieList;
        if (retrieveCookies) {
            StringBuilder cookieFileBuilder = new StringBuilder();
            String cookieFileList;

            Logger.log(SectionName.COOKIE, "Enter SEC cookie: ", false);
            String cookieKey_sec = "wordpress_sec_23ca6f26f7da56ef1f9ff73af2effc58=";
            String cookieValue_sec = scanner.nextLine();
            cookieListBuilder.append(cookieKey_sec).append((cookieValue_sec)).append("; ");
            cookieFileBuilder.append("SEC: ").append(cookieKey_sec).append((cookieValue_sec)).append("\n");

            Logger.log(SectionName.COOKIE, "Enter LOGGED_IN cookie: ", false);
            String cookieKey_logged_in = "wordpress_logged_in_23ca6f26f7da56ef1f9ff73af2effc58=";
            String cookieValue_logged_in = scanner.nextLine();
            cookieListBuilder.append(cookieKey_logged_in).append((cookieValue_logged_in)).append("; ");
            cookieFileBuilder.append("LOGGED_IN: ").append(cookieKey_logged_in).append((cookieValue_logged_in)).append("\n");

            Logger.log(SectionName.COOKIE, "Enter login cookies expiration date (DD MMM YYYY): ", false);
            String cookieValue_login_expiration = scanner.nextLine();
            cookieFileBuilder.append("EXPIRATION: ").append(cookieValue_login_expiration);

            cookieList = cookieListBuilder.toString();
            cookieFileList = cookieFileBuilder.toString();

            FileWriter cookieFileWriter = new FileWriter(cookieFile);
            cookieFileWriter.write(cookieFileList);
            cookieFileWriter.close();
        } else {
            Scanner cookieScanner = new Scanner(cookieFile);

            while (cookieScanner.hasNextLine()) {
                String cookie = cookieScanner.nextLine();

                String[] cookieParts = cookie.split(": ");
                String cookieKey = cookieParts[0];
                String cookieValue = cookieParts[1];

                if (cookieKey.equals("SEC") || cookieKey.equals("LOGGED_IN")) {
                    cookieListBuilder.append(cookieValue).append("; ");
                }
            }
            cookieList = cookieListBuilder.toString();
        }

        Logger.log(SectionName.COOKIE, "Finish");
        return cookieList;
    }

    private ArrayList<String> staffRetrieve(String cookieList) throws IOException, URISyntaxException {
        Logger.log(SectionName.STAFF, "Begin");

        ArrayList<String> staffList;
        if (retrieveStaff) {
            StaffScraper staffScraper = new StaffScraper(startID, endID);
            staffList = staffScraper.run(cookieList);
        } else {
            Scanner staffScanner = new Scanner(staffFile);
            staffList = new ArrayList<>(List.of(staffScanner.useDelimiter("\\A").next().split("\n")));
        }

        FileWriter staffFileWriter = new FileWriter(staffFile);
        for (String staff : staffList) {
            staffFileWriter.write(staff + "\n");
        }
        staffFileWriter.close();

        Logger.log(SectionName.STAFF, "Finish");
        return staffList;
    }

    private ArrayList<String> storyRetrieve(String cookieList, ArrayList<String> staffList) throws URISyntaxException, IOException {
        Logger.log(SectionName.STORY, "Begin");
        ArrayList<String> storyList;
        if (retrieveStories) {
            StoryScraper storyScraper = new StoryScraper();
            storyList = storyScraper.run(staffList, cookieList);
        } else {
            Scanner storyScanner = new Scanner(storyFile);
            storyList = new ArrayList<>(List.of(storyScanner.useDelimiter("\\A").next().split("\n")));
        }

        FileWriter storyFileWriter = new FileWriter(storyFile);
        for (String story : storyList) {
            storyFileWriter.write(story + "\n");
        }
        storyFileWriter.close();

        Logger.log(SectionName.STORY, "Finish");
        return storyList;
    }

}