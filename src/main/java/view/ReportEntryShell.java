package view;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;

import controller.ReportEntryController;
import model.ReportEntryDAO;
import util.ShellUtils;
import org.eclipse.jface.resource.LocalResourceManager;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.resource.FontDescriptor;

public class ReportEntryShell extends Shell {
    private final FormToolkit formToolkit = new FormToolkit(Display.getDefault());
    private Text textTakedowns, textIdRequests, textEquipment, textMonitoring, txtName, txtDate, txtShift,
            txtOncomingLead, txtCallins;
    private Combo comboMOC, comboODS;
    private Table table;
    private Label lblEditTime, lblEdit;
    private ScrolledForm scrldfrmCheyenneTocLead;
    private Group grpDailyChecklist;
    private ReportEntryController controller;
    private ReportEntryDAO reportEntryDAO;
    private Composite composite;
    private Button btnAddTicket, btnRemoveTicket;
    private LocalResourceManager localResourceManager;
    
    public ReportEntryShell(Shell parent) {
        super(parent, SWT.SHELL_TRIM);
        createResourceManager();
        setSize(1300, 850);
        setText("Report Entry");
        ShellUtils.centerOnParent(parent, this);
        // Initialize DAO if null
        if (reportEntryDAO == null) {
            reportEntryDAO = new ReportEntryDAO(); // Replace with actual initialization if needed
        }
        controller = new ReportEntryController(this, reportEntryDAO);
        createContents();
        // Delegate event handling to the controller
        controller.attachEventListeners(this);
    }
    private void createResourceManager() {
        localResourceManager = new LocalResourceManager(JFaceResources.getResources(),this);
    }

    protected void createContents() {
        GridLayout gridLayout = new GridLayout(1, false);
        gridLayout.marginBottom = 25;
        gridLayout.marginRight = 25;
        gridLayout.marginTop = 25;
        gridLayout.marginLeft = 30;
        setLayout(gridLayout);
        
        scrldfrmCheyenneTocLead = formToolkit.createScrolledForm(this);
        
        GridData gd_scrldfrmCheyenneTocLead = new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1);
        gd_scrldfrmCheyenneTocLead.widthHint = 1200;
        gd_scrldfrmCheyenneTocLead.heightHint = 700;
        scrldfrmCheyenneTocLead.setLayoutData(gd_scrldfrmCheyenneTocLead);
        formToolkit.paintBordersFor(scrldfrmCheyenneTocLead);
        scrldfrmCheyenneTocLead.setText("Cheyenne TOC Lead Tech Shift Report");
        composite = formToolkit.createComposite(scrldfrmCheyenneTocLead.getBody(), SWT.NONE);
        composite.setBounds(10, 20, 697, 619);
        formToolkit.paintBordersFor(composite);
        
        lblEdit = new Label(scrldfrmCheyenneTocLead.getBody(), SWT.NONE);
        lblEdit.setBounds(965, 645, 65, 15);
        formToolkit.adapt(lblEdit, true, true);
        lblEdit.setText("Last edited: ");
        lblEditTime = new Label(scrldfrmCheyenneTocLead.getBody(), SWT.NONE);
        lblEditTime.setBounds(1036, 645, 154, 15);
        formToolkit.adapt(lblEditTime, true, true);
        lblEditTime.setText("");
        
        
        Group grpStaff = new Group(composite, SWT.NONE);
        grpStaff.setText("Shift Staff and Assignments");
        grpStaff.setBounds(10, 10, 330, 260);
        formToolkit.adapt(grpStaff);
        formToolkit.paintBordersFor(grpStaff);
        for (int i = 0; i < 10; i++) {
            Button btnEmployee = new Button(grpStaff, SWT.CHECK);
            btnEmployee.setBounds(10, 20 + (i * 20), 150, 16);
            btnEmployee.setText("Employee_" + i);
            Text employeeAssignment = formToolkit.createText(grpStaff, "", SWT.NONE);
            employeeAssignment.setBounds(180, 20 + (i * 20), 140, 18);
        }
        
        Label lblSelectNormalStaff = formToolkit.createLabel(grpStaff,
                "Select staff for the shift and where they were assigned for the shift.", SWT.WRAP);
        lblSelectNormalStaff.setBounds(10, 222, 310, 30);
        
        
        Group grpOngoingOutagesAnd = new Group(composite, SWT.NONE);
        grpOngoingOutagesAnd.setText("Ongoing Outages and Maintenance");
        grpOngoingOutagesAnd.setBounds(357, 10, 330, 260);
        formToolkit.adapt(grpOngoingOutagesAnd);
        formToolkit.paintBordersFor(grpOngoingOutagesAnd);
        
