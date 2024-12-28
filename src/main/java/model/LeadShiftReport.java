package model;

import java.time.LocalDate;

public class LeadShiftReport {
    private int id;
    private String technicianName;
    private LocalDate shiftDate;
    private String shift;
    private String reportContent;
    private boolean signOff;

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTechnicianName() { return technicianName; }
    public void setTechnicianName(String technicianName) { this.technicianName = technicianName; }

    public LocalDate getShiftDate() { return shiftDate; }
    public void setShiftDate(LocalDate shiftDate) { this.shiftDate = shiftDate; }

    public String getShift() { return shift; }
    public void setShift(String shift) { this.shift = shift; }

    public String getReportContent() { return reportContent; }
    public void setReportContent(String reportContent) { this.reportContent = reportContent; }

    public boolean isSignOff() { return signOff; }
    public void setSignOff(boolean signOff) { this.signOff = signOff; }
}
