package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class LeadShiftReportDAO {
    // For demo: store reports in memory
    private static final List<LeadShiftReport> REPORTS = new ArrayList<>();

    public void saveReport(LeadShiftReport report) {
        // In a real application, insert into a database
        report.setId(REPORTS.size() + 1);
        REPORTS.add(report);
    }

    public List<LeadShiftReport> getReportsByDateAndShift(LocalDate date, String shift) {
        // Filter the in-memory list in this demo
        List<LeadShiftReport> result = new ArrayList<>();
        for (LeadShiftReport r : REPORTS) {
            if (r.getShiftDate().equals(date) && r.getShift().equalsIgnoreCase(shift)) {
                result.add(r);
            }
        }
        return result;
    }

    // Additional methods like update, delete, etc., can be added as needed.
}
