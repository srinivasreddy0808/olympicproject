package testcases;
import main.java.Event;
import main.java.Main;
import main.java.NocCode;

import java.util.*;

public class SystemTestDisplayConsecutiveWomen {

    public static void main(String[] args) {
        List<Event> events = Main.getEventData();
        List<NocCode> nocs = Main.getNocCodeData();

        Map<String, Set<Integer>> womenWonMap = new HashMap<>();

        for (Event event : events) {
            if ("f".equalsIgnoreCase(event.getSex())
                    && "gold".equalsIgnoreCase(event.getMedal())
                    && event.getYear() >= 1930 && event.getYear() <= 1980) {

                if (!womenWonMap.containsKey(event.getName())) {
                    womenWonMap.put(event.getName(), new TreeSet<>());
                }
                womenWonMap.get(event.getName()).add(event.getYear());
            }
        }

        boolean found = false;
        System.out.println("Female athletes who won gold in consecutive Olympic years:");

        for (Map.Entry<String, Set<Integer>> entry : womenWonMap.entrySet()) {
            String athlete = entry.getKey();
            List<Integer> years = new ArrayList<>(entry.getValue());

            for (int i = 1; i < years.size(); i++) {
                if (years.get(i) - years.get(i - 1) == 4) {
                    System.out.println("✓ " + athlete + " - " + years.get(i - 1) + " and " + years.get(i));
                    found = true;
                    break;
                }
            }
        }

        if (!found) {
            System.err.println("✗ No female athlete found with consecutive gold medals.");
            System.exit(1); // Mark as failure
        } else {
            System.out.println("Test passed.");
            System.exit(0); // Mark as success
        }
    }
}
