package view;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

import java.time.LocalDate;

public class MainShell extends Shell {
    private LocalDate date;
    private String shift;
    private String time;

    // Buttons
    private Button btnNewReportEntry;
    private Button btnPreviousReports;

    // Menu items
    private MenuItem mntmQuit;
    private MenuItem mntmEditDays;
    private MenuItem mntmEditSwings;
    private MenuItem mntmEditMids;
    private MenuItem mntmEditMoc;

    public MainShell(Display display) {
        super(display, SWT.SHELL_TRIM);
        setSize(400, 300);
        setMinimumSize(400,300);
        centerOnScreen();
        createContents();
    }

    private void createContents() {
        setText("Lead Tech Reporting Service");

        GridLayout gridLayout = new GridLayout(1, true);
        gridLayout.marginTop = 10;
        gridLayout.marginLeft = 100;
        setLayout(gridLayout);

        date = LocalDate.now();
        shift = "Mids";
        if (!"Mids".equals(shift)) {
            time = date.getMonthValue() + "/" + date.getDayOfMonth() + "/" + date.getYear();
        } else {
            time = date.getMonthValue() + "/" + (date.getDayOfMonth() + 1) + "/" + date.getYear();
        }

        new Label(this, SWT.NONE); // spacer

        // Button: New Report Entry
        btnNewReportEntry = new Button(this, SWT.NONE);
        GridData gdBtnNewReportEntry = new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1);
        gdBtnNewReportEntry.widthHint = 175;
        gdBtnNewReportEntry.heightHint = 75;
        btnNewReportEntry.setLayoutData(gdBtnNewReportEntry);
        btnNewReportEntry.setText("New Report Entry");

        // Button: Review Previous Shift Reports
        btnPreviousReports = new Button(this, SWT.NONE);
        GridData gdBtnPreviousReports = new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1);
        gdBtnPreviousReports.widthHint = 175;
        gdBtnPreviousReports.heightHint = 75;
        btnPreviousReports.setLayoutData(gdBtnPreviousReports);
        btnPreviousReports.setText("Review Previous Shift Reports");

        new Label(this, SWT.NONE); // spacer

        // Menu Bar
        Menu menuBar = new Menu(this, SWT.BAR);
        setMenuBar(menuBar);

        // File Menu
        MenuItem mntmFile = new MenuItem(menuBar, SWT.CASCADE);
        mntmFile.setText("&File");
        Menu menuFile = new Menu(mntmFile);
        mntmFile.setMenu(menuFile);

        mntmQuit = new MenuItem(menuFile, SWT.NONE);
        mntmQuit.setText("&Quit\tCtrl+Q");
        mntmQuit.setAccelerator(SWT.MOD1 + 'q');

        // Config Menu
        MenuItem mntmConfig = new MenuItem(menuBar, SWT.CASCADE);
        mntmConfig.setText("&Config");
        Menu menuConfig = new Menu(mntmConfig);
        mntmConfig.setMenu(menuConfig);

        // Edit Days
        mntmEditDays = new MenuItem(menuConfig, SWT.NONE);
        mntmEditDays.setText("Edit Days");

        // Edit Swings
        mntmEditSwings = new MenuItem(menuConfig, SWT.NONE);
        mntmEditSwings.setText("Edit Swings");

        // Edit Mids
        mntmEditMids = new MenuItem(menuConfig, SWT.NONE);
        mntmEditMids.setText("Edit Mids");

        // Edit MOC
        mntmEditMoc = new MenuItem(menuConfig, SWT.NONE);
        mntmEditMoc.setText("Edit MOC");
    }

    private void centerOnScreen() {
        Display display = getDisplay();
        Rectangle screenBounds = display.getPrimaryMonitor().getBounds(); // Get screen size

        
        int x = (screenBounds.width - getSize().x) / 2;
        int y = (screenBounds.height - getSize().y) / 2;

        setLocation(x, y);
    }
    
    @Override
    protected void checkSubclass() {
        // Disable subclass check
    }

    // Getters for the buttons so the controller can attach listeners
    public Button getBtnNewReportEntry() {
        return btnNewReportEntry;
    }

    public Button getBtnPreviousReports() {
        return btnPreviousReports;
    }

    // Getters for menu items
    public MenuItem getMntmQuit() {
        return mntmQuit;
    }

    public MenuItem getMntmEditDays() {
        return mntmEditDays;
    }

    public MenuItem getMntmEditSwings() {
        return mntmEditSwings;
    }

    public MenuItem getMntmEditMids() {
        return mntmEditMids;
    }

    public MenuItem getMntmEditMoc() {
        return mntmEditMoc;
    }

    // Accessor methods if the controller needs date/shift/time
    public LocalDate getDateValue() {
        return date;
    }

    public String getShift() {
        return shift;
    }

    public String getTimeValue() {
        return time;
    }
}
