package model;

import java.time.LocalDateTime;

public class ReportEntry {
    private int dailyReportID;
    private LocalDateTime dailyReportDate;
    private String oncomingLead;
    private boolean passdownAccepted;
    private String createdBy;
    private int shiftID; // references Shift

    // Getters & Setters
}
