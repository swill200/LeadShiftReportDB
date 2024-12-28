package controller;

import model.ShiftDAO;
import view.MainShell;
import view.ReportEntryShell;
import view.EmployeeShiftEditorShell;

import java.util.Map;

import org.eclipse.swt.SWT;

public class MainController {
    private MainShell mainShell;
    private ShiftDAO shiftDAO;
    
    private static final Map<String, Integer> SHIFT_IDS = Map.of(
            "Day", 1,
            "Swing", 2,
            "Mid", 3
        );
    
    public MainController(MainShell mainShell) {
        this.mainShell = mainShell;
        this.shiftDAO = new ShiftDAO();  // Initialize the DAO for database operations
        attachEventListeners();
    }

    private void attachEventListeners() {
        // Button: New Report Entry
        mainShell.getBtnNewReportEntry().addListener(SWT.Selection, event -> handleNewReportEntry());

        // Button: Review Previous Shift Reports
        mainShell.getBtnPreviousReports().addListener(SWT.Selection, event -> handlePreviousReports());

        // Menu: Quit
        mainShell.getMntmQuit().addListener(SWT.Selection, event -> handleQuit());

        // Menu: Edit Days
        mainShell.getMntmEditDays().addListener(SWT.Selection, event -> handleEditShifts("Day"));

        // Menu: Edit Swings
        mainShell.getMntmEditSwings().addListener(SWT.Selection, event -> handleEditShifts("Swing"));
        
        // Menu: Edit Mids
        mainShell.getMntmEditMids().addListener(SWT.Selection, event -> handleEditShifts("Mid"));

        // Menu: Edit MOC
        mainShell.getMntmEditMoc().addListener(SWT.Selection, event -> handleEditMoc());
    }

    private void handleNewReportEntry() {
        // Logic for opening the "New Report Entry" form
        ReportEntryShell report = new ReportEntryShell(mainShell);
        report.open();
        System.out.println("Opening New Report Entry...");
        // TODO: Open the corresponding form or shell here
    }

    private void handlePreviousReports() {
        // Logic for reviewing previous reports
        System.out.println("Opening Previous Shift Reports...");
        // TODO: Open the corresponding form or shell here
    }

    private void handleQuit() {
        // Close the main application shell
        mainShell.close();
    }

    private void handleEditShifts(String shiftType) {
        int shiftID = SHIFT_IDS.get(shiftType);
        EmployeeShiftEditorShell editor = new EmployeeShiftEditorShell(mainShell, shiftID, shiftType);
        editor.open();
    }

    private void handleEditMoc() {
        // Logic for editing MOC shifts or other data
        System.out.println("Editing MOC List...");
        // TODO: Open a relevant form or dialog for editing MOC data
    }
}
