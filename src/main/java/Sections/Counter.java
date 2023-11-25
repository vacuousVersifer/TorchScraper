package Sections;

import Utils.Logger;
import Utils.SectionName;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public record Counter(ArrayList<String> staffs, ArrayList<String> stories) {
    private static final HashMap<String, HashMap<String, Integer>> tally = new HashMap<>();

    public void run() {
        Logger.log(SectionName.COUNTER, "Begin");
        for (String staff : staffs) {
            HashMap<String, Integer> staffTally = new HashMap<>();
            String[] staffParts = staff.split(":");
            String staffNumber = staffParts[0];
            String staffName = staffParts[1];

            for (String storyList : stories) {
                ArrayList<String> stories = new ArrayList<>(List.of(storyList.split(";")));
                String storyStaffNumber = stories.get(0);

                if (storyStaffNumber.equals(staffNumber)) {
                    stories.remove(0);

                    for (String story : stories) {
                        String[] parts = story.split(":");

                        String storyDate = parts[0];

                        staffTally.merge(storyDate, 1, Integer::sum);
                    }
                }
            }
            tally.put(staffName, staffTally);
        }

        printResults();
        Logger.log(SectionName.COUNTER, "Finish");
    }

    private void printResults() {
        Logger.log(SectionName.COUNTER_PRINT, "Begin");
        String[] headings = new String[]{"Name", "08/23", "09/23", "10/23", "11/23", "12/23", "01/24", "02/24", "03/24", "04/24", "05/24", "Total:"};

        staffs.add("Total:");
        HashMap<String, Integer> monthTally = new HashMap<>();

        StringBuilder header = new StringBuilder();
        for (String head : headings) {
            header.append(head).append("\t");
            monthTally.put(head, 0);
        }
        Logger.log(SectionName.SILENT, header.toString());


        for (String staff : staffs) {
            if (!staff.equals("Total:")) {
                String staffName = staff.split(":")[1];
                HashMap<String, Integer> staffStories = tally.get(staffName);

                StringBuilder row = new StringBuilder();

                int count = 0;
                for (String date : headings) {
                    String result = "0";
                    if (date.equals("Name")) {
                        String[] parts = staffName.split(" ");
                        String first = parts[0];
                        String lastI;
                        if (parts.length == 1) {
                            lastI = "X.";
                        } else {
                            lastI = parts[1].charAt(0) + ".";
                        }
                        result = first + " " + lastI;
                    } else if (date.equals("Total:")) {
                        result = String.valueOf(count);
                    } else if (staffStories.get(date) != null) {
                        result = staffStories.get(date).toString();
                        monthTally.put(date, monthTally.get(date) + Integer.parseInt(result));
                        count += Integer.parseInt(result);
                    }

                    row.append(result).append("\t");
                }
                Logger.log(SectionName.SILENT, row.toString());
            } else {
                StringBuilder monthRow = new StringBuilder();

                int count = 0;
                for (String date : headings) {
                    String result;
                    if (date.equals("Name")) {
                        result = "Total:";
                    } else if (date.equals("Total:")) {
                        result = String.valueOf(count);
                    } else {
                        result = monthTally.get(date).toString();
                        monthTally.put(date, monthTally.get(date) + Integer.parseInt(result));
                        count += Integer.parseInt(result);
                    }

                    monthRow.append(result).append("\t");
                }

                Logger.log(SectionName.SILENT, monthRow.toString());
            }
        }

        Logger.log(SectionName.COUNTER_PRINT, "Finish");
    }
}
