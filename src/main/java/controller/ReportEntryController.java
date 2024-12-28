package controller;

import model.LeadShiftReport;
import model.LeadShiftReportDAO;
import view.ReportEntryView;

import javax.swing.*;
import java.time.LocalDate;

public class ReportEntryController {
    private ReportEntryView entryView;
    private LeadShiftReportDAO dao;

    public ReportEntryController(ReportEntryView entryView, LeadShiftReportDAO dao) {
        this.entryView = entryView;
        this.dao = dao;
    }

    public void handleSubmit(String techName, String dateStr, String shift, String content) {
        try {
            LocalDate date = LocalDate.parse(dateStr);
            LeadShiftReport report = new LeadShiftReport();
            report.setTechnicianName(techName);
            report.setShiftDate(date);
            report.setShift(shift);
            report.setReportContent(content);
            report.setSignOff(false);

            dao.saveReport(report);

            JOptionPane.showMessageDialog(entryView, "Report submitted successfully!");
            entryView.dispose();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(entryView, "Invalid date format. Please use YYYY-MM-DD.");
        }
    }
}
