package main;

import java.io.IOException;
import java.time.LocalDate;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.wb.swt.SWTResourceManager;

import forms.PreviousReports;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;

public class DateSelection extends Shell {
	private final FormToolkit formToolkit = new FormToolkit(Display.getDefault());
	private static DateSelection shell;

	/**
	 * Launch the application.
	 * 
	 * @param args
	 */
	public static void main(String args[]) {
		try {
			Display display = Display.getDefault();
			shell = new DateSelection(display);
			shell.open();
			shell.setLocation(400, 400);
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
	 */
	public DateSelection(Display display) {
		super(display, SWT.SHELL_TRIM);
		setSize(400, 300);
		setImage(SWTResourceManager.getImage(DateSelection.class, "/datastore/icons8-exchange-48.ico"));
		setLayout(new FormLayout());

		Label lblSelectDateAnd = new Label(this, SWT.NONE);
		FormData fd_lblSelectDateAnd = new FormData();
		fd_lblSelectDateAnd.top = new FormAttachment(0, 10);
		fd_lblSelectDateAnd.left = new FormAttachment(0, 20);
		lblSelectDateAnd.setLayoutData(fd_lblSelectDateAnd);
		lblSelectDateAnd.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.BOLD));
		lblSelectDateAnd.setText("Select date and shift you would like to view:");

		// Calendar style date picker
		DateTime dateTime = new DateTime(this, SWT.BORDER | SWT.CALENDAR);
		dateTime.setForeground(SWTResourceManager.getColor(255, 255, 255));
		dateTime.setBackground(SWTResourceManager.getColor(255, 255, 255));
		FormData fd_dateTime = new FormData();
		fd_dateTime.left = new FormAttachment(lblSelectDateAnd, 0, SWT.LEFT);
		dateTime.setLayoutData(fd_dateTime);
		dateTime.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.BOLD));

		//
		Combo combo = new Combo(this, SWT.NONE);
		fd_dateTime.top = new FormAttachment(combo, 0, SWT.TOP);
		FormData fd_combo = new FormData();
		fd_combo.top = new FormAttachment(lblSelectDateAnd, 18);
		fd_combo.right = new FormAttachment(100, -28);
		fd_combo.left = new FormAttachment(dateTime, 15);
		combo.setLayoutData(fd_combo);
		combo.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.BOLD));
		combo.setItems(new String[] { "Mids", "Days", "Swings" });

		Button btnSubmit = formToolkit.createButton(this, "Submit", SWT.NONE);
		btnSubmit.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
		btnSubmit.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				int month = dateTime.getMonth();
				int day = dateTime.getDay();
				int year = dateTime.getYear();
				LocalDate date = null;
				date = LocalDate.of(year, month + 1, day);
				Main.setDate(date);
//				System.out.println((Main.date.getMonth()) + "/" + Main.date.getDayOfMonth() + "/" + Main.date.getYear());
//				System.out.println(date);
				Main.setShift(combo.getText());
//				System.out.println(combo.getText());
				PreviousReports newPrevious;
				try {
					newPrevious = new PreviousReports(display, Main.getDate(), Main.getShift());
					newPrevious.open();
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
		});
		FormData fd_btnSubmit = new FormData();
		fd_btnSubmit.bottom = new FormAttachment(100, -36);
		fd_btnSubmit.right = new FormAttachment(100, -28);
		fd_btnSubmit.left = new FormAttachment(dateTime, 16);
		btnSubmit.setLayoutData(fd_btnSubmit);
		btnSubmit.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.NORMAL));

		Label lblSubmit = formToolkit.createLabel(this, "Click submit when finished:", SWT.WRAP | SWT.CENTER);
		lblSubmit.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
		lblSubmit.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
		fd_btnSubmit.top = new FormAttachment(lblSubmit, 12);
		FormData fd_lblSubmit = new FormData();
		fd_lblSubmit.right = new FormAttachment(100, -28);
		fd_lblSubmit.left = new FormAttachment(dateTime, 15);
		fd_lblSubmit.top = new FormAttachment(combo, 49);
		combo.select(0);
		fd_lblSubmit.bottom = new FormAttachment(100, -79);
		lblSubmit.setLayoutData(fd_lblSubmit);
		
		Menu menu = new Menu(this, SWT.BAR);
		setMenuBar(menu);
		
		MenuItem mntmNewSubmenu = new MenuItem(menu, SWT.CASCADE);
		mntmNewSubmenu.setText("&File");
		mntmNewSubmenu.setAccelerator(SWT.MOD2 + 'f');
		Menu menu_1 = new Menu(mntmNewSubmenu);
		mntmNewSubmenu.setMenu(menu_1);
		
		MenuItem mntmQuit = new MenuItem(menu_1, SWT.NONE);
		mntmQuit.setAccelerator(SWT.MOD1 + 'q');
		mntmQuit.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				quit();
			}
		});
		mntmQuit.setText("&Quit\tCtrl+Q");

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
}
