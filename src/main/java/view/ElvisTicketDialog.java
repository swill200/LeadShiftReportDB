package view;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

import util.ShellUtils;

public class ElvisTicketDialog extends Shell {
    private Text txtService;
    private Text txtTicketNumber;
    private boolean isOkPressed = false;
    private String service;
    private String ticketNumber;

    public ElvisTicketDialog(Shell parent) {
        super(parent, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
        setText("Elvis Ticket");
        setSize(400, 200);
        setLayout(new GridLayout(2, false));
        ShellUtils.centerOnParent(parent, this);
        createContents();
    }

    private void createContents() {
        // Service Label and Text Field
        Label lblService = new Label(this, SWT.NONE);
        lblService.setText("Service:");
        txtService = new Text(this, SWT.BORDER);
        txtService.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

        // Ticket Number Label and Text Field
        Label lblTicketNumber = new Label(this, SWT.NONE);
        lblTicketNumber.setText("Ticket Number:");
        txtTicketNumber = new Text(this, SWT.BORDER);
        txtTicketNumber.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

        // Add a VerifyListener to restrict input to numbers only
        txtTicketNumber.addVerifyListener(new VerifyListener() {
            @Override
            public void verifyText(VerifyEvent e) {
                String currentText = txtTicketNumber.getText();
                String newText = currentText.substring(0, e.start) + e.text + currentText.substring(e.end);
                e.doit = newText.matches("\\d*");
            }
        });
        
        
        // Buttons
        Composite buttonComposite = new Composite(this, SWT.NONE);
        buttonComposite.setLayoutData(new GridData(SWT.RIGHT, SWT.BOTTOM, true, true, 2, 1));
        buttonComposite.setLayout(new GridLayout(2, true));

        Button btnOk = new Button(buttonComposite, SWT.PUSH);
        btnOk.setText("OK");
        btnOk.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
        btnOk.addListener(SWT.Selection, event -> {
            service = txtService.getText(); // Save data before closing
            ticketNumber = txtTicketNumber.getText();
            isOkPressed = true;
            close();
        });

        Button btnCancel = new Button(buttonComposite, SWT.PUSH);
        btnCancel.setText("Cancel");
        btnCancel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
        btnCancel.addListener(SWT.Selection, event -> close());
    }

    public String getService() {
        return service; // Return saved service
    }

    public String getTicketNumber() {
        return ticketNumber; // Return saved ticket number
    }

    public boolean isOkPressed() {
        return isOkPressed;
    }

    public void openDialog() {
        open();
        Display display = getDisplay();
        while (!isDisposed()) {
            if (!display.readAndDispatch()) {
                display.sleep();
            }
        }
    }

    // Add these methods to set the initial values
    public void setService(String service) {
        this.service = service;
        if (txtService != null) {
            txtService.setText(service != null ? service : "");
        }
    }

    public void setTicketNumber(String ticketNumber) {
        this.ticketNumber = ticketNumber;
        if (txtTicketNumber != null) {
            txtTicketNumber.setText(ticketNumber != null ? ticketNumber : "");
        }
    }

    @Override
    protected void checkSubclass() {
        // Allow subclassing
    }
}
