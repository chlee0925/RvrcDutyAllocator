import model.DutyRoster;
import model.DutySlot;
import model.ResidentAssistant;

import java.io.*;
import java.text.ParseException;
import java.util.*;

/**
 * Created by juholee on 2/8/17.
 */
public class Main {

    private static final String PATH_TO_DUTY_ROSTER_CONFIG = "/Users/juholee/Projects/RvrcDutyAllocator/src/config/duty_roster_config.txt";
    private static final String PATH_TO_RESIDENT_ASSISTANT_LIST = "/Users/juholee/Projects/RvrcDutyAllocator/src/config/resident_assistant_list.txt";

    public static void main(String[] args) throws IOException, ParseException {
        DutyRoster dutyRoster = readData();
        dutyRoster.attemptAllocDuties();
        System.out.println(dutyRoster.toString());
    }

    /**
     * Read from input or file
     *  1) Duty roster name
     *  2) Month settings (duty slots, public holidays, point group)
     */
    private static DutyRoster readData() throws ParseException, IOException {

        // Read duty roster config (month)
        BufferedReader br = new BufferedReader(new FileReader(new File(PATH_TO_DUTY_ROSTER_CONFIG)));
        String name = br.readLine();

        DutyRoster dutyRoster = new DutyRoster(name);

        String line;
        while ((line = br.readLine()) != null) {
            String[] data = line.trim().split(" ");
            int day = Integer.parseInt(data[0].trim());
            DutySlot.Meridiem meridiem = DutySlot.Meridiem.parseMeridiem(data[1].trim());
            DutySlot.PointGroup pointGroup = DutySlot.PointGroup.parsePointGroup(data[2].trim());
            dutyRoster.getDutySlots().add(new DutySlot(meridiem, day, pointGroup));
        }
        br.close();

        // Read resident assistant list
        br = new BufferedReader(new FileReader(new File(PATH_TO_RESIDENT_ASSISTANT_LIST)));
        while ((line = br.readLine()) != null) {
            String[] data = line.trim().split(" ");
            String raName = data[0].trim();
            float accumPoints = Float.parseFloat(data[1].trim());
            // extract block out dates
            Set<Integer> blockOutDates = new HashSet<>();
            for (int i = 2; i < data.length; i++) {
                blockOutDates.add(Integer.parseInt(data[i]));
            }
            dutyRoster.getResidentAssistants().add(new ResidentAssistant(raName, accumPoints, blockOutDates));
        }
        br.close();

        return dutyRoster;
    }

}