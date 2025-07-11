import javax.swing.*;
import java.io.*;
import java.net.Inet4Address;
import java.util.*;

public class Main {
    // Static final column indexes for Event data
    private static final int ID = 0;
    private static final int NAME = 1;
    private static final int SEX = 2;
    private static final int AGE = 3;
    private static final int HEIGHT = 4;
    private static final int WEIGHT = 5;
    private static final int TEAM = 6;
    private static final int NOC = 7;
    private static final int GAMES = 8;
    private static final int YEAR = 9;
    private static final int SEASON = 10;
    private static final int CITY = 11;
    private static final int SPORT = 12;
    private static final int EVENT = 13;
    private static final int MEDAL = 14;

    // Static final column indexes for NOC data
    private static final int NOC_CODE = 0;
    private static final int REGION = 1;
    private static final int NOTES = 2;

    public static void main(String[] args) {
        List<Event> events = getEventData();
        List<NocCode> nocs = getNocCodeData();

   //    displayYearWiseNumberOfGoldMedalsWonByEachPlayer(events,nocs);
//       displayAthletesWhoWonGoldMedalIn1980AndAgeIsLessThan30Years(events,nocs);
//       eventWiseNumberOfGoldSilverBronzeMedalsInYear1980(events ,nocs);
//       displayGoldWinnerOfFootballOfEveryOlympic(events,nocs);
//       femaleAthleteWonMaximumNumberOfGoldAllOlympics(events,nocs);
//       nameOfAthleteParticipatedInMoreThanThreeOlympics(events,nocs);
        displayTheWomenWonConsecutively(events,nocs);



    }



    public static void displayTheWomenWonConsecutively(List<Event> events, List<NocCode> nocs) {

        Map<String, Set<Integer>> womenWonMap = new HashMap<>();

        for (Event event : events) {
            if ("f".equalsIgnoreCase(event.getSex()) && "gold".equalsIgnoreCase(event.getMedal()) && event.getYear() >= 1930 && event.getYear() <= 1980) {
                womenWonMap
                        .computeIfAbsent(event.getName(), k -> new TreeSet<>())
                        .add(event.getYear());
            }
        }

        System.out.println("Female athletes who won gold in consecutive Olympic years:");

        for (Map.Entry<String, Set<Integer>> entry : womenWonMap.entrySet()) {
            String athlete = entry.getKey();
            List<Integer> sortedYears = new ArrayList<>(entry.getValue());
            Collections.sort(sortedYears);

            for (int i = 1; i < sortedYears.size(); i++) {
                if (sortedYears.get(i) - sortedYears.get(i - 1) == 4) {
                    System.out.println(athlete + " - " + sortedYears.get(i - 1) + " and " + sortedYears.get(i));
                    break;
                }
            }
        }
    }






    public static void nameOfAthleteParticipatedInMoreThanThreeOlympics(List<Event> events, List<NocCode> nocs) {
        Map<String, Set<String>> athleteGamesMap = new HashMap<>();

        for (Event event : events) {
            String athleteName = event.getName();
            String gameEdition = event.getGames();

            athleteGamesMap
                    .computeIfAbsent(athleteName, k -> new HashSet<>())
                    .add(gameEdition);
        }

        for (Map.Entry<String, Set<String>> entry : athleteGamesMap.entrySet()) {
            if (entry.getValue().size() > 3) {
                System.out.println("Name: " + entry.getKey() + " | Olympics Participated: " + entry.getValue().size());
            }
        }
    }



    public static void  femaleAthleteWonMaximumNumberOfGoldAllOlympics(List<Event> events,List<NocCode> nocs) {
        Map<String ,Integer> femaleAthleteMax = new HashMap<>();
        for(Event event : events) {
            if(event.getMedal().equalsIgnoreCase("gold") && event.getSex().equalsIgnoreCase("f") ) {
                femaleAthleteMax.put(event.getName(),femaleAthleteMax.getOrDefault(event.getName(),0)+1);
            }
        }
        List<Map.Entry<String,Integer>> l= new ArrayList<>(femaleAthleteMax.entrySet());
        Collections.sort(l,Map.Entry.comparingByValue());
        for(Map.Entry<String,Integer> e: l) {
            System.out.println("name  "+e.getKey()+"  total wins"+e.getValue());
        }
    }