        table = new Table(grpOngoingOutagesAnd, SWT.MULTI | SWT.FULL_SELECTION);
        table.setBounds(10, 22, 310, 192);
        table.setHeaderVisible(true);
        table.setLinesVisible(true);
        formToolkit.paintBordersFor(table);
        
        TableColumn col1 = new TableColumn(table, SWT.LEFT);
        col1.setWidth(147);
        col1.setText("Service");
        
        TableColumn col2 = new TableColumn(table, SWT.LEFT);
        col2.setWidth(146);
        col2.setText("ELVIS Ticket #");
        
        btnAddTicket = new Button(grpOngoingOutagesAnd, SWT.PUSH);
        btnAddTicket.setText("+");
        btnAddTicket.setBounds(10, 220, 30, 30);
        formToolkit.adapt(btnAddTicket, true, true);

        btnRemoveTicket = new Button(grpOngoingOutagesAnd, SWT.PUSH);
        btnRemoveTicket.setText("-");
        btnRemoveTicket.setBounds(50, 220, 30, 30);
        formToolkit.adapt(btnRemoveTicket, true, true);
        
        
        Group grpTakedowns = new Group(composite, SWT.NONE);
        grpTakedowns.setText("Takedowns, Restores, and Other Channel Launch");
        grpTakedowns.setBounds(10, 276, 330, 158);
        formToolkit.adapt(grpTakedowns);
        formToolkit.paintBordersFor(grpTakedowns);
        textTakedowns = formToolkit.createText(grpTakedowns, "", SWT.NONE | SWT.WRAP);
        textTakedowns.setBounds(10, 22, 310, 75);
        textTakedowns.setData("action", "takedownsChanged");
        
        Label lblTakedownEventsIn = formToolkit.createLabel(grpTakedowns,
                "Takedown events in the next 48 hours, restore events, or upcoming channel launch proceedings.",
                SWT.WRAP);
        lblTakedownEventsIn.setBounds(10, 103, 310, 45);
        
        
        Group grpRequestsFromOther = new Group(composite, SWT.NONE);
        grpRequestsFromOther.setText("Requests from other departments");
        grpRequestsFromOther.setBounds(357, 276, 330, 158);
        formToolkit.adapt(grpRequestsFromOther);
        formToolkit.paintBordersFor(grpRequestsFromOther);
        textIdRequests = formToolkit.createText(grpRequestsFromOther, "", SWT.WRAP);
        textIdRequests.setBounds(10, 22, 310, 85);
        textIdRequests.setData("action", "idRequestsChanged");
        
        Label lblEnterAnyInterdepartmental = formToolkit.createLabel(grpRequestsFromOther,
                "Any interdepartmental requests that need to be handled on a different shift.", SWT.WRAP);
        lblEnterAnyInterdepartmental.setBounds(10, 115, 310, 30);
        
        
        Group grpEquipmentredundancyIssues = new Group(composite, SWT.NONE);
        grpEquipmentredundancyIssues.setText("Equipment/Redundancy Issues");
        grpEquipmentredundancyIssues.setBounds(10, 440, 330, 158);
        formToolkit.adapt(grpEquipmentredundancyIssues);
        formToolkit.paintBordersFor(grpEquipmentredundancyIssues);
        textEquipment = formToolkit.createText(grpEquipmentredundancyIssues, "", SWT.WRAP);
        textEquipment.setBounds(10, 22, 310, 85);
        textEquipment.setData("action", "equipmentChanged");
        
        Label lblEnterAnyOngoing = formToolkit.createLabel(grpEquipmentredundancyIssues,
                "Ongoing equipment or redundancy issues.", SWT.WRAP);
        lblEnterAnyOngoing.setBounds(10, 115, 310, 30);
        
        
        Group grpSpecialMonitoringRequests = new Group(composite, SWT.NONE);
        grpSpecialMonitoringRequests.setText("Special Monitoring Requests");
        grpSpecialMonitoringRequests.setBounds(357, 440, 330, 158);
        formToolkit.adapt(grpSpecialMonitoringRequests);
        formToolkit.paintBordersFor(grpSpecialMonitoringRequests);
        textMonitoring = formToolkit.createText(grpSpecialMonitoringRequests, "", SWT.WRAP);
        textMonitoring.setBounds(10, 22, 310, 85);
        textMonitoring.setData("action", "monitoringChanged");
        Label lblEnterAnySpecial = formToolkit.createLabel(grpSpecialMonitoringRequests,
                "Any special monitoring requests.", SWT.WRAP);
        lblEnterAnySpecial.setBounds(10, 115, 310, 30);
        
