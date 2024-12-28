package controller;

import view.DateSelectionView;
import view.PreviousReportsView;
import model.DailyReportDAO;
import model.DailyReport;
import java.util.List;

public class DateSelectionController {
    private DateSelectionView dateView;
    private DailyReportDAO reportDAO;

    public DateSelectionController(DateSelectionView dateView, DailyReportDAO reportDAO) {
        this.dateView = dateView;
        this.reportDAO = reportDAO;

        this.dateView.setSearchListener((date, shiftName) -> {
            // You would need a method in DAO to get reports by date/shiftName
            // For demonstration, let's assume we have it:
            List<DailyReport> reports = reportDAO.getReportsByDateAndShift(date, shiftName);

            // Once you have the reports:
            PreviousReportsView prevView = new PreviousReportsView();
            new PreviousReportsController(prevView/*pass needed data*/);
            prevView.open();
            dateView.dispose();
        });
    }
}
