package forms;

import java.io.IOException;
import java.time.LocalDate;

import javax.swing.JOptionPane;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.wb.swt.SWTResourceManager;

import main.Code;
import main.DataObject;

public class PreviousReports extends Shell {
	private final FormToolkit formToolkit = new FormToolkit(Display.getDefault());
	private Text text, text1, text2, text3, text4, text5, text6, text7, text8, text9;
	private Text textTakedowns, textIdRequests, textEquipment, textMonitoring, txtName, txtDate, txtShift,
			txtOncominglead, txtCallins;
	private Combo combo, combo_1;
	private static String time, shift;
	private Button btnEmployee, btnEmployee_1, btnEmployee_2, btnEmployee_3, btnEmployee_4, btnEmployee_5, btnEmployee_6,
			btnEmployee_7, btnEmployee_8, btnEmployee_9;
	private boolean[] buttonValues = new boolean[10];
	private Text txtReasonPassdown;
	private String[] mocArray;
	private DataObject obj;
	
	public PreviousReports(Display display, LocalDate date, String shift)
			throws IllegalArgumentException, IllegalAccessException, IOException {
		super(display, SWT.SHELL_TRIM);
		setImage(SWTResourceManager.getImage(PreviousReports.class, "/datastore/icons8-exchange-48.ico"));
		setLocation(200, 100);
		GridLayout gridLayout = new GridLayout(1, false);
		gridLayout.marginBottom = 25;
		gridLayout.marginRight = 25;
		gridLayout.marginTop = 25;
		gridLayout.marginLeft = 30;
		setLayout(gridLayout);
		try {
			mocArray = Code.getMocList();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		// Set the shift check string from the DateSelection class
		setShift(shift);

		// Establish the date string
		setTime((date.getMonthValue()) + "/" + date.getDayOfMonth() + "/" + date.getYear());

		// Create a DataObject object, and try to set it's values using the passdown
		// datastore
		obj = new DataObject();
		obj.date = null;
		obj = Code.getPassdownData(getTime(), getShift());
		setObj(obj);
		
		buttonValues[0] = obj.employee;
		buttonValues[1] = obj.employee1;
		buttonValues[2] = obj.employee2;
		buttonValues[3] = obj.employee3;
		buttonValues[4] = obj.employee4;
		buttonValues[5] = obj.employee5;
		buttonValues[6] = obj.employee6;
		buttonValues[7] = obj.employee7;
		buttonValues[8] = obj.employee8;
		buttonValues[9] = obj.employee9;
		
		for (int i = 0; i < buttonValues.length; i++) {
			if (buttonValues[i] == true) {
				obj.setEmployees(i, true);
			} else {
				obj.setEmployees(i, false);
			}
		}
		
		// Close this shell if the record doesn't exist
		if (obj.date == null) {
			JOptionPane.showMessageDialog(null, "Record not found");
			this.close();
		}
		
		ScrolledForm scrldfrmCheyenneTocLead = formToolkit.createScrolledForm(this);
		GridData gd_scrldfrmCheyenneTocLead = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_scrldfrmCheyenneTocLead.widthHint = 1200;
		gd_scrldfrmCheyenneTocLead.heightHint = 725;
		scrldfrmCheyenneTocLead.setLayoutData(gd_scrldfrmCheyenneTocLead);
		formToolkit.paintBordersFor(scrldfrmCheyenneTocLead);
		scrldfrmCheyenneTocLead.setText("Cheyenne TOC Lead Tech Shift Report");
		scrldfrmCheyenneTocLead.setBounds(0, 0, 697, 564);
		Composite composite = formToolkit.createComposite(scrldfrmCheyenneTocLead.getBody(), SWT.BORDER);
		composite.setBounds(10, 20, 697, 600);
		formToolkit.paintBordersFor(composite);


		// Recreate the application window and set values based on the retrieved
		// record
		// Uses DataObject as the source of data
		Group grpStaff = new Group(composite, SWT.NONE);
		grpStaff.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.BOLD));
		grpStaff.setText("Shift Staff and Assignments");
		grpStaff.setBounds(10, 10, 330, 260);
		formToolkit.adapt(grpStaff);
		formToolkit.paintBordersFor(grpStaff);