        // Combo composite
        Composite body = scrldfrmCheyenneTocLead.getBody();
        body.setLayout(null);
        
        // MOC Label and Combo
        Label lblMOC = formToolkit.createLabel(body, "MOC:", SWT.NONE);
        lblMOC.setBounds(780, 20, 40, 20);
        lblMOC.setFont(localResourceManager.create(FontDescriptor.createFrom("Segoe UI", 10, SWT.BOLD)));

        comboMOC = new Combo(body, SWT.DROP_DOWN | SWT.READ_ONLY);
        comboMOC.setBounds(830, 20, 326, 23);
        formToolkit.adapt(comboMOC);
        comboMOC.add("Option 1");
        comboMOC.add("Option 2");

        // ODS Label and Combo
        Label lblODS = formToolkit.createLabel(body, "ODS:", SWT.NONE);
        lblODS.setBounds(780, 52, 40, 20);
        lblODS.setFont(localResourceManager.create(FontDescriptor.createFrom("Segoe UI", 10, SWT.BOLD)));

        comboODS = new Combo(body, SWT.DROP_DOWN | SWT.READ_ONLY);
        comboODS.setBounds(830, 49, 326, 23);
        formToolkit.adapt(comboODS);
        comboODS.add("Option A");
        comboODS.add("Option B");
        
        Group group = new Group(scrldfrmCheyenneTocLead.getBody(), SWT.NONE);
        group.setBounds(780, 76, 376, 82);
        formToolkit.adapt(group);
        formToolkit.paintBordersFor(group);
        
        Label lblDate = formToolkit.createLabel(group, "Date and Time:", SWT.NONE);
        lblDate.setFont(localResourceManager.create(FontDescriptor.createFrom("Segoe UI", 9, SWT.BOLD)));
        lblDate.setBounds(10, 22, 95, 17);
        
        txtDate = formToolkit.createText(group, "New Text", SWT.READ_ONLY);
        txtDate.setBounds(111, 21, 123, 20);
        formToolkit.adapt(txtDate, true, true);
        
        Label lblShift = formToolkit.createLabel(group, "Shift:", SWT.NONE);
        lblShift.setFont(localResourceManager.create(FontDescriptor.createFrom("Segoe UI", 9, SWT.BOLD)));
        lblShift.setBounds(259, 22, 40, 15);
        txtShift = formToolkit.createText(group, "", SWT.READ_ONLY);
        txtShift.setBounds(305, 18, 60, 21);
        formToolkit.adapt(txtShift, true, true);
        
        Label lblName = formToolkit.createLabel(group, "Name: ", SWT.NONE);
        lblName.setFont(localResourceManager.create(FontDescriptor.createFrom("Segoe UI", 9, SWT.BOLD)));
        lblName.setBounds(10, 48, 40, 17);
        txtName = formToolkit.createText(group, "", SWT.READ_ONLY);
        txtName.setBounds(60, 46, 174, 20);
        formToolkit.adapt(txtName, true, true);
        populateFields();

        createDailyChecklist("Days");
        
        Group oncomingLead = new Group(scrldfrmCheyenneTocLead.getBody(), SWT.NONE);
        oncomingLead.setBounds(738, 570, 452, 69);
        formToolkit.adapt(oncomingLead);
        formToolkit.paintBordersFor(oncomingLead);
        // Oncoming Lead Label and Text Box
        Label lblOncomingLead = formToolkit.createLabel(oncomingLead, "Oncoming Lead:", SWT.NONE);
        lblOncomingLead.setBounds(10, 10, 110, 20);
        lblOncomingLead.setFont(localResourceManager.create(FontDescriptor.createFrom("Segoe UI", 10, SWT.BOLD)));
        
        Text txtOncomingLead = formToolkit.createText(oncomingLead, "", SWT.BORDER);
        txtOncomingLead.setBounds(10, 36, 256, 20);
        formToolkit.adapt(txtOncomingLead, true, true);
        
