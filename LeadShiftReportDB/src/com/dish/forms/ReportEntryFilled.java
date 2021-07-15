package com.dish.forms;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import javax.swing.JOptionPane;

import org.eclipse.jface.viewers.*;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.wb.swt.SWTResourceManager;

import com.dish.main.Code;
import com.dish.main.DataObject;
import com.dish.main.Toast;

public class ReportEntryFilled extends Shell {
	private final FormToolkit formToolkit = new FormToolkit(Display.getDefault());
	private Text textTakedowns, textIdRequests, textEquipment, textMonitoring, txtName, txtDate, txtShift,
			txtOncomingLead, txtCallins;;
	private Combo combo, combo_1;
	private String declinedReasoning = "Not Applicable";
	private static String time, shift;
	private boolean[] buttonValues = new boolean[10];
	private static boolean shellChanged;
	private final String FILE_PATH = "src\\datastore\\passdown_datastore.pd";
	private final String BACKUP_FILE_PATH = "src\\datastore\\passdown_datastore.bak";
	private Table table;
	private boolean eaWaItxComplete, eaWaItxPlayoutComplete, channelLaunchComplete, weatherComplete, interactiveComplete,
			dailySweeps, maintenanceSigned, maintenanceComplete, turnerComplete, preliminaryKciComplete, skdlComplete,
			mcSwitchesComplete, passdownAccepted, passdownDeclined;
	private Label lblEditTime;
	private String[] mocArray;
	private static int mocIndex, odsIndex;
	private static LocalDate date;
//	private TableViewer tableViewer;
//	private Menu headerMenu;
	
