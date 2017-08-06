package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by juholee on 2/8/17.
 */
public class DutyRoster {

    private String name;
    private List<DutySlot> dutySlots = new ArrayList<>();
    private List<ResidentAssistant> residentAssistants = new ArrayList<>();

    public DutyRoster(String name){
        this.name = name;
    }

    public void attemptAllocDuties() {
        // Handle different duty groups separately
        List<DutySlot> dutySlotsGroupA = new ArrayList<>();
        List<DutySlot> dutySlotsGroupB = new ArrayList<>();
        List<DutySlot> dutySlotsGroupC = new ArrayList<>();

        dutySlots.forEach(dutySlot -> {
            switch(dutySlot.getPointGroup()) {
                case GROUP_A: dutySlotsGroupA.add(dutySlot); break;
                case GROUP_B: dutySlotsGroupB.add(dutySlot); break;
                case GROUP_C: dutySlotsGroupC.add(dutySlot); break;
            }
        });

        allocDutiesForDutyGroup(dutySlotsGroupA);
        allocDutiesForDutyGroup(dutySlotsGroupB);
        allocDutiesForDutyGroup(dutySlotsGroupC);
    }

    /**
     * @return reporting of the duty roster
     */
    @Override
    public String toString() {
        String output = "";
        output += this.name;

        // Report duty slots
        output += "\n\n==========DUTY SLOTS==========";
        for (DutySlot dutySlot : this.dutySlots) {
            output += "\n\t" + dutySlot.toString();
        }

        // Report RA status
        output += "\n\n==========RA STATUS==========";
        for (ResidentAssistant residentAssistant : this.residentAssistants) {
            output += "\n\t" + residentAssistant.toString();
        }

        return output;
    }

    // Group A, B, C
    private void allocDutiesForDutyGroup(List<DutySlot> dutySlotList) {
        // Shuffle the list (to a pick random duty slot in duty group)
        Collections.shuffle(dutySlotList);

        List<ResidentAssistant> availableRaList = new ArrayList<>();
        for (DutySlot dutySlot : dutySlotList) {
            // Find the available people
            this.residentAssistants.forEach(residentAssistant -> {
                if (residentAssistant.isAvailable(dutySlot.getDay())) availableRaList.add(residentAssistant);
            });

            if (availableRaList.isEmpty()) continue; // couldn't find any available RA

            // Find best candidate (lowest points)
            Collections.sort(availableRaList, (ResidentAssistant o1, ResidentAssistant o2) -> {
                float diff = o1.getAccumPoints() - o2.getAccumPoints();
                if (diff > 0) return 1;
                else if (diff < 0) return -1;
                else return 0;
            });

            ResidentAssistant allocatedRa = availableRaList.get(0);  // Choose RA with lowest points
            dutySlot.setResidentAssistant(allocatedRa);
            allocatedRa.setAccumPoints(allocatedRa.getAccumPoints() + dutySlot.getPointGroup().points);
        }
    }

    //////////////////////
    // getters and setters
    //////////////////////

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<DutySlot> getDutySlots() {
        return dutySlots;
    }

    public void setDutySlots(List<DutySlot> dutySlots) {
        this.dutySlots = dutySlots;
    }

    public List<ResidentAssistant> getResidentAssistants() {
        return residentAssistants;
    }

    public void setResidentAssistants(List<ResidentAssistant> residentAssistants) {
        this.residentAssistants = residentAssistants;
    }
}
