package model;

import java.util.Set;

/**
 * Created by juholee on 2/8/17.
 */
public class ResidentAssistant {
    private String name;
    private float accumPoints;
    private Set<Integer> blockOutDays;

    public ResidentAssistant(String name, float accumPoints, Set<Integer> blockOutDays) {
        this.name = name;
        this.accumPoints = accumPoints;
        this.blockOutDays = blockOutDays;
    }

    @Override
    public String toString() {
        String blockOutDays = "";
        for (Integer day : this.blockOutDays) {
            blockOutDays += day + " ";
        }
        return "Name: " + this.name + " Points: " + this.accumPoints + " Block-out dates: " + blockOutDays;
    }

    public boolean isAvailable(int day) {
        return !blockOutDays.contains(day);
    }

    /////////////////////
    //getters and setters
    /////////////////////

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getAccumPoints() {
        return accumPoints;
    }

    public void setAccumPoints(float accumPoints) {
        this.accumPoints = accumPoints;
    }

    public Set<Integer> getBlockOutDays() {
        return blockOutDays;
    }

    public void setBlockOutDays(Set<Integer> blockOutDays) {
        this.blockOutDays = blockOutDays;
    }
}