    public static void displayGoldWinnerOfFootballOfEveryOlympic(List<Event> events,List<NocCode> nocs) {
        Map<String , String>  footBallOlympicWinner = new HashMap<>();
        for(Event event: events) {
            if(event.getSport().equalsIgnoreCase("football") && event.getMedal().equalsIgnoreCase("gold")) {
                footBallOlympicWinner.put(event.getGames(),event.getNoc());

            }
        }

        for(Map.Entry<String,String> m :footBallOlympicWinner.entrySet()) {
            System.out.println("football event  "+m.getKey()+"  country  "+m.getValue());
        }
    }


    public  static  void eventWiseNumberOfGoldSilverBronzeMedalsInYear1980(List<Event> events ,List<NocCode> nocs) {
        Map<String, ArrayList<Integer>> eventMedals = new HashMap<>();
        final int GOLD = 0, SILVER = 1, BRONZE = 2;
        for (Event event : events) {
            if(event.getYear() == 1980) {
                int medalIndex =-1;
                switch (event.getMedal().toLowerCase().trim()) {
                    case "gold": medalIndex = GOLD; break;
                    case "silver": medalIndex = SILVER; break;
                    case "bronze": medalIndex = BRONZE; break;
                }
                if(medalIndex !=-1) {
                    ArrayList<Integer> medalsList = eventMedals.computeIfAbsent(event.getEvent(),k -> new ArrayList<>(Arrays.asList(0, 0, 0)));
                    medalsList.set(medalIndex,medalsList.get(medalIndex)+1);
                }


            }
        }

        for(Map.Entry<String ,ArrayList<Integer>>  e : eventMedals.entrySet()) {
            System.out.println("event "+e.getKey() + " gold silver bronze "+ e.getValue().get(0)+" "+e.getValue().get(1)+" "+e.getValue().get(2));
        }
    }



    public  static  void  displayAthletesWhoWonGoldMedalIn1980AndAgeIsLessThan30Years(List<Event> events , List<NocCode> nocs) {
        HashSet<String> athletes = new HashSet<>();
        for(Event event : events) {
            if(event.getYear() == 1980 && event.getAge() <30 && event.getMedal().equalsIgnoreCase("gold")) {
                athletes.add(event.getName());

            }
        }
        for(String athlete : athletes) {
            System.out.println(athlete);
        }

        System.out.println(athletes.size()+ "total members ");


    }