		btnEmployee = new Button(grpStaff, SWT.CHECK);
		btnEmployee.setBounds(10, 20, 150, 16);
		formToolkit.adapt(btnEmployee, true, true);
		btnEmployee.setText(obj.employeeNames.getItem(0));
		btnEmployee.setSelection(obj.employee);
		btnEmployee.setEnabled(false);

		btnEmployee_1 = new Button(grpStaff, SWT.CHECK);
		btnEmployee_1.setBounds(10, 40, 150, 16);
		formToolkit.adapt(btnEmployee_1, true, true);
		btnEmployee_1.setText(obj.employeeNames.getItem(1));
		btnEmployee_1.setSelection(obj.employee1);
		btnEmployee_1.setEnabled(false);

		btnEmployee_2 = new Button(grpStaff, SWT.CHECK);
		btnEmployee_2.setBounds(10, 60, 150, 16);
		formToolkit.adapt(btnEmployee_2, true, true);
		btnEmployee_2.setText(obj.employeeNames.getItem(2));
		btnEmployee_2.setSelection(obj.employee2);
		btnEmployee_2.setEnabled(false);

		btnEmployee_3 = new Button(grpStaff, SWT.CHECK);
		btnEmployee_3.setBounds(10, 80, 150, 16);
		formToolkit.adapt(btnEmployee_3, true, true);
		btnEmployee_3.setText(obj.employeeNames.getItem(3));
		btnEmployee_3.setSelection(obj.employee3);
		btnEmployee_3.setEnabled(false);

		btnEmployee_4 = new Button(grpStaff, SWT.CHECK);
		btnEmployee_4.setBounds(10, 100, 150, 16);
		formToolkit.adapt(btnEmployee_4, true, true);
		btnEmployee_4.setText(obj.employeeNames.getItem(4));
		btnEmployee_4.setSelection(obj.employee4);
		btnEmployee_4.setEnabled(false);

		btnEmployee_5 = new Button(grpStaff, SWT.CHECK);
		btnEmployee_5.setBounds(10, 120, 150, 16);
		formToolkit.adapt(btnEmployee_5, true, true);
		btnEmployee_5.setText(obj.employeeNames.getItem(5));
		btnEmployee_5.setSelection(obj.employee5);
		btnEmployee_5.setEnabled(false);

		btnEmployee_6 = new Button(grpStaff, SWT.CHECK);
		btnEmployee_6.setBounds(10, 140, 150, 16);
		formToolkit.adapt(btnEmployee_6, true, true);
		btnEmployee_6.setText(obj.employeeNames.getItem(6));
		btnEmployee_6.setSelection(obj.employee6);
		btnEmployee_6.setEnabled(false);

		btnEmployee_7 = new Button(grpStaff, SWT.CHECK);
		btnEmployee_7.setBounds(10, 160, 150, 16);
		formToolkit.adapt(btnEmployee_7, true, true);
		btnEmployee_7.setText(obj.employeeNames.getItem(7));
		btnEmployee_7.setSelection(obj.employee7);
		btnEmployee_7.setEnabled(false);

		btnEmployee_8 = new Button(grpStaff, SWT.CHECK);
		btnEmployee_8.setBounds(10, 180, 150, 16);
		formToolkit.adapt(btnEmployee_8, true, true);
		btnEmployee_8.setText(obj.employeeNames.getItem(8));
		btnEmployee_8.setSelection(obj.employee8);
		btnEmployee_8.setEnabled(false);

		btnEmployee_9 = new Button(grpStaff, SWT.CHECK);
		btnEmployee_9.setBounds(10, 200, 150, 16);
		formToolkit.adapt(btnEmployee_9, true, true);
		btnEmployee_9.setText(obj.employeeNames.getItem(9));
		btnEmployee_9.setSelection(obj.employee9);
		btnEmployee_9.setEnabled(false);

		text = formToolkit.createText(grpStaff, "", SWT.NONE);
		text.setText(obj.text);
		text.setBounds(180, 20, 140, 18);
		text.setEditable(false);

		text1 = formToolkit.createText(grpStaff, "", SWT.NONE);
		text1.setText(obj.text1);
		text1.setBounds(180, 40, 140, 18);
		text1.setEditable(false);

		text2 = formToolkit.createText(grpStaff, "", SWT.NONE);
		text2.setText(obj.text2);
		text2.setBounds(180, 60, 140, 18);
		text2.setEditable(false);

		text3 = formToolkit.createText(grpStaff, "", SWT.NONE);
		text3.setText(obj.text3);
		text3.setBounds(180, 80, 140, 18);
		text3.setEditable(false);

