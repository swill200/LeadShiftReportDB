package controller;

import view.DateSelectionView;
import view.PreviousReportsView;
import model.LeadShiftReport;
import model.LeadShiftReportDAO;

import javax.swing.*;
import java.time.LocalDate;
import java.util.List;

public class DateSelectionController {
    private DateSelectionView dateView;
    private LeadShiftReportDAO dao;

    public DateSelectionController(DateSelectionView dateView, LeadShiftReportDAO dao) {
        this.dateView = dateView;
        this.dao = dao;
    }

    public void handleSearch(String dateStr, String shift) {
        try {
            LocalDate date = LocalDate.parse(dateStr);
            List<LeadShiftReport> reports = dao.getReportsByDateAndShift(date, shift);

            PreviousReportsView prevView = new PreviousReportsView();
            PreviousReportsController prevController = new PreviousReportsController(prevView);
            prevView.setController(prevController);

            if (reports.isEmpty()) {
                prevView.setReportsText("No reports found for this date and shift.");
            } else {
                StringBuilder sb = new StringBuilder();
                for (LeadShiftReport r : reports) {
                    sb.append("ID: ").append(r.getId()).append("\n")
                            .append("Technician: ").append(r.getTechnicianName()).append("\n")
                            .append("Date: ").append(r.getShiftDate()).append("\n")
                            .append("Shift: ").append(r.getShift()).append("\n")
                            .append("Content:\n").append(r.getReportContent()).append("\n")
                            .append("Sign-Off: ").append(r.isSignOff()).append("\n")
                            .append("-------------------------\n");
                }
                prevView.setReportsText(sb.toString());
            }

            prevView.open();
            dateView.dispose();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(dateView, "Invalid date format. Use YYYY-MM-DD.");
        }
    }
}