    public  static void displayYearWiseNumberOfGoldMedalsWonByEachPlayer(List<Event> events ,List<NocCode> nocs) {
        Map<Integer ,Map <String ,Integer> > yearWiseGoldMedalWon = new HashMap<>();

        for(Event event : events) {
            if("gold".equalsIgnoreCase(event.getMedal())) {
                Map<String,Integer>  playerGoldMedalsCount = yearWiseGoldMedalWon.getOrDefault(event.getYear(),new HashMap<>());
                playerGoldMedalsCount.put(event.getName(),playerGoldMedalsCount.getOrDefault(event.getName(),0)+1);
                yearWiseGoldMedalWon.put(event.getYear(),playerGoldMedalsCount);

            }
        }

        List<Map.Entry<Integer,Map<String,Integer>>> yearWiseGoldMedalWonList = new ArrayList<>(yearWiseGoldMedalWon.entrySet());
        Collections.sort(yearWiseGoldMedalWonList,Map.Entry.comparingByKey());
        for(Map.Entry<Integer,Map<String,Integer>> year : yearWiseGoldMedalWonList) {
            Map<String,Integer>  playerWiseGoldMedalsWon = year.getValue();
            for(Map.Entry<String,Integer> player : playerWiseGoldMedalsWon.entrySet()) {
                System.out.println("year " + year.getKey()+ "  player name "+ player.getKey()+" gold medals Won "+player.getValue());
            }
        }


    }
    public  static void displayYearWiseNumberOfGoldMedalsWonByEachPlayerIfOnlyWon(List<Event> events ,List<NocCode> nocs) {
        Map<Integer, Map<String, Integer>> goldMedalsByYear = new TreeMap<>();

        for (Event event : events) {
            if ("Gold".equalsIgnoreCase(event.getMedal())) {
                int year = event.getYear();
                String name = event.getName();

                Map<String, Integer> athleteMedals = goldMedalsByYear.getOrDefault(year, new HashMap<>());
                athleteMedals.put(name, athleteMedals.getOrDefault(name, 0) + 1);
                goldMedalsByYear.put(year, athleteMedals);
            }
        }
        for (Map.Entry<Integer, Map<String, Integer>> entry : goldMedalsByYear.entrySet()) {
            int year = entry.getKey();
            Map<String, Integer> athletesInYear = entry.getValue();

            System.out.println("Year: " + year);
            for (Map.Entry<String, Integer> athleteEntry : athletesInYear.entrySet()) {
                System.out.println(" - " + athleteEntry.getKey() + ": " + athleteEntry.getValue() + " Gold Medal(s)");
            }
            System.out.println();
        }

    }


    public static String removeCommasInsideQuotes(String line) {
        StringBuilder sb = new StringBuilder();
        boolean inQuotes = false;
        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            if (c == '"') {
                inQuotes = !inQuotes;
            }
            if (c == ',' && inQuotes) {
                continue; // skip comma inside quotes
            }
            sb.append(c);
        }
        return sb.toString();
    }

    public static List<Event> getEventData() {
        String filePath = "resources/athlete_events.csv"; // Hardcoded file name
        List<Event> events = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            br.readLine(); // Skip header
            String line;
            while ((line = br.readLine()) != null) {
                line = removeCommasInsideQuotes(line);
                String[] data = line.split(",", -1); // Preserve empty fields

                // for removing the quotes .
                for (int i = 0; i < data.length; i++) {
                    data[i] = data[i].replaceAll("^\"|\"$", "");
                }

                Event event = new Event();
                event.setId(Integer.valueOf(data[ID]));
                event.setName(data[NAME]);
                event.setSex(data[SEX]);
                event.setAge((data[AGE].isEmpty() || data[AGE].equals("NA")) ? -1 : Integer.parseInt(data[AGE]));
                event.setHeight((data[HEIGHT].isEmpty() || data[HEIGHT].equals("NA")) ? -1 : Double.parseDouble(data[HEIGHT]));
                event.setWeight((data[WEIGHT].isEmpty() || data[WEIGHT].equals("NA")) ? -1 : Double.parseDouble(data[WEIGHT]));
                event.setTeam(data[TEAM]);
                event.setNoc(data[NOC]);
                event.setGames(data[GAMES]);
                event.setYear(Integer.valueOf(data[YEAR]));
                event.setSeason(data[SEASON]);
                event.setCity(data[CITY]);
                event.setSport(data[SPORT]);
                event.setEvent(data[EVENT]);
                event.setMedal(data.length > MEDAL ? data[MEDAL] : "");

                events.add(event);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return events;
    }

    public static List<NocCode> getNocCodeData() {
        String filePath = "resources/noc_regions.csv"; // Hardcoded file name
        List<NocCode> nocCodes = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            br.readLine(); // Skip header
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",", -1); // Preserve empty fields

                NocCode noc = new NocCode();
                noc.setNoc(data[NOC_CODE]);
                noc.setRegion(data[REGION]);
                noc.setNotes(data.length > NOTES ? data[NOTES] : "");

                nocCodes.add(noc);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return nocCodes;
    }
}
