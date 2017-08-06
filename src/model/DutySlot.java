package model;

import java.text.ParseException;

/**
 * Created by juholee on 2/8/17.
 */
public class DutySlot {

    public enum PointGroup {
        GROUP_A("A", 4f),
        GROUP_B("B", 3f),
        GROUP_C("C", 2f);

        public final String group;
        public final float points;

        PointGroup(String group, float points) {
            this.group = group;
            this.points = points;
        }

        public static PointGroup parsePointGroup(String string) throws ParseException {
            if (string.equalsIgnoreCase(GROUP_A.group)) return GROUP_A;
            else if (string.equalsIgnoreCase(GROUP_B.group)) return GROUP_B;
            else if (string.equalsIgnoreCase(GROUP_C.group)) return GROUP_C;
            else throw new ParseException("Cannot parse point group", 0);
        }
    }

    public enum Meridiem {
        AM("am"),
        PM("pm");

        public final String meridiem;

        Meridiem(String meridiem) {
            this.meridiem = meridiem;
        }

        public static Meridiem parseMeridiem(String string) throws ParseException {
            if (string.equalsIgnoreCase(AM.meridiem)) return AM;
            else if (string.equalsIgnoreCase(PM.meridiem)) return PM;
            else throw new ParseException("Cannot parse meridiem", 0);
        }
    }

    private Meridiem meridiem; // AM or PM
    private int day;
    private PointGroup pointGroup;
    private ResidentAssistant residentAssistant;

    public DutySlot(Meridiem meridiem, int day, PointGroup pointGroup) {
        this.meridiem = meridiem;
        this.day = day;
        this.pointGroup = pointGroup;
    }

    public boolean isFilled() {
        return residentAssistant != null;
    }

    @Override
    public String toString() {
        String residentAssistant = this.isFilled()? this.residentAssistant.getName() : "No RA assigned";
        return "Day: " + this.day + " " + this.meridiem.meridiem
                + " Group: " + this.pointGroup.group + " (" + this.pointGroup.points + " points)"
                + " RA: " + residentAssistant;
    }

    //////////////////////
    // getters and setters
    //////////////////////

    public PointGroup getPointGroup() {
        return pointGroup;
    }

    public void setPointGroup(PointGroup pointGroup) {
        this.pointGroup = pointGroup;
    }

    public ResidentAssistant getResidentAssistant() {
        return residentAssistant;
    }

    public void setResidentAssistant(ResidentAssistant residentAssistant) {
        this.residentAssistant = residentAssistant;
    }

    public Meridiem getMeridiem() {
        return meridiem;
    }

    public void setMeridiem(Meridiem meridiem) {
        this.meridiem = meridiem;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }
}
