package model;

public class Shift {
    private int shiftID;
    private String shiftName;
    private String startTime;
    private String endTime;

    // Getters and Setters
    public int getShiftID() {
        return shiftID;
    }
    public void setShiftID(int shiftID) {
        this.shiftID = shiftID;
    }
    public String getShiftName() {
        return shiftName;
    }
    public void setShiftName(String shiftName) {
        this.shiftName = shiftName;
    }
    public String getStartTime() {
        return startTime;
    }
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }
    public String getEndTime() {
        return endTime;
    }
    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
