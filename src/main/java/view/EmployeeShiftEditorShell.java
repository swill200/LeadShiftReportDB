package view;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;
import controller.EmployeeShiftEditorController;
import util.ShellUtils;

public class EmployeeShiftEditorShell extends Shell {
    private Table table;
    private List<Button> buttons;

    public EmployeeShiftEditorShell(Shell parent, int shiftID, String shiftName) {
        super(parent, SWT.SHELL_TRIM | SWT.APPLICATION_MODAL);
        setText("Edit " + shiftName + " Shift Employees");
        buttons = new ArrayList<>(); // Initialize button list
        createContents();
        setSize(500, 400);
        setMinimumSize(500,400);
        ShellUtils.centerOnParent(parent, this);
        // Initialize the controller and pass this shell as a reference
        EmployeeShiftEditorController controller = new EmployeeShiftEditorController(shiftID, this);

        // Delegate event handling to the controller
        controller.attachEventListeners(buttons);
        controller.populateTable();
    }

    private void createContents() {
        setLayout(new GridLayout(1, false));

        // Table setup
        table = new Table(this, SWT.BORDER | SWT.FULL_SELECTION);
        table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
        table.setHeaderVisible(true);
        table.setLinesVisible(true);

        TableColumn colID = new TableColumn(table, SWT.NONE);
        colID.setText("ID");
        colID.setWidth(50);

        TableColumn colName = new TableColumn(table, SWT.NONE);
        colName.setText("Name");
        colName.setWidth(150);

        TableColumn colTitle = new TableColumn(table, SWT.NONE);
        colTitle.setText("Title");
        colTitle.setWidth(150);

        // Buttons at the bottom
        Composite buttonComposite = new Composite(this, SWT.NONE);
        buttonComposite.setLayout(new GridLayout(2, true));
        buttonComposite.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

        // Left button group (Add/Remove)
        Composite leftButtonGroup = new Composite(buttonComposite, SWT.NONE);
        leftButtonGroup.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false));
        leftButtonGroup.setLayout(new GridLayout(2, true));

        Button btnAdd = new Button(leftButtonGroup, SWT.PUSH);
        btnAdd.setText("Add Employee");
        btnAdd.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
        btnAdd.setData("action", "add");
        buttons.add(btnAdd); // Add button to list

        Button btnRemove = new Button(leftButtonGroup, SWT.PUSH);
        btnRemove.setText("Remove Selected");
        btnRemove.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
        btnRemove.setData("action", "remove");
        buttons.add(btnRemove); // Add button to list

        // Right button group (Undo/Save)
        Composite rightButtonGroup = new Composite(buttonComposite, SWT.NONE);
        rightButtonGroup.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, true, false));
        rightButtonGroup.setLayout(new GridLayout(2, true));

        Button btnUndo = new Button(rightButtonGroup, SWT.PUSH);
        btnUndo.setText("Undo Changes");
        btnUndo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
        btnUndo.setData("action", "undo");
        buttons.add(btnUndo); // Add button to list

        Button btnSave = new Button(rightButtonGroup, SWT.PUSH);
        btnSave.setText("Save Changes");
        btnSave.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
        btnSave.setData("action", "save");
        buttons.add(btnSave); // Add button to list
    }

    public Table getEmployeeTable() {
        return table;
    }

    public Composite getButtonComposite() {
        return (Composite) getChildren()[1];
    }

    @Override
    protected void checkSubclass() {
        // Allow subclassing
    }
}