        // Save and Close Button
        Button btnSaveClose = new Button(oncomingLead, SWT.PUSH);
        btnSaveClose.setBounds(334, 41, 100, 25);
        btnSaveClose.setText("Save and Close");
        formToolkit.adapt(btnSaveClose, true, true);

    }

    private void createDailyChecklist(String shift) {
        Composite checklistComposite = formToolkit.createComposite(scrldfrmCheyenneTocLead.getBody(), SWT.NONE);
        checklistComposite.setBounds(738, 164, 452, 400); // Adjust position and size
        formToolkit.paintBordersFor(checklistComposite);
        grpDailyChecklist = new Group(checklistComposite, SWT.NONE);
        grpDailyChecklist.setText("Daily Checklist");
        grpDailyChecklist.setBounds(10, 0, 433, 390);
        formToolkit.adapt(grpDailyChecklist);
        formToolkit.paintBordersFor(grpDailyChecklist);
        

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/dd/yyyy, HH:mm:ss");

        // Checklist items with labels and corresponding buttons
        Object[][] checklistItems = { { "EA_WA_ITX Playouts Operational/Accurate:", 25, true },
                { "EAS System Function Checks (4+/shift):", 45, true },
                { "Channel Launch Verification Spreadsheet:", 65, true },
                { "Daily SRF Agenda Verification:", 85, true }, { "Perform interactive channel checks:", 105, true },
                { "Daily Lead Sweeps:", 125, true }, { "Maintenance tickets signed off:", 145, true },
                { "Mids Only", 175, null },
                { "Verify restoration of scheduled maintenance:", 195, shift.equals("Mids") },
                { "Days Only", 225, null },
                { "Update the STB Turner Services every Thursday:", 245, shift.equals("Days") },
                { "Respond to the Preliminary KCI Report:", 265, shift.equals("Days") },
                { "Ensure SKDL alarms uninhibited at 09:00 MT:", 285, shift.equals("Days") },
                { "Swings Only", 315, null },
                { "Verify takedown router logs for 5PM MT takedowns:", 335, shift.equals("Swings") } };
        for (Object[] item : checklistItems) {
            String label = (String) item[0];
            int yPosition = (int) item[1];
            Boolean enabled = (Boolean) item[2];

            Label lblItem = formToolkit.createLabel(grpDailyChecklist, label, SWT.NONE);
            lblItem.setBounds(10, yPosition, 300, 15);

            // Apply bold font for label-only rows
            if (enabled == null) {
                Font boldFont = new Font(Display.getDefault(), lblItem.getFont().getFontData()[0].getName(),
                        lblItem.getFont().getFontData()[0].getHeight(), SWT.BOLD);
                lblItem.setFont(boldFont);

                // Dispose of the font when the label is disposed
                lblItem.addDisposeListener(e -> boldFont.dispose());
            }

            if (enabled != null) {
                Button btnComplete = new Button(grpDailyChecklist, SWT.CHECK);
                btnComplete.setBounds(338, yPosition, 93, 16);
                btnComplete.setEnabled(enabled);
                formToolkit.adapt(btnComplete, true, true);
                btnComplete.setText("Complete");


                btnComplete.addSelectionListener(new SelectionAdapter() {
                    @Override
                    public void widgetSelected(SelectionEvent e) {
                        boolean isChecked = btnComplete.getSelection();
                        System.out.println(isChecked);
                        String newAction = isChecked ? "markComplete" : "markIncomplete";
                        System.out.println("New action set: " + newAction);
                        controller.handleAction(newAction);
                        controller.setShellChanged(true);
                        lblEditTime.setText(LocalDateTime.now().format(formatter));
                    }
                });
            }
        }
    }

    private void populateFields() {
        if (txtDate != null) {
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("M/dd/yyyy, HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            txtDate.setText(dateTimeFormatter.format(now));
        }
        if (txtShift != null) {
            int hour = LocalDateTime.now().getHour();
            String shift = (hour >= 7 && hour < 15) ? "Day" : (hour >= 15 && hour < 23) ? "Swing" : "Mid";
            txtShift.setText(shift);
        }
        if (txtName != null) {
            txtName.setText(System.getProperty("user.name", "Unknown User"));
            System.out.println("Username: " + System.getProperty("user.name"));
        }
    }

    public List<Text> getTextFields() {
        return Arrays.asList(textTakedowns, textIdRequests, textEquipment, textMonitoring, txtName, txtDate, txtShift,
                txtOncomingLead, txtCallins);
    }

    public List<Button> getChecklistButtons() {
        List<Button> checklistButtons = new ArrayList<>();
        if (grpDailyChecklist != null) {
            for (Control control : grpDailyChecklist.getChildren()) {
                if (control instanceof Button && (control.getStyle() & SWT.CHECK) != 0) {
                    checklistButtons.add((Button) control);
                }
            }
        }
        return checklistButtons;
    }

    public void updateEditTimeLabel(String text) {
        lblEditTime.setText(text);
    }
    
    public Button getAddTicketButton() {
        return btnAddTicket;
    }

    public Button getRemoveTicketButton() {
        return btnRemoveTicket;
    }

    public Table getTicketTable() {
        return table;
    }
    
    @Override
    protected void checkSubclass() {
        // Prevent subclassing checks
    }
}