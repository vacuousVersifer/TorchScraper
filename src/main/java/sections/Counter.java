package sections;

import memory.types.Date;
import memory.types.Staff;
import memory.types.Story;
import utilities.Logger;
import utilities.SectionName;

import java.util.ArrayList;
import java.util.HashMap;

public class Counter {
    private final ArrayList<Staff> staffList;
    private final ArrayList<Story> storyList;
    private final HashMap<Integer, HashMap<String, Integer>> tally = new HashMap<>();
    //                    StaffID          Date    Tally

    public Counter(ArrayList<Staff> staffList, ArrayList<Story> storyList) {
        this.staffList = staffList;
        this.storyList = storyList;
    }

    public void run() {
        Logger.log(SectionName.COUNTER, "Begin");

        for (Staff staff : staffList) {
            tally.put(staff.getId(), new HashMap<>());
        }

        for (Story story : storyList) {
            HashMap<String, Integer> staffTally = tally.get(story.getId());
            Date storyDate = story.getDate();
            staffTally.merge(storyDate.toString(), 1, Integer::sum);
        }

        printResults();
        Logger.log(SectionName.COUNTER, "Finish");
    }

    private void printResults() {
        Logger.log(SectionName.COUNTER_PRINT, "Begin");

        // Create Header Dates
        int[] headersMonth = new int[]{8, 9, 10, 11, 12, 1, 2, 3, 4, 5};
        int[] headersYear = new int[]{23, 23, 23, 23, 23, 24, 24, 24, 24, 24};
        int length = headersMonth.length;
        Date[] headers = new Date[length];
        for (int i = 0; i < length; i++) {
            headers[i] = new Date(headersMonth[i], headersYear[i]);
        }

        // Constructs header row
        StringBuilder headerBuilder = new StringBuilder();
        headerBuilder.append("Name").append("\t");
        for (Date date : headers) {
            headerBuilder.append(date.toString()).append("\t");
        }
        headerBuilder.append("Total:").append("\t");
        Logger.log(SectionName.SILENT, headerBuilder.toString());

        HashMap<Date, Integer> monthTally = new HashMap<>();
        for (Staff staff : staffList) {
            StringBuilder rowBuilder = new StringBuilder();
            HashMap<String, Integer> staffStoryList = tally.get(staff.getId());

            rowBuilder.append(staff.getName().getFull()).append("\t");

            int totalStaffStories = 0;
            for (Date date : headers) {
                Integer stories = staffStoryList.get(date.toString());
                if (stories == null) stories = 0;
                rowBuilder.append(stories).append("\t");
                totalStaffStories += stories;
                monthTally.merge(date, stories, Integer::sum);
            }

            rowBuilder.append(totalStaffStories).append("\t");
            Logger.log(SectionName.SILENT, rowBuilder.toString());
        }

        StringBuilder monthRow = new StringBuilder();
        int totalStories = 0;

        monthRow.append("Total:").append("\t");
        for (Date date : headers) {
            int totalMonthStories = monthTally.get(date);
            monthRow.append(totalMonthStories).append("\t");
            totalStories += totalMonthStories;
        }
        monthRow.append(totalStories).append("\t");
        Logger.log(SectionName.SILENT, monthRow.toString());


        Logger.log(SectionName.COUNTER_PRINT, "Finish");
    }
}
