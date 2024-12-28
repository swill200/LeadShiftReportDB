package view;

import model.Employee;
import util.ShellUtils;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Label;

public class AddEmployeeDialog extends Shell {
    private Text txtFirstName;
    private Text txtLastName;
    private Text txtTitle;
    private Employee newEmployee;

    public AddEmployeeDialog(Shell parent) {
        super(parent, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
        setText("Add New Employee");
        setSize(300, 200);
        ShellUtils.centerOnParent(parent, this);
        createContents();
    }

    private void createContents() {
        setLayout(new GridLayout(2, false));

        Label lblFirstName = new Label(this, SWT.NONE);
        lblFirstName.setText("First Name:");
        txtFirstName = new Text(this, SWT.BORDER);
        txtFirstName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

        Label lblLastName = new Label(this, SWT.NONE);
        lblLastName.setText("Last Name:");
        txtLastName = new Text(this, SWT.BORDER);
        txtLastName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

        Label lblTitle = new Label(this, SWT.NONE);
        lblTitle.setText("Title:");
        txtTitle = new Text(this, SWT.BORDER);
        txtTitle.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

        Button btnSave = new Button(this, SWT.PUSH);
        btnSave.setText("Save");
        btnSave.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
        btnSave.addListener(SWT.Selection, e -> saveEmployee());

        Button btnCancel = new Button(this, SWT.PUSH);
        btnCancel.setText("Cancel");
        btnCancel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
        btnCancel.addListener(SWT.Selection, e -> close());

    }

    private void saveEmployee() {
        String firstName = txtFirstName.getText().trim();
        String lastName = txtLastName.getText().trim();
        String title = txtTitle.getText().trim();

        if (!firstName.isEmpty() && !lastName.isEmpty() && !title.isEmpty()) {
            newEmployee = new Employee();
            newEmployee.setFirstName(firstName);
            newEmployee.setLastName(lastName);
            newEmployee.setTitle(title);
            close();
        } else {
            System.out.println("All fields are required.");
        }
    }

    public Employee openAndGetEmployee() {
        open();
        while (!isDisposed()) {
            if (!getDisplay().readAndDispatch()) {
                getDisplay().sleep();
            }
        }
        return newEmployee;
    }

    @Override
    protected void checkSubclass() {
        // Allow subclassing
    }
}