		text4 = formToolkit.createText(grpStaff, "", SWT.NONE);
		text4.setText(obj.text4);
		text4.setBounds(180, 100, 140, 18);
		text4.setEditable(false);

		text5 = formToolkit.createText(grpStaff, "", SWT.NONE);
		text5.setText(obj.text5);
		text5.setBounds(180, 120, 140, 18);
		text5.setEditable(false);

		text6 = formToolkit.createText(grpStaff, "", SWT.NONE);
		text6.setText(obj.text6);
		text6.setBounds(180, 140, 140, 18);
		text6.setEditable(false);

		text7 = formToolkit.createText(grpStaff, "", SWT.NONE);
		text7.setText(obj.text7);
		text7.setBounds(180, 160, 140, 18);
		text7.setEditable(false);

		text8 = formToolkit.createText(grpStaff, "", SWT.NONE);
		text8.setText(obj.text8);
		text8.setBounds(180, 180, 140, 18);
		text8.setEditable(false);

		text9 = formToolkit.createText(grpStaff, "", SWT.NONE);
		text9.setText(obj.text9);
		text9.setBounds(180, 200, 140, 18);
		text9.setEditable(false);

		Label lblSelectNormalStaff = formToolkit.createLabel(grpStaff,
				"Select staff for the shift and where they were assigned for the shift.", SWT.WRAP);
		lblSelectNormalStaff.setLocation(10, 222);
		lblSelectNormalStaff.setSize(310, 30);

