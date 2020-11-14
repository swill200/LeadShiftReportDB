package main;

import java.io.IOException;
import java.time.LocalDate;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.wb.swt.SWTResourceManager;

import forms.ReportEntry;
import forms.ReportEntryFilled;

public class Main extends Shell {
	private static LocalDate date;
	private static String shift;
	private static String time;
	private static Main shell;

	/**
	 * Launch the application.
	 * 
	 * @param args
	 */
	public static void main(String args[]) {
		try {
			Display display = Display.getDefault();
			shell = new Main(display);
			shell.open();
			shell.setLocation(300, 200);
			shell.layout();
			while (!shell.isDisposed()) {
				if (!display.readAndDispatch()) {
					display.sleep();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		

	}

	/**
	 * Create the shell.
	 * 
	 * @param display
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws ClassNotFoundException
	 */
	public Main(Display display) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		super(display, SWT.SHELL_TRIM);
		setImage(SWTResourceManager.getImage(Main.class, "/datastore/icons8-exchange-48.ico"));
		GridLayout gridLayout = new GridLayout(1, true);
		gridLayout.marginTop = 10;
		gridLayout.marginLeft = 100;
		setLayout(gridLayout);
		new Label(this, SWT.NONE);
		setDate(LocalDate.now());
		setShift(Code.getShiftText());
		if (shift != "Mids") {
			setTime((getDate().getMonthValue()) + "/" + getDate().getDayOfMonth() + "/" + getDate().getYear());
		} else {
			setTime((getDate().getMonthValue()) + "/" + (getDate().getDayOfMonth() + 1) + "/" + getDate().getYear());
		}
		// Button to create a new report
		Button btnNewReportEntry = new Button(this, SWT.NONE);
		GridData gd_btnNewReportEntry = new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1);
		btnNewReportEntry.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				setShellVisible(false);
				try {
					Code.getPassdownData(getTime(), getShift());
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				if (!Code.getOk()) {
					setShift(Code.getShiftText());
					ReportEntry newReport = new ReportEntry(display);
					newReport.open();
					newReport.addListener(SWT.Dispose, new Listener() {
						// Handle dispose event for new report window.
						// Main.this.setActive() brings the main window back into focus.
						@Override
						public void handleEvent(Event arg0) {
							// TODO Auto-generated method stub
							setShellVisible(true);
							Main.this.setActive();
						}
					});
				} else {
					ReportEntryFilled newReport;
					try {
						setShift(Code.getShiftText());
						newReport = new ReportEntryFilled(display, getDate(), getShift());
						newReport.open();
						newReport.addListener(SWT.Dispose, new Listener() {
							// Handle dispose event for the filled report window.
							// Main.this.setActive() brings the main window back into focus.
							@Override
							public void handleEvent(Event arg0) {
								setShellVisible(true);
								Main.this.setActive();
							}
						});
					} catch (IllegalArgumentException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IllegalAccessException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});

		gd_btnNewReportEntry.widthHint = 175;
		gd_btnNewReportEntry.heightHint = 75;
		btnNewReportEntry.setLayoutData(gd_btnNewReportEntry);
		btnNewReportEntry.setText("New Report Entry");

		// Button to view existing reports
		Button btnPreviousReports = new Button(this, SWT.NONE | SWT.WRAP);
		GridData gd_btnPreviousReports = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		btnPreviousReports.setText("Review Previous Shift Reports");
		gd_btnPreviousReports.widthHint = 175;
		gd_btnPreviousReports.heightHint = 75;
		btnPreviousReports.setLayoutData(gd_btnPreviousReports);
		btnPreviousReports.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(Event event) {
				setShellVisible(false);
				DateSelection dateSelection = new DateSelection(display);
				dateSelection.open();
				dateSelection.addListener(SWT.Dispose, new Listener() {

					// Handle dispose event for previous report window.
					// Main.this.setActive() brings the main window back into focus.
					@Override
					public void handleEvent(Event arg0) {
						// TODO Auto-generated method stub
						setShellVisible(true);
						Main.this.setActive();
					}

				});
			}

		});

		new Label(this, SWT.NONE);
		
		Menu menu = new Menu(this, SWT.BAR);
		setMenuBar(menu);
		
		MenuItem mntmFile = new MenuItem(menu, SWT.CASCADE);
		mntmFile.setText("&File");
		mntmFile.setAccelerator(SWT.MOD2 + 'f');
		
		Menu menu_2 = new Menu(mntmFile);
		mntmFile.setMenu(menu_2);
		
		MenuItem mntmQuit = new MenuItem(menu_2, SWT.NONE);
		mntmQuit.setAccelerator(SWT.MOD1 + 'q');
		mntmQuit.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				quit();
			}
		});
		mntmQuit.setText("&Quit\tCtrl+Q");
		
		MenuItem mntmConfig = new MenuItem(menu, SWT.CASCADE);
		mntmConfig.setText("&Config");
		mntmFile.setAccelerator(SWT.MOD2 + 'c');
		
		Menu menu_1 = new Menu(mntmConfig);
		mntmConfig.setMenu(menu_1);
		
		MenuItem mntmEditDays = new MenuItem(menu_1, SWT.NONE);
		mntmEditDays.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				ProcessBuilder pb = new ProcessBuilder("Notepad.exe", "src\\datastore\\shift_days_employees");
				try {
					pb.start();
				} catch (IOException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
			}
		});
		mntmEditDays.setText("Edit Days");
		
		MenuItem mntmEditSwings = new MenuItem(menu_1, SWT.NONE);
		mntmEditSwings.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				ProcessBuilder pb = new ProcessBuilder("Notepad.exe", "src\\datastore\\shift_swings_employees");
				try {
					pb.start();
				} catch (IOException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
			}
		});
		mntmEditSwings.setText("Edit Swings");
		
		MenuItem mntmEditMids = new MenuItem(menu_1, SWT.NONE);
		mntmEditMids.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				ProcessBuilder pb = new ProcessBuilder("Notepad.exe", "src\\datastore\\shift_mids_employees");
				try {
					pb.start();
				} catch (IOException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
			}
		});
		mntmEditMids.setText("Edit Mids");
		
		MenuItem mntmEditMoc = new MenuItem(menu_1, SWT.NONE);
		mntmEditMoc.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				ProcessBuilder pb = new ProcessBuilder("Notepad.exe", "src\\datastore\\moc_list");
				try {
					pb.start();
				} catch (IOException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
			}
		});
		mntmEditMoc.setText("Edit MOC");

		createContents();
	}

	private void setTime(String timeSet) {
		time = timeSet;
	}

	private String getTime() {
		return time;
	}

	/**
	 * Create contents of the shell.
	 */
	protected void createContents() {
		setText("Lead Tech Reporting Service");
		setSize(400, 300);

	}

	protected void quit() {
		this.close();
	}
	
	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

	protected static void setDate(LocalDate date) {
		Main.date = date;
	}

	protected static LocalDate getDate() {
		return date;
	}

	public static void setShift(String shift) {
		Main.shift = shift;
	}

	protected static String getShift() {
		return shift;
	}

	protected void setShellVisible(boolean bool) {
		Main.shell.setVisible(bool);
	}
}
