package view;

import model.Shift;
import model.ShiftDAO;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

import java.util.List;

public class ShiftEditorShell extends Shell {
    private ShiftDAO shiftDAO;
    private Table table;

    public ShiftEditorShell(Display display, ShiftDAO shiftDAO) {
        super(display, SWT.SHELL_TRIM | SWT.BORDER);
        this.shiftDAO = shiftDAO;
        createContents();
        populateTable();
    }

    private void createContents() {
        setText("Edit Days Shifts");
        setSize(600, 400);
        setLayout(new GridLayout(1, false));

        table = new Table(this, SWT.BORDER | SWT.FULL_SELECTION);
        table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
        table.setHeaderVisible(true);
        table.setLinesVisible(true);

        TableColumn colID = new TableColumn(table, SWT.NONE);
        colID.setText("ID");
        colID.setWidth(50);

        TableColumn colName = new TableColumn(table, SWT.NONE);
        colName.setText("Shift Name");
        colName.setWidth(150);

        TableColumn colStart = new TableColumn(table, SWT.NONE);
        colStart.setText("Start Time");
        colStart.setWidth(100);

        TableColumn colEnd = new TableColumn(table, SWT.NONE);
        colEnd.setText("End Time");
        colEnd.setWidth(100);

        // Save button
        Button btnSave = new Button(this, SWT.PUSH);
        btnSave.setText("Save Changes");
        btnSave.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false));
        btnSave.addListener(SWT.Selection, event -> handleSave());
    }

    private void populateTable() {
        List<Shift> shifts = shiftDAO.getAllShifts();
        for (Shift shift : shifts) {
            TableItem item = new TableItem(table, SWT.NONE);
            item.setText(0, String.valueOf(shift.getShiftID()));
            item.setText(1, shift.getShiftName());
            item.setText(2, shift.getStartTime());
            item.setText(3, shift.getEndTime());
        }
    }

    private void handleSave() {
        TableItem[] items = table.getItems();
        for (TableItem item : items) {
            Shift shift = new Shift();
            shift.setShiftID(Integer.parseInt(item.getText(0)));
            shift.setShiftName(item.getText(1));
            shift.setStartTime(item.getText(2));
            shift.setEndTime(item.getText(3));
            shiftDAO.updateShift(shift);
        }
        System.out.println("Shifts updated successfully.");
    }

    @Override
    protected void checkSubclass() {
        // Disable subclass check
    }
}