		Group grpOngoingOutagesAnd = new Group(composite, SWT.NONE);
		grpOngoingOutagesAnd.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.BOLD));
		grpOngoingOutagesAnd.setText("Ongoing Outages and Maintenance");
		grpOngoingOutagesAnd.setBounds(357, 10, 330, 260);
		formToolkit.adapt(grpOngoingOutagesAnd);
		formToolkit.paintBordersFor(grpOngoingOutagesAnd);

		Label lblTicketNumbersAnd = formToolkit.createLabel(grpOngoingOutagesAnd,
				"Service/Provider name and ticket numbers of any ongoing outages and/or maintenance.", SWT.NONE | SWT.WRAP);
		lblTicketNumbersAnd.setBounds(10, 220, 310, 30);

		Table table = new Table(grpOngoingOutagesAnd, SWT.MULTI | SWT.FULL_SELECTION | SWT.HIDE_SELECTION);
		table.setBounds(10, 22, 310, 192);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		final GridData gd = new GridData(SWT.FILL, SWT.FILL, true, true);
		table.setLayoutData(gd);

		TableColumn col1 = new TableColumn(table, SWT.CENTER);
		col1.setWidth(147);
		col1.setAlignment(SWT.LEFT);
		col1.setResizable(false);
		col1.setText("Service");
		TableColumn col2 = new TableColumn(table, SWT.CENTER);
		col2.setWidth(146);
		col2.setAlignment(SWT.LEFT);
		col2.setResizable(false);
		col2.setText("ELVIS Ticket #");
		Color gray = display.getSystemColor(SWT.COLOR_GRAY);

		int count = 0;
		for (int i = 0; i < 22; i += 2) {
			count++;
			TableItem item = new TableItem(table, SWT.BORDER_SOLID);
			item.setText(0, obj.tableData.getItem(i));
			item.setText(1, obj.tableData.getItem(i + 1));
			if (count % 2 == 0) {
				item.setBackground(gray);
			}
		}

		Group grpRequestsFromOther = new Group(composite, SWT.NONE);
		grpRequestsFromOther.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.BOLD));
		grpRequestsFromOther.setText("Requests from other departments");
		grpRequestsFromOther.setBounds(357, 276, 330, 158);
		formToolkit.adapt(grpRequestsFromOther);
		formToolkit.paintBordersFor(grpRequestsFromOther);

		textIdRequests = formToolkit.createText(grpRequestsFromOther, "New Text", SWT.WRAP);
		textIdRequests.setText(obj.idRequestText);
		textIdRequests.setBounds(10, 22, 310, 85);
		textIdRequests.setEditable(false);

		Label lblEnterAnyInterdepartmental = formToolkit.createLabel(grpRequestsFromOther,
				"Any interdepartmental requests that need to be handled on a different shift (e.g. routes, ELVIS updates, etc...)",
				SWT.NONE | SWT.WRAP);
		lblEnterAnyInterdepartmental.setBounds(10, 115, 310, 30);

		Group grpEquipmentredundancyIssues = new Group(composite, SWT.NONE);
		grpEquipmentredundancyIssues.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.BOLD));
		grpEquipmentredundancyIssues.setText("Equipment/Redundancy Issues");
		grpEquipmentredundancyIssues.setBounds(10, 440, 330, 158);
		formToolkit.adapt(grpEquipmentredundancyIssues);
		formToolkit.paintBordersFor(grpEquipmentredundancyIssues);

		textEquipment = formToolkit.createText(grpEquipmentredundancyIssues, "New Text", SWT.WRAP);
		textEquipment.setText(obj.equipmentText);
		textEquipment.setBounds(10, 22, 310, 85);
		textEquipment.setEditable(false);

		Label lblEnterAnyOngoing = formToolkit.createLabel(grpEquipmentredundancyIssues,
				"Ongoing equipment or redundancy issues (e.g. ESPN backup down, OP-2 SA 1 not working, wall issues"
						+ " etc...)",
				SWT.WRAP);
		lblEnterAnyOngoing.setBounds(10, 115, 310, 30);

		Group grpSpecialMonitoringRequests = new Group(composite, SWT.NONE);
		grpSpecialMonitoringRequests.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.BOLD));
		grpSpecialMonitoringRequests.setText("Special Monitoring Requests");
		grpSpecialMonitoringRequests.setBounds(357, 440, 330, 158);
		formToolkit.adapt(grpSpecialMonitoringRequests);
		formToolkit.paintBordersFor(grpSpecialMonitoringRequests);

		textMonitoring = formToolkit.createText(grpSpecialMonitoringRequests, "New Text", SWT.WRAP);
		textMonitoring.setText(obj.specialMonitoringText);
		textMonitoring.setBounds(10, 22, 310, 85);
		textMonitoring.setEditable(false);

		Label lblEnterAnySpecial = formToolkit.createLabel(grpSpecialMonitoringRequests,
				"Any special monitoring requests (e.g. Negative crawls, high-profile broadcasts or discrepancies, etc...)",
				SWT.WRAP);
		lblEnterAnySpecial.setBounds(10, 115, 310, 30);

		Group grpTakedowns = new Group(composite, SWT.NONE);
		grpTakedowns.setBounds(10, 276, 330, 158);
		grpTakedowns.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.BOLD));
		grpTakedowns.setText("Takedowns, Restores, and Other Channel Launch");
		formToolkit.adapt(grpTakedowns);
		formToolkit.paintBordersFor(grpTakedowns);

		textTakedowns = formToolkit.createText(grpTakedowns, "New Text", SWT.NONE | SWT.WRAP);
		textTakedowns.setText(obj.takedownText);
		textTakedowns.setBounds(10, 22, 310, 75);
		textTakedowns.setEditable(false);

		Label lblTakedownEventsIn =
				formToolkit.createLabel(grpTakedowns, "Takedown events in the next 48 hours, restore events, or upcoming channel launch proceedings. (e.g. takedowns, temp/permanent restores, files need ingested, etc...)", SWT.WRAP);
		lblTakedownEventsIn.setBounds(10, 103, 310, 45);

		Group group = new Group(scrldfrmCheyenneTocLead.getBody(), SWT.NONE);
		group.setBackground(SWTResourceManager.getColor(255, 255, 255));
		group.setBounds(780, 65, 376, 82);
		formToolkit.adapt(group);
		formToolkit.paintBordersFor(group);

		Label lblDate = formToolkit.createLabel(group, "Date and Time:", SWT.NONE);
		lblDate.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.BOLD));
		lblDate.setBounds(10, 22, 95, 17);

		txtDate = formToolkit.createText(group, "New Text", SWT.NONE);
		txtDate.setEditable(false);
		txtDate.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.NORMAL));
		txtDate.setText(obj.date + " " + obj.time + " MT");
		txtDate.setBounds(111, 21, 123, 20);
		txtDate.setEditable(false);

		Label lblShift = formToolkit.createLabel(group, "Shift:", SWT.NONE);
		lblShift.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.BOLD));
		lblShift.setBounds(259, 22, 40, 15);

		Label lblName = formToolkit.createLabel(group, "Name:", SWT.NONE);
		lblName.setBounds(10, 45, 40, 17);
		lblName.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.BOLD));

		txtName = formToolkit.createText(group, "New Text", SWT.LEFT);
		txtName.setBounds(56, 45, 178, 20);
		txtName.setEditable(false);
		txtName.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.NORMAL));
		txtName.setText(obj.user);

		txtShift = formToolkit.createText(group, "New Text", SWT.NONE);
		txtShift.setText(shift);
		txtShift.setBounds(305, 18, 60, 21);
		txtShift.setEditable(false);

		Group grpDailyChecklist = new Group(scrldfrmCheyenneTocLead.getBody(), SWT.NONE);
		grpDailyChecklist.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.BOLD));
		grpDailyChecklist.setText("Daily Checklist");
		grpDailyChecklist.setBounds(730, 150, 460, 370);
		formToolkit.adapt(grpDailyChecklist);
		formToolkit.paintBordersFor(grpDailyChecklist);

		Label lblEaWaItx =
				formToolkit.createLabel(grpDailyChecklist, "EA_WA_ITX_Spreadsheet Verified for Accuracy: ", SWT.NONE);
		lblEaWaItx.setBounds(10, 25, 300, 15);

		Label lblEaWaItxPlayout =
				formToolkit.createLabel(grpDailyChecklist, "EA_WA_ITX_Spreadsheet Playouts Verified: \t", SWT.NONE);
		lblEaWaItxPlayout.setBounds(10, 45, 300, 15);

		Label lblChannelLaunchActivities =
				formToolkit.createLabel(grpDailyChecklist, "Channel Launch Verification Spreadsheet:\t", SWT.NONE);
		lblChannelLaunchActivities.setBounds(10, 65, 300, 15);

		Label lblWeatherMaps =
				formToolkit.createLabel(grpDailyChecklist, "Ensure Weather Maps is functioning:\t", SWT.NONE);
		lblWeatherMaps.setBounds(10, 85, 300, 15);

		Label lblInteractiveChecks =
				formToolkit.createLabel(grpDailyChecklist, "Perform interactive channel checks:", SWT.NONE);
		lblInteractiveChecks.setBounds(10, 105, 300, 15);

		Label lblDailySweeps = formToolkit.createLabel(grpDailyChecklist, "Daily Lead Sweeps:", SWT.NONE);
		lblDailySweeps.setBounds(10, 125, 300, 15);

		Label lblMaintenanceSignOff = formToolkit.createLabel(grpDailyChecklist, "Maintenance tickets signed off:", SWT.NONE);
		lblMaintenanceSignOff.setBounds(10, 145, 300, 15);

		Label lblMidsOnly = formToolkit.createLabel(grpDailyChecklist, "Mids only:", SWT.NONE);
		lblMidsOnly.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.BOLD));
		lblMidsOnly.setBounds(10, 175, 70, 20);

		Label lblMaintenanceRestored =
				formToolkit.createLabel(grpDailyChecklist, "Verify restoration of scheduled maintenance:\t", SWT.NONE);
		lblMaintenanceRestored.setBounds(10, 195, 300, 15);

		Label lblDaysOnly = formToolkit.createLabel(grpDailyChecklist, "Days only:", SWT.NONE);
		lblDaysOnly.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.BOLD));
		lblDaysOnly.setBounds(10, 225, 70, 20);

		Label lblTurnerservices =
				formToolkit.createLabel(grpDailyChecklist, "Update the STB Turner Services every Thursday:", SWT.NONE);
		lblTurnerservices.setBounds(10, 245, 300, 15);

		Label lblPreliminaryKciReport =
				formToolkit.createLabel(grpDailyChecklist, "Respond to the Preliminary KCI Report:", SWT.NONE);
		lblPreliminaryKciReport.setBounds(10, 265, 300, 15);

		Label lblSkdlActivation =
				formToolkit.createLabel(grpDailyChecklist, "Ensure SKDL alarms have been uninhibited at 09:00 MT:", SWT.NONE);
		lblSkdlActivation.setBounds(10, 285, 300, 15);

		Label lblSwingsOnly = formToolkit.createLabel(grpDailyChecklist, "Swings only:", SWT.NONE);
		lblSwingsOnly.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.BOLD));
		lblSwingsOnly.setBounds(10, 315, 80, 20);

		Label lblMcSwitches =
				formToolkit.createLabel(grpDailyChecklist, "Verify takedown router logs for 5PM MT takedowns:", SWT.NONE);
		lblMcSwitches.setBounds(10, 335, 300, 15);

		Button btnEaWaItxComplete = new Button(grpDailyChecklist, SWT.CHECK);
		btnEaWaItxComplete.setEnabled(false);
		btnEaWaItxComplete.setBounds(338, 25, 93, 16);
		formToolkit.adapt(btnEaWaItxComplete, true, true);
		btnEaWaItxComplete.setText("Complete");
		btnEaWaItxComplete.setSelection(obj.eaWaItxComplete);

		Button btnEaWaItxPlayoutComplete = new Button(grpDailyChecklist, SWT.CHECK);
		btnEaWaItxPlayoutComplete.setEnabled(false);
		btnEaWaItxPlayoutComplete.setBounds(338, 45, 93, 16);
		formToolkit.adapt(btnEaWaItxPlayoutComplete, true, true);
		btnEaWaItxPlayoutComplete.setText("Complete");
		btnEaWaItxPlayoutComplete.setSelection(obj.eaWaItxPlayoutComplete);

		Button btnChannelLaunchComplete = new Button(grpDailyChecklist, SWT.CHECK);
		btnChannelLaunchComplete.setEnabled(false);
		btnChannelLaunchComplete.setBounds(338, 65, 93, 16);
		formToolkit.adapt(btnChannelLaunchComplete, true, true);
		btnChannelLaunchComplete.setText("Complete");
		btnChannelLaunchComplete.setSelection(obj.channelLaunchComplete);

		Button btnWeatherComplete = new Button(grpDailyChecklist, SWT.CHECK);
		btnWeatherComplete.setEnabled(false);
		btnWeatherComplete.setBounds(338, 85, 93, 16);
		formToolkit.adapt(btnWeatherComplete, true, true);
		btnWeatherComplete.setText("Complete");
		btnWeatherComplete.setSelection(obj.weatherComplete);

		Button btnInteractiveComplete = new Button(grpDailyChecklist, SWT.CHECK);
		btnInteractiveComplete.setEnabled(false);
		btnInteractiveComplete.setBounds(338, 105, 93, 16);
		formToolkit.adapt(btnInteractiveComplete, true, true);
		btnInteractiveComplete.setText("Complete");
		btnInteractiveComplete.setSelection(obj.interactiveComplete);

		Button btnDailySweeps = new Button(grpDailyChecklist, SWT.CHECK);
		btnDailySweeps.setEnabled(false);
		btnDailySweeps.setBounds(338, 125, 93, 16);
		formToolkit.adapt(btnDailySweeps, true, true);
		btnDailySweeps.setText("Complete");
		btnDailySweeps.setSelection(obj.dailySweeps);

		Button btnMaintenanceSignOff = new Button(grpDailyChecklist, SWT.CHECK);
		btnMaintenanceSignOff.setEnabled(false);
		btnMaintenanceSignOff.setBounds(338, 145, 93, 16);
		formToolkit.adapt(btnMaintenanceSignOff, true, true);
		btnMaintenanceSignOff.setText("Complete");
		btnMaintenanceSignOff.setSelection(obj.maintenanceSigned);
		
		Button btnMaintenanceComplete = new Button(grpDailyChecklist, SWT.CHECK);
		btnMaintenanceComplete.setEnabled(false);
		btnMaintenanceComplete.setBounds(338, 195, 93, 16);
		formToolkit.adapt(btnMaintenanceComplete, true, true);
		btnMaintenanceComplete.setText("Complete");
		btnMaintenanceComplete.setSelection(obj.maintenanceComplete);

		Button btnTurnerComplete = new Button(grpDailyChecklist, SWT.CHECK);
		btnTurnerComplete.setEnabled(false);
		btnTurnerComplete.setBounds(338, 245, 93, 16);
		formToolkit.adapt(btnTurnerComplete, true, true);
		btnTurnerComplete.setText("Complete");
		btnTurnerComplete.setSelection(obj.turnerComplete);

		Button btnPreliminaryKciComplete = new Button(grpDailyChecklist, SWT.CHECK);
		btnPreliminaryKciComplete.setEnabled(false);
		btnPreliminaryKciComplete.setBounds(338, 265, 93, 16);
		formToolkit.adapt(btnPreliminaryKciComplete, true, true);
		btnPreliminaryKciComplete.setText("Complete");
		btnPreliminaryKciComplete.setSelection(obj.kciComplete);

		Button btnSkdlComplete = new Button(grpDailyChecklist, SWT.CHECK);
		btnSkdlComplete.setEnabled(false);
		btnSkdlComplete.setBounds(338, 285, 93, 16);
		formToolkit.adapt(btnSkdlComplete, true, true);
		btnSkdlComplete.setText("Complete");
		btnSkdlComplete.setSelection(obj.skdlComplete);

		Button btnMcSwitchesComplete = new Button(grpDailyChecklist, SWT.CHECK);
		btnMcSwitchesComplete.setEnabled(false);
		btnMcSwitchesComplete.setBounds(338, 335, 93, 16);
		formToolkit.adapt(btnMcSwitchesComplete, true, true);
		btnMcSwitchesComplete.setText("Complete");
		btnMcSwitchesComplete.setSelection(obj.mcSwitchesComplete);

		Label lblOncomingLead = formToolkit.createLabel(scrldfrmCheyenneTocLead.getBody(), "Oncoming Lead:", SWT.NONE);
		lblOncomingLead.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.BOLD));
		lblOncomingLead.setBounds(730, 537, 100, 20);

		txtOncominglead = formToolkit.createText(scrldfrmCheyenneTocLead.getBody(), "New Text", SWT.NONE);
		txtOncominglead.setText(obj.oncomingLead);
		txtOncominglead.setEditable(false);
		txtOncominglead.setBounds(730, 562, 200, 20);

		txtCallins = formToolkit.createText(scrldfrmCheyenneTocLead.getBody(), "", SWT.NONE);
		txtCallins.setBounds(130, 630, 477, 21);
		txtCallins.setText(obj.callins);
		
		Button btnPassdownAccepted = new Button(scrldfrmCheyenneTocLead.getBody(), SWT.CHECK);
		btnPassdownAccepted.setEnabled(false);
		btnPassdownAccepted.setBounds(940, 542, 127, 20);
		formToolkit.adapt(btnPassdownAccepted, true, true);
		btnPassdownAccepted.setText("Passdown Accepted");
		btnPassdownAccepted.setSelection(obj.acceptedChecked);

		Button btnPassdownDeclined = new Button(scrldfrmCheyenneTocLead.getBody(), SWT.CHECK);
		btnPassdownDeclined.setEnabled(false);
		btnPassdownDeclined.setBounds(940, 562, 150, 20);
		formToolkit.adapt(btnPassdownDeclined, true, true);
		btnPassdownDeclined.setText("Passdown Declined");
		btnPassdownDeclined.setSelection(obj.declinedChecked);

		Label lblEnterTheName = formToolkit.createLabel(scrldfrmCheyenneTocLead.getBody(),
				"Enter the name of the oncoming lead techs and select whether the "
						+ "accept the passdown. Passdowns should be declined if the if the "
						+ "information is unclear or incorrect.",
				SWT.WRAP);
		lblEnterTheName.setBounds(730, 587, 477, 30);

		Label lblLastEdit = new Label(scrldfrmCheyenneTocLead.getBody(), SWT.NONE);
		lblLastEdit.setBounds(1005, 625, 55, 15);
		formToolkit.adapt(lblLastEdit, true, true);
		lblLastEdit.setText("Last edit:");

		Label lblEditTime = new Label(scrldfrmCheyenneTocLead.getBody(), SWT.NONE);
		lblEditTime.setBounds(1060, 625, 130, 15);
		formToolkit.adapt(lblEditTime, true, true);
		lblEditTime.setText(obj.editTime);

		Label lblReasonPassdown = new Label(scrldfrmCheyenneTocLead.getBody(), SWT.WRAP);
		lblReasonPassdown.setBounds(10, 665, 100, 38);
		formToolkit.adapt(lblReasonPassdown, true, true);
		lblReasonPassdown.setText("Reason passdown was declined:");
		try {
		} catch (IllegalArgumentException e) {
			// TODO: handle exception
			txtReasonPassdown.setText("Not applicable");
		}

		combo = new Combo(scrldfrmCheyenneTocLead.getBody(), SWT.READ_ONLY);
		combo.setBounds(823, 10, 333, 23);
		combo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		formToolkit.adapt(combo);
		formToolkit.paintBordersFor(combo);
		for (int i = 0; i < mocArray.length; i++) {
			if (!mocArray[i].contentEquals(" ")) {
				combo.add(mocArray[i]);
			}
		}
		combo.select(obj.mocIndex);
		combo.setEnabled(false);
		
		combo_1 = new Combo(scrldfrmCheyenneTocLead.getBody(), SWT.READ_ONLY);
		combo_1.setBounds(823, 41, 167, 23);
		combo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		formToolkit.adapt(combo_1);
		formToolkit.paintBordersFor(combo_1);
		combo_1.setEnabled(false);
		combo_1.add("Cheyenne");
		combo_1.add("Gilbert");
		combo_1.select(obj.odsIndex);
		
		Label lblMoc = formToolkit.createLabel(scrldfrmCheyenneTocLead.getBody(), "MOC:", SWT.NONE);
		lblMoc.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.BOLD));
		lblMoc.setBounds(780, 10, 44, 23);
		
		Label lblOds = formToolkit.createLabel(scrldfrmCheyenneTocLead.getBody(), "ODS:", SWT.NONE);
		lblOds.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.BOLD));
		lblOds.setBounds(780, 40, 44, 23);
		
		Label lblCallins = formToolkit.createLabel(scrldfrmCheyenneTocLead.getBody(), "Call-Ins:", SWT.NONE);
		lblCallins.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.BOLD));
		lblCallins.setBounds(65, 630, 62, 20);
		
		txtReasonPassdown = formToolkit.createText(scrldfrmCheyenneTocLead.getBody(), "New Text", SWT.WRAP | SWT.V_SCROLL);
		txtReasonPassdown.setEditable(false);
		txtReasonPassdown.setBounds(110, 660, 597, 35);
		txtReasonPassdown.setText(obj.declinedReason);

		new Label(this, SWT.NONE);
		
		Menu menu = new Menu(this, SWT.BAR);
		setMenuBar(menu);
		
		MenuItem mntmFile = new MenuItem(menu, SWT.CASCADE);
		mntmFile.setText("&File");
		mntmFile.setAccelerator(SWT.MOD2 + 'f');
		
		Menu menu_1 = new Menu(mntmFile);
		mntmFile.setMenu(menu_1);
		
		MenuItem mntmQuit = new MenuItem(menu_1, SWT.NONE);
		mntmQuit.setAccelerator(SWT.MOD1 + 'q');
		mntmQuit.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				quit();
			}
		});
		mntmQuit.setText("&Quit\tCtrl+Q");

		
		MenuItem mntmCloseApp = new MenuItem(menu_1, SWT.NONE);
		mntmCloseApp.setAccelerator(SWT.MOD1 + SWT.F4);
		mntmCloseApp.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				System.exit(0);
			}
		});
		mntmCloseApp.setText("Close App\t Ctrl+F4");
		
		MenuItem mntmEmail = new MenuItem(menu, SWT.CASCADE);
		mntmEmail.setText("Email");
		
		Menu menu_2 = new Menu(mntmEmail);
		mntmEmail.setMenu(menu_2);
		
		MenuItem mntmCreateShiftReport = new MenuItem(menu_2, SWT.NONE);
		mntmCreateShiftReport.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				sendEmail();
			}
		});
		mntmCreateShiftReport.setText("Create Shift Report Email");
		createContents();
	}

	// Create contents of the shell
	protected void createContents() {
		setText("Report Viewer");
		setSize(1300, 830);

	}

	protected void sendEmail() {
		forms.HtmlEmailSender.sendEMail(getObj());
	}
	
	@Override
	public void close() {
		// TODO Auto-generated method stub
		super.close();
	}
	protected void setObj(DataObject obj) {
		this.obj = obj;
	}
	
	protected DataObject getObj() {
		return this.obj;
	}
	protected void quit() {
		this.close();
	}
	
	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

	// Setter for time
	protected void setTime(String timeSet) {
		time = timeSet;
	}

	// Getter for time
	protected String getTime() {
		return time;
	}

	// Setter for checkbox button text
	protected void setButtonValues(boolean butValues, int index) {
		buttonValues[index] = butValues;
	}

	// Getter for checkbox button text
	protected boolean getButtonValues(int index) {
		return buttonValues[index];
	}

	// Setter for shift
	protected static void setShift(String shift) {
		PreviousReports.shift = shift;
	}

	// Getter for shift
	protected static String getShift() {
		return PreviousReports.shift;
	}
}