	public ReportEntryFilled(Display display, LocalDate date, String shift)
			throws IllegalArgumentException, IllegalAccessException, IOException {
		super(display, SWT.SHELL_TRIM);
		setImage(SWTResourceManager.getImage(ReportEntryFilled.class, "/datastore/icons8-exchange-48.ico"));
		setLocation(200, 150);
		this.addListener(SWT.Close, new Listener() {
			public void handleEvent(Event event) {
				if (getShellChanged()) {
					if (JOptionPane.showConfirmDialog(null,
							"The report has been changed, are you sure you want to close without saving?", "Close Window?",
							JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE) == JOptionPane.YES_OPTION) {
						setShellChanged(false);
						event.doit = true;
					} else {
						event.doit = false;
					}

				}
			}
		});

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
		setDate(date);
		// Set the shift check string from the DateSelection class
		setShift(shift);

		// Establish the date string
		if (shift == "Mids" && LocalTime.now().isAfter(LocalTime.of(23, 00))) {
			setTime((getDate().getMonthValue()) + "/" + (getDate().getDayOfMonth() + 1) + "/" + getDate().getYear());
		} else {
			setTime((getDate().getMonthValue()) + "/" + getDate().getDayOfMonth() + "/" + getDate().getYear());
		}
		// Create a DataObject object, and try to set it's values using the passdown
		// datastore
		DataObject obj = Code.getPassdownData(getTime(), getShift());
		mocIndex = obj.mocIndex;

//		// Close this shell if the record doesn't exist
//		if (obj.date == null) {
//			this.close();
//		}

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

		eaWaItxComplete = obj.eaWaItxComplete;
		eaWaItxPlayoutComplete = obj.eaWaItxPlayoutComplete;
		channelLaunchComplete = obj.channelLaunchComplete;
		weatherComplete = obj.weatherComplete;
		interactiveComplete = obj.interactiveComplete;
		dailySweeps = obj.dailySweeps;
		maintenanceSigned = obj.maintenanceSigned;
		maintenanceComplete = obj.maintenanceComplete;
		turnerComplete = obj.turnerComplete;
		preliminaryKciComplete = obj.kciComplete;
		skdlComplete = obj.skdlComplete;
		mcSwitchesComplete = obj.mcSwitchesComplete;
		passdownAccepted = obj.acceptedChecked;
		passdownDeclined = obj.declinedChecked;

		ScrolledForm scrldfrmCheyenneTocLead = formToolkit.createScrolledForm(this);
		GridData gd_scrldfrmCheyenneTocLead = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_scrldfrmCheyenneTocLead.widthHint = 1200;
		gd_scrldfrmCheyenneTocLead.heightHint = 1450;
		scrldfrmCheyenneTocLead.setLayoutData(gd_scrldfrmCheyenneTocLead);
		formToolkit.paintBordersFor(scrldfrmCheyenneTocLead);
		scrldfrmCheyenneTocLead.setText("Cheyenne TOC Lead Tech Shift Report");

		Composite composite = formToolkit.createComposite(scrldfrmCheyenneTocLead.getBody(), SWT.NONE);
		composite.setBounds(10, 20, 697, 597);
		formToolkit.paintBordersFor(composite);

		lblEditTime = new Label(scrldfrmCheyenneTocLead.getBody(), SWT.NONE);
		lblEditTime.setBounds(1060, 625, 130, 15);
		formToolkit.adapt(lblEditTime, true, true);
		lblEditTime.setText(obj.editTime);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/dd/yyyy, HH:mm:ss");

		ModifyListener modListener = new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent arg0) {
				setShellChanged(true);
				lblEditTime.setText(LocalDateTime.now().format(formatter));
			}
		};

		// Recreate the application window and set values based on the retrieved
		// record
		// Uses DataObject as the source of data
		Group grpStaff = new Group(composite, SWT.NONE);
		grpStaff.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.BOLD));
		grpStaff.setText("Shift Staff and Assignments");
		grpStaff.setBounds(10, 10, 330, 260);
		formToolkit.adapt(grpStaff);
		formToolkit.paintBordersFor(grpStaff);

		Label lblSelectNormalStaff = formToolkit.createLabel(grpStaff,
				"Select staff for the shift and where they were assigned for the shift.", SWT.WRAP);
		lblSelectNormalStaff.setLocation(10, 222);
		lblSelectNormalStaff.setSize(310, 30);
		
		// Define the tableviewer for staff
		TableViewer tableViewer = new TableViewer(grpStaff, SWT.MULTI | SWT.H_SCROLL
        | SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.BORDER);

		
		// TODO: create function
		createColumns(tableViewer);
		
		// make lines and header visible
		final Table tableTwo = tableViewer.getTable();
		tableTwo.setHeaderVisible(true);
		tableTwo.setLinesVisible(true);
		tableTwo.setBounds(10, 20, 310, 196);
		formToolkit.paintBordersFor(tableTwo);
		
		// Set the content provider
		tableViewer.setContentProvider(ArrayContentProvider.getInstance());
		
		// Provide the input to the viewer, setInput() calls
		// getElements() on the content provider instance
		// TODO: which we will use to get the particular staff for 
		// the current shift
		tableViewer.setInput(null);
		
		
		Group grpOngoingOutagesAnd = new Group(composite, SWT.NONE);
		grpOngoingOutagesAnd.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.BOLD));
		grpOngoingOutagesAnd.setText("Ongoing Outages and Maintenance");
		grpOngoingOutagesAnd.setBounds(357, 10, 330, 260);
		formToolkit.adapt(grpOngoingOutagesAnd);
		formToolkit.paintBordersFor(grpOngoingOutagesAnd);

		Label lblTicketNumbersAnd = formToolkit.createLabel(grpOngoingOutagesAnd,
				"Ticket numbers and description of any ongoing outages and/or" + " maintenance.", SWT.NONE | SWT.WRAP);
		lblTicketNumbersAnd.setBounds(10, 220, 310, 30);

		table = new Table(grpOngoingOutagesAnd, SWT.MULTI | SWT.FULL_SELECTION | SWT.HIDE_SELECTION);
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

		// Editor code to handle editing of the table
		final TableEditor editor = new TableEditor(table);
		editor.horizontalAlignment = SWT.LEFT;
		editor.grabHorizontal = true;
		editor.minimumWidth = 50;
		table.addListener(SWT.MouseDown, new Listener() {
			@Override
			public void handleEvent(Event event) {
				Rectangle clientArea = table.getClientArea();
				Point pt = new Point(event.x, event.y);
				int index = table.getTopIndex();
				while (index < table.getItemCount()) {
					boolean visible = false;
					final TableItem item = table.getItem(index);
					for (int i = 0; i < table.getColumnCount(); i++) {
						Rectangle rectangle = item.getBounds(i);
						if (rectangle.contains(pt)) {
							final int column = i;
							final Text text = new Text(table, SWT.NONE);
							Listener textListener = new Listener() {
								@Override
								public void handleEvent(final Event e) {
									switch (e.type) {
									case SWT.FocusOut:
										item.setText(column, text.getText());
										text.dispose();
										break;
									case SWT.Traverse:
										switch (e.detail) {
										case SWT.TRAVERSE_RETURN:
											item.setText(column, text.getText());
										case SWT.TRAVERSE_ESCAPE:
											text.dispose();
											e.doit = false;
										case SWT.TAB:
											table.setSelection(table.getSelectionIndex() + 1);
											e.doit = true;
										}
										break;
									}
								}
							};
							text.addListener(SWT.FocusOut, textListener);
							text.addListener(SWT.Traverse, textListener);
							editor.setEditor(text, item, i);
							text.setText(item.getText(i));
							text.selectAll();
							text.setFocus();
							return;
						}
						if (!visible && rectangle.intersects(clientArea)) {
							visible = true;
						}
					}
					if (!visible) {
						return;
					}
					index++;
				}
			}
		});

		Group grpTakedowns = new Group(composite, SWT.NONE);
		grpTakedowns.setBounds(10, 276, 330, 158);
		grpTakedowns.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.BOLD));
		grpTakedowns.setText("Takedowns, Restores, and Other Channel Launch");
		formToolkit.adapt(grpTakedowns);
		formToolkit.paintBordersFor(grpTakedowns);

		textTakedowns = formToolkit.createText(grpTakedowns, "New Text", SWT.NONE | SWT.WRAP);
		textTakedowns.setText(obj.takedownText);
		textTakedowns.setBounds(10, 22, 310, 75);
		textTakedowns.setEditable(true);
		textTakedowns.addModifyListener(modListener);

		Group grpRequestsFromOther = new Group(composite, SWT.NONE);
		grpRequestsFromOther.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.BOLD));
		grpRequestsFromOther.setText("Requests from other departments");
		grpRequestsFromOther.setBounds(357, 276, 330, 158);
		formToolkit.adapt(grpRequestsFromOther);
		formToolkit.paintBordersFor(grpRequestsFromOther);

		textIdRequests = formToolkit.createText(grpRequestsFromOther, "New Text", SWT.WRAP);
		textIdRequests.setText(obj.idRequestText);
		textIdRequests.setBounds(10, 22, 310, 85);
		textIdRequests.setEditable(true);
		textIdRequests.addModifyListener(modListener);

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
		textEquipment.setEditable(true);
		textEquipment.addModifyListener(modListener);

		Label lblEnterAnyOngoing = formToolkit.createLabel(grpEquipmentredundancyIssues,
				"Ongoing equipment or redundancy issues (e.g. ESPN backup down, OP-2 SA 1 not working, wall issues, etc...)",
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
		textMonitoring.setEditable(true);
		textMonitoring.addModifyListener(modListener);

		Label lblEnterAnySpecial = formToolkit.createLabel(grpSpecialMonitoringRequests,
				"Any special monitoring requests (e.g. Negative crawls, high-profile broadcasts or discrepancies, etc...)",
				SWT.WRAP);
		lblEnterAnySpecial.setBounds(10, 115, 310, 30);

		Label lblTakedownEventsIn = formToolkit.createLabel(grpTakedowns,
				"Takedown events in the next 48 hours, restore events, or upcoming channel launch proceedings. (e.g. takedowns, temp/permanent restores, files need ingested, etc...)",
				SWT.WRAP);
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
		txtName.setText(System.getProperty("user.name"));

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

		Label lblEaWaItx
				= formToolkit.createLabel(grpDailyChecklist, "EA_WA_ITX Playouts Operational/Accurate:", SWT.NONE);
		lblEaWaItx.setBounds(10, 25, 300, 15);

		Label lblEaWaItxPlayout
				= formToolkit.createLabel(grpDailyChecklist, "EAS System Function Checks (4+/shift):", SWT.NONE);
		lblEaWaItxPlayout.setBounds(10, 45, 300, 15);

		Label lblChannelLaunchActivities
				= formToolkit.createLabel(grpDailyChecklist, "Channel Launch Verification Spreadsheet:\t", SWT.NONE);
		lblChannelLaunchActivities.setBounds(10, 65, 300, 15);

		Label lblWeatherMaps
				= formToolkit.createLabel(grpDailyChecklist, "Daily SRF Agenda Verification:", SWT.NONE);
		lblWeatherMaps.setBounds(10, 85, 300, 15);

		Label lblInteractiveChecks
				= formToolkit.createLabel(grpDailyChecklist, "Perform interactive channel checks:", SWT.NONE);
		lblInteractiveChecks.setBounds(10, 105, 300, 15);

		Label lblDailySweeps = formToolkit.createLabel(grpDailyChecklist, "Daily Lead Sweeps:", SWT.NONE);
		lblDailySweeps.setBounds(10, 125, 300, 15);

		Label lblMaintenanceSignOff
				= formToolkit.createLabel(grpDailyChecklist, "Maintenance tickets signed off:", SWT.NONE);
		lblMaintenanceSignOff.setBounds(10, 145, 300, 15);

		Label lblMidsOnly = formToolkit.createLabel(grpDailyChecklist, "Mids only:", SWT.NONE);
		lblMidsOnly.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.BOLD));
		lblMidsOnly.setBounds(10, 175, 70, 20);

		Label lblMaintenanceRestored
				= formToolkit.createLabel(grpDailyChecklist, "Verify restoration of scheduled maintenance:\t", SWT.NONE);
		lblMaintenanceRestored.setBounds(10, 195, 300, 15);

		Label lblDaysOnly = formToolkit.createLabel(grpDailyChecklist, "Days only:", SWT.NONE);
		lblDaysOnly.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.BOLD));
		lblDaysOnly.setBounds(10, 225, 70, 20);

		Label lblTurnerservices
				= formToolkit.createLabel(grpDailyChecklist, "Update the STB Turner Services every Thursday:", SWT.NONE);
		lblTurnerservices.setBounds(10, 245, 300, 15);

		Label lblPreliminaryKciReport
				= formToolkit.createLabel(grpDailyChecklist, "Respond to the Preliminary KCI Report:", SWT.NONE);
		lblPreliminaryKciReport.setBounds(10, 265, 300, 15);

		Label lblSkdlActivation
				= formToolkit.createLabel(grpDailyChecklist, "Ensure SKDL alarms have been uninhibited at 09:00 MT:", SWT.NONE);
		lblSkdlActivation.setBounds(10, 285, 300, 15);

		Label lblSwingsOnly = formToolkit.createLabel(grpDailyChecklist, "Swings only:", SWT.NONE);
		lblSwingsOnly.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.BOLD));
		lblSwingsOnly.setBounds(10, 315, 80, 20);

		Label lblMcSwitches
				= formToolkit.createLabel(grpDailyChecklist, "Verify takedown router logs for 5PM MT takedowns:", SWT.NONE);
		lblMcSwitches.setBounds(10, 335, 300, 15);

		Button btnEaWaItxComplete = new Button(grpDailyChecklist, SWT.CHECK);
		btnEaWaItxComplete.setSelection(obj.eaWaItxComplete);
		btnEaWaItxComplete.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				eaWaItxComplete = btnEaWaItxComplete.getSelection();
				setShellChanged(true);
				lblEditTime.setText(LocalDateTime.now().format(formatter));
			}
		});
		btnEaWaItxComplete.setBounds(338, 25, 93, 16);
		formToolkit.adapt(btnEaWaItxComplete, true, true);
		btnEaWaItxComplete.setText("Complete");
		btnEaWaItxComplete.setSelection(obj.eaWaItxComplete);

		Button btnEaWaItxPlayoutComplete = new Button(grpDailyChecklist, SWT.CHECK);
		btnEaWaItxPlayoutComplete.setSelection(obj.eaWaItxPlayoutComplete);
		btnEaWaItxPlayoutComplete.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				eaWaItxPlayoutComplete = btnEaWaItxPlayoutComplete.getSelection();
				setShellChanged(true);
				lblEditTime.setText(LocalDateTime.now().format(formatter));
			}
		});
		btnEaWaItxPlayoutComplete.setBounds(338, 45, 93, 16);
		formToolkit.adapt(btnEaWaItxPlayoutComplete, true, true);
		btnEaWaItxPlayoutComplete.setText("Complete");
		btnEaWaItxPlayoutComplete.setSelection(obj.eaWaItxPlayoutComplete);

		Button btnChannelLaunchComplete = new Button(grpDailyChecklist, SWT.CHECK);
		btnChannelLaunchComplete.setSelection(obj.channelLaunchComplete);
		btnChannelLaunchComplete.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				channelLaunchComplete = btnChannelLaunchComplete.getSelection();
				setShellChanged(true);
				lblEditTime.setText(LocalDateTime.now().format(formatter));
			}
		});
		btnChannelLaunchComplete.setBounds(338, 65, 93, 16);
		formToolkit.adapt(btnChannelLaunchComplete, true, true);
		btnChannelLaunchComplete.setText("Complete");
		btnChannelLaunchComplete.setSelection(obj.channelLaunchComplete);

		Button btnWeatherComplete = new Button(grpDailyChecklist, SWT.CHECK);
		btnWeatherComplete.setSelection(obj.weatherComplete);
		btnWeatherComplete.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				weatherComplete = btnWeatherComplete.getSelection();
				setShellChanged(true);
				lblEditTime.setText(LocalDateTime.now().format(formatter));
			}
		});
		btnWeatherComplete.setBounds(338, 85, 93, 16);
		formToolkit.adapt(btnWeatherComplete, true, true);
		btnWeatherComplete.setText("Complete");
		btnWeatherComplete.setSelection(obj.weatherComplete);

		Button btnInteractiveComplete = new Button(grpDailyChecklist, SWT.CHECK);
		btnInteractiveComplete.setSelection(obj.interactiveComplete);
		btnInteractiveComplete.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				interactiveComplete = btnInteractiveComplete.getSelection();
				setShellChanged(true);
				lblEditTime.setText(LocalDateTime.now().format(formatter));
			}
		});
		btnInteractiveComplete.setBounds(338, 105, 93, 16);
		formToolkit.adapt(btnInteractiveComplete, true, true);
		btnInteractiveComplete.setText("Complete");
		btnInteractiveComplete.setSelection(obj.interactiveComplete);

		Button btnDailySweepsComplete = new Button(grpDailyChecklist, SWT.CHECK);
		btnDailySweepsComplete.setSelection(obj.dailySweeps);
		btnDailySweepsComplete.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				dailySweeps = btnDailySweepsComplete.getSelection();
				setShellChanged(true);
				lblEditTime.setText(LocalDateTime.now().format(formatter));
			}
		});
		btnDailySweepsComplete.setBounds(338, 125, 93, 16);
		formToolkit.adapt(btnDailySweepsComplete, true, true);
		btnDailySweepsComplete.setText("Complete");
		btnDailySweepsComplete.setSelection(obj.dailySweeps);

		Button btnMaintenanceSignOff = new Button(grpDailyChecklist, SWT.CHECK);
		btnMaintenanceSignOff.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				maintenanceSigned = btnMaintenanceSignOff.getSelection();
				setShellChanged(true);
				lblEditTime.setText(LocalDateTime.now().format(formatter));
			}
		});
		btnMaintenanceSignOff.setBounds(338, 145, 93, 16);
		formToolkit.adapt(btnMaintenanceSignOff, true, true);
		btnMaintenanceSignOff.setText("Complete");
		btnMaintenanceSignOff.setSelection(obj.maintenanceSigned);

		Button btnMaintenanceComplete = new Button(grpDailyChecklist, SWT.CHECK);
		if(shift == "Days" || shift == "Swings") {
			btnMaintenanceComplete.setSelection(true);
			btnMaintenanceComplete.setEnabled(false);
			maintenanceComplete = true;
		}
		btnMaintenanceComplete.setSelection(obj.maintenanceComplete);
		btnMaintenanceComplete.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				maintenanceComplete = btnMaintenanceComplete.getSelection();
				setShellChanged(true);
				lblEditTime.setText(LocalDateTime.now().format(formatter));
			}
		});
		btnMaintenanceComplete.setBounds(338, 195, 93, 16);
		formToolkit.adapt(btnMaintenanceComplete, true, true);
		btnMaintenanceComplete.setText("Complete");
		btnMaintenanceComplete.setSelection(obj.maintenanceComplete);

		Button btnTurnerComplete = new Button(grpDailyChecklist, SWT.CHECK);
		btnTurnerComplete.setSelection(obj.turnerComplete);
		btnTurnerComplete.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				turnerComplete = btnTurnerComplete.getSelection();
				setShellChanged(true);
				lblEditTime.setText(LocalDateTime.now().format(formatter));
			}
		});
		btnTurnerComplete.setBounds(338, 245, 93, 16);
		formToolkit.adapt(btnTurnerComplete, true, true);
		btnTurnerComplete.setText("Complete");
		btnTurnerComplete.setSelection(obj.turnerComplete);

		Button btnPreliminaryKciComplete = new Button(grpDailyChecklist, SWT.CHECK);
		btnPreliminaryKciComplete.setSelection(obj.kciComplete);
		btnPreliminaryKciComplete.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				preliminaryKciComplete = btnPreliminaryKciComplete.getSelection();
				setShellChanged(true);
				lblEditTime.setText(LocalDateTime.now().format(formatter));
			}
		});
		btnPreliminaryKciComplete.setBounds(338, 265, 93, 16);
		formToolkit.adapt(btnPreliminaryKciComplete, true, true);
		btnPreliminaryKciComplete.setText("Complete");
		btnPreliminaryKciComplete.setSelection(obj.kciComplete);

		Button btnSkdlComplete = new Button(grpDailyChecklist, SWT.CHECK);
		if(shift == "Swings" || shift == "Mids") {
			btnTurnerComplete.setSelection(true);
			btnTurnerComplete.setEnabled(false);
			turnerComplete = true;
			
			btnPreliminaryKciComplete.setSelection(true);
			btnPreliminaryKciComplete.setEnabled(false);
			preliminaryKciComplete = true;
			
			btnSkdlComplete.setSelection(true);
			btnSkdlComplete.setEnabled(false);
			skdlComplete = true;
		}
		btnSkdlComplete.setSelection(obj.skdlComplete);
		btnSkdlComplete.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				skdlComplete = btnSkdlComplete.getSelection();
				setShellChanged(true);
				lblEditTime.setText(LocalDateTime.now().format(formatter));
			}
		});
		btnSkdlComplete.setBounds(338, 285, 93, 16);
		formToolkit.adapt(btnSkdlComplete, true, true);
		btnSkdlComplete.setText("Complete");
		btnSkdlComplete.setSelection(obj.skdlComplete);

		Button btnMcSwitchesComplete = new Button(grpDailyChecklist, SWT.CHECK);
		if(shift == "Days" || shift == "Mids") {
			btnMcSwitchesComplete.setSelection(true);
			btnMcSwitchesComplete.setEnabled(false);
			mcSwitchesComplete = true;
		}
		btnMcSwitchesComplete.setSelection(obj.mcSwitchesComplete);
		btnMcSwitchesComplete.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				mcSwitchesComplete = btnMcSwitchesComplete.getSelection();
				setShellChanged(true);
				lblEditTime.setText(LocalDateTime.now().format(formatter));
			}
		});
		btnMcSwitchesComplete.setBounds(338, 335, 93, 16);
		formToolkit.adapt(btnMcSwitchesComplete, true, true);
		btnMcSwitchesComplete.setText("Complete");
		btnMcSwitchesComplete.setSelection(obj.mcSwitchesComplete);

		Label lblOncomingLead = formToolkit.createLabel(scrldfrmCheyenneTocLead.getBody(), "Oncoming Lead:", SWT.NONE);
		lblOncomingLead.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.BOLD));
		lblOncomingLead.setBounds(730, 537, 100, 20);

		txtOncomingLead = formToolkit.createText(scrldfrmCheyenneTocLead.getBody(), "New Text", SWT.NONE);
		txtOncomingLead.setText(obj.oncomingLead);
		txtOncomingLead.setEditable(true);
		txtOncomingLead.setBounds(730, 562, 200, 20);
		txtOncomingLead.addModifyListener(modListener);

		txtCallins = formToolkit.createText(scrldfrmCheyenneTocLead.getBody(), "", SWT.NONE);
		txtCallins.setText(obj.callins);
		txtCallins.setBounds(130, 625, 477, 21);
		txtCallins.addModifyListener(modListener);

		Button btnPassdownAccepted = new Button(scrldfrmCheyenneTocLead.getBody(), SWT.CHECK);
		btnPassdownAccepted.setEnabled(true);
//		btnPassdownAccepted.setSelection(passdownAccepted);
		btnPassdownAccepted.setBounds(940, 542, 127, 20);
		formToolkit.adapt(btnPassdownAccepted, true, true);
		btnPassdownAccepted.setText("Passdown Accepted");

		Button btnPassdownDeclined = new Button(scrldfrmCheyenneTocLead.getBody(), SWT.CHECK);
		btnPassdownDeclined.setEnabled(true);
//		btnPassdownAccepted.setSelection(passdownDeclined);
		btnPassdownDeclined.setBounds(940, 567, 127, 20);
		formToolkit.adapt(btnPassdownDeclined, true, true);
		btnPassdownDeclined.setText("Passdown Declined");

		btnPassdownAccepted.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				passdownAccepted = btnPassdownAccepted.getSelection();
				btnPassdownDeclined.setSelection(false);
				passdownDeclined = btnPassdownDeclined.getSelection();
				setShellChanged(true);
				lblEditTime.setText(LocalDateTime.now().format(formatter));
			}
		});
		btnPassdownDeclined.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				passdownDeclined = btnPassdownDeclined.getSelection();
				btnPassdownAccepted.setSelection(false);
				passdownAccepted = btnPassdownAccepted.getSelection();
				setShellChanged(true);
				lblEditTime.setText(LocalDateTime.now().format(formatter));
			}
		});

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

		combo = new Combo(scrldfrmCheyenneTocLead.getBody(), SWT.READ_ONLY);
		combo.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				mocIndex = combo.getSelectionIndex();
				setShellChanged(true);
				lblEditTime.setText(LocalDateTime.now().format(formatter));
			}
		});
		combo.setBounds(823, 10, 333, 23);
		combo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		formToolkit.adapt(combo);
		formToolkit.paintBordersFor(combo);
		for (int i = 0; i < mocArray.length; i++) {
			if (!mocArray[i].contentEquals(" ")) {
				combo.add(mocArray[i]);
			}
		}
		combo.select(mocIndex);

		combo_1 = new Combo(scrldfrmCheyenneTocLead.getBody(), SWT.READ_ONLY);
		combo_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				odsIndex = combo_1.getSelectionIndex();
				setShellChanged(true);
				lblEditTime.setText(LocalDateTime.now().format(formatter));
				System.out.println(odsIndex);
			}
		});
		combo_1.setBounds(823, 41, 167, 23);
		combo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		formToolkit.adapt(combo_1);
		formToolkit.paintBordersFor(combo_1);
		combo_1.add("Cheyenne");
		combo_1.add("Gilbert");
		combo_1.select(odsIndex);

		Label lblMoc = formToolkit.createLabel(scrldfrmCheyenneTocLead.getBody(), "MOC:", SWT.NONE);
		lblMoc.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.BOLD));
		lblMoc.setBounds(780, 10, 44, 23);

		Label lblOds = formToolkit.createLabel(scrldfrmCheyenneTocLead.getBody(), "ODS:", SWT.NONE);
		lblOds.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.BOLD));
		lblOds.setBounds(780, 40, 44, 23);

		Label lblCallins = formToolkit.createLabel(scrldfrmCheyenneTocLead.getBody(), "Call-Ins:", SWT.NONE);
		lblCallins.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.BOLD));
		lblCallins.setBounds(65, 625, 62, 20);

		new Label(this, SWT.NONE);

		Menu menu = new Menu(this, SWT.BAR);
		setMenuBar(menu);
		
		MenuItem mntmFile = new MenuItem(menu, SWT.CASCADE);
		mntmFile.setText("&File");

		Menu menu_1 = new Menu(mntmFile);
		mntmFile.setMenu(menu_1);
		
		MenuItem mntmSaveReport = new MenuItem(menu_1, SWT.NONE);
		mntmSaveReport.setAccelerator(SWT.MOD1 + 's');
		mntmSaveReport.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					save();
				} catch (IOException | URISyntaxException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
	
		mntmSaveReport.setText("&Save Report\tCtrl+S");
		
		MenuItem mntmQuit = new MenuItem(menu_1, SWT.NONE);
		mntmQuit.setAccelerator(SWT.MOD1 + 'q');
		mntmQuit.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				quit();
			}
		});
		mntmQuit.setText("&Quit\tCtrl+Q");

		Button btnSave = formToolkit.createButton(scrldfrmCheyenneTocLead.getBody(), "Save and Close", SWT.PUSH);
		btnSave.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (btnPassdownAccepted.getSelection() || btnPassdownDeclined.getSelection()) {
					if (btnPassdownDeclined.getSelection() == true) {
						declinedReasoning = JOptionPane.showInputDialog(null, "Why was the passdown declined?",
								"Passdown Declined Justification", 0);
						if ((declinedReasoning == null || (declinedReasoning != null && ("".equals(declinedReasoning))))) {
							JOptionPane.showMessageDialog(null, "A reason must be provided in order to decline the passdown");
						} else {
							try {
								saveAndClose();
							} catch (IOException | URISyntaxException e1) {
									e1.printStackTrace();
							}
						}
					} else {
						try {
							saveAndClose();
						} catch (IOException | URISyntaxException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				} else {
					JOptionPane.showMessageDialog(null, "You must accept the passdown or decline it.");
				}
			}
		});

		btnSave.setForeground(SWTResourceManager.getColor(0, 0, 0));
		btnSave.setBounds(1070, 552, 120, 25);
		btnSave.setBackground(display.getSystemColor(SWT.COLOR_GRAY));
		
		ToolBar toolBar = new ToolBar(scrldfrmCheyenneTocLead.getBody(), SWT.CENTER);
		toolBar.setBounds(-8, 675, 24, 22);
		formToolkit.adapt(toolBar);
		formToolkit.paintBordersFor(toolBar);
		ToolItem toolItem = new ToolItem(toolBar, SWT.PUSH);
		toolItem.setWidth(5);
		toolItem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				btnEaWaItxComplete.setSelection(true);
				btnEaWaItxComplete.notifyListeners(SWT.Selection, new Event());
				
				btnEaWaItxPlayoutComplete.setSelection(true);
				btnEaWaItxPlayoutComplete.notifyListeners(SWT.Selection, new Event());
				
				btnChannelLaunchComplete.setSelection(true);
				btnChannelLaunchComplete.notifyListeners(SWT.Selection, new Event());
				
				btnWeatherComplete.setSelection(true);
				btnWeatherComplete.notifyListeners(SWT.Selection, new Event());
				
				btnInteractiveComplete.setSelection(true);
				btnInteractiveComplete.notifyListeners(SWT.Selection, new Event());
				
				btnDailySweepsComplete.setSelection(true);
				btnDailySweepsComplete.notifyListeners(SWT.Selection, new Event());
				
				btnMaintenanceSignOff.setSelection(true);
				btnMaintenanceSignOff.notifyListeners(SWT.Selection, new Event());
				
				btnMaintenanceComplete.setSelection(true);
				btnMaintenanceComplete.notifyListeners(SWT.Selection, new Event());
				
				btnTurnerComplete.setSelection(true);
				btnTurnerComplete.notifyListeners(SWT.Selection, new Event());
				
				btnPreliminaryKciComplete.setSelection(true);
				btnPreliminaryKciComplete.notifyListeners(SWT.Selection, new Event());
				
				btnSkdlComplete.setSelection(true);
				btnSkdlComplete.notifyListeners(SWT.Selection, new Event());
				
				btnMcSwitchesComplete.setSelection(true);
				btnMcSwitchesComplete.notifyListeners(SWT.Selection, new Event());
			}
		});
		
		
		createContents();
	}

	// This will create the columns for the table
	private void createColumns(TableViewer tableViewer) {

		TableViewerColumn colName = new TableViewerColumn(tableViewer, SWT.NONE);
		colName.getColumn().setWidth(200);
		colName.getColumn().setText("Name");
//		colName.setLabelProvider(new ColumnLabelProvider() {
//			@Override
//			// TODO:
//			public String getText(Object element) {
//				return null;
//			}
//		});
	}
	// Create contents of the shell
	protected void createContents() {
		setText("Report Viewer Filled");
		setSize(1300, 2000);

	}

	protected void saveAndClose() throws IOException, URISyntaxException {
		DataObject obj = new DataObject();
		File oldBackup = new File(BACKUP_FILE_PATH);
		oldBackup.delete();
		Files.copy(new File(FILE_PATH).toPath(), new File(BACKUP_FILE_PATH).toPath());
		File oldData = new File(FILE_PATH);
		oldData.renameTo(new File(BACKUP_FILE_PATH));

		File data = new File("src\\datastore\\passdown_datastore.pd");

		DateTimeFormatter date = DateTimeFormatter.ofPattern("M/d/yyyy");
//		DateTimeFormatter time = DateTimeFormatter.ofPattern("HH:mm");
		LocalDateTime now;
		StringBuilder sb = new StringBuilder();
		if (shift == "Mids" && LocalTime.now().isAfter(LocalTime.of(23, 00))) {
			now = LocalDateTime.now().plusDays(1);
		} else {
			now = LocalDateTime.now();
		}
		String formattedDate = date.format(now);
//		String formattedTime = time.format(now);
		sb.append(formattedDate + ";--" + txtName.getText() + ";--" + txtShift.getText() + ";--");

		for (int i = 0; i < buttonValues.length; i++) {
			sb.append(buttonValues[i] + ";--");
			if (buttonValues[i] == true) {
				obj.setEmployees(i, true);
			} else {
				obj.setEmployees(i, false);
			}
		}


		FileWriter writer = new FileWriter(data, true);

		// Opens the passdown_datastore.pd file with read/write capabilities
		RandomAccessFile f = new RandomAccessFile(data, "rw");
		long length = f.length() - 1; // Gets the length of the passdown file
		byte b;
		do { // searches backwards through the file looking for a new line character
			length -= 1;
			f.seek(length);
			b = f.readByte();
		} while (b != 10);

		f.setLength(length + 1); // truncate the file at the new line character + 1
		f.close();
		
		String writeString = sb.toString().replaceAll("[\\r\\n]", "%%");
		writeString += "\n";
		setShellChanged(false);

		// Write the string to the file
		writer.write(writeString);
		writer.close();
		this.close();

		String line = sb.toString();
		String[] splitLine = line.split(";--");
		obj = Code.setDataObject(obj, splitLine);
		com.dish.forms.HtmlEmailSender.sendEMail(obj);
	}

	protected void save() throws IOException, URISyntaxException {
		DataObject obj = new DataObject();
		File oldBackup = new File(BACKUP_FILE_PATH);
		oldBackup.delete();
		Files.copy(new File(FILE_PATH).toPath(), new File(BACKUP_FILE_PATH).toPath());
		File oldData = new File(FILE_PATH);
		oldData.renameTo(new File(BACKUP_FILE_PATH));

		File data = new File("src\\datastore\\passdown_datastore.pd");

		DateTimeFormatter date = DateTimeFormatter.ofPattern("M/d/yyyy");
		DateTimeFormatter time = DateTimeFormatter.ofPattern("HH:mm");
		LocalDateTime now;
		StringBuilder sb = new StringBuilder();
		if (shift == "Mids" && LocalTime.now().isAfter(LocalTime.of(23, 00))) {
			now = LocalDateTime.now().plusDays(1);
		} else {
			now = LocalDateTime.now();
		}
		String formattedDate = date.format(now);
		String formattedTime = time.format(now);
		sb.append(formattedDate + ";--" + txtName.getText() + ";--" + txtShift.getText() + ";--");

		for (int i = 0; i < buttonValues.length; i++) {
			sb.append(buttonValues[i] + ";--");
			if (buttonValues[i] == true) {
				obj.setEmployees(i, true);
			} else {
				obj.setEmployees(i, false);
			}
		}

		sb.append(";--" + formattedTime + ";--" + lblEditTime.getText());
		sb.append(";--" + declinedReasoning + ";--" + combo.getText());
		sb.append(";--" + mocIndex + ";--" + combo_1.getText() + ";--" + odsIndex + ";--" + txtCallins.getText() + ";--"
				+ maintenanceSigned + ";--");
		
		// Opens the passdown_datastore.pd file with read/write capabilities
		RandomAccessFile f = new RandomAccessFile(data, "rw");
		long length = f.length() - 1; // Gets the length of the passdown file
		byte b;
		do { // searches backwards through the file looking for a new line character
			length -= 1;
			f.seek(length);
			b = f.readByte();
		} while (b != 10);

		f.setLength(length + 1); // truncate the file at the new line character + 1
		f.close();
		
//		sb.append("\n");
		String writeString = sb.toString().replaceAll("[\\n\\r]", "%%");
		FileWriter writer = new FileWriter(data, true);
		writeString += "\n";
		setShellChanged(false);
		writer.write(writeString);
		writer.close();

		String line = sb.toString();
		String[] splitLine = line.split(";--");
//		for(int i = 0; i < splitLine.length; i++) {
//			System.out.println(splitLine[i]);
//		}
		obj = Code.setDataObject(obj, splitLine);
//		System.out.println(obj);

		Toast toast = new Toast("File Saved", 150, 400);
		toast.showToast();
	}
	
	protected void quit() {
		this.close();
	}
	
	@Override
	public void close() {
		// TODO Auto-generated method stub
		super.close();
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

	protected static void setDate(LocalDate d) {
		date = d;
	}

	protected static LocalDate getDate() {
		return date;
	}

	// Setter for shift
	protected static void setShift(String shift) {
		ReportEntryFilled.shift = shift;
	}

	// Getter for shift
	protected static String getShift() {
		return ReportEntryFilled.shift;
	}

	protected static void setShellChanged(boolean bool) {
		shellChanged = bool;
	}

	protected boolean getShellChanged() {
		// TODO Auto-generated method stub
		return shellChanged;
	}
}
