package controller;

import model.Employee;
import model.EmployeeDAO;
import util.DBConnection;
import view.AddEmployeeDialog;
import view.EmployeeShiftEditorShell;
import view.Toast;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.TableItem;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeShiftEditorController {
    private final int shiftID;
    private final EmployeeShiftEditorShell shell;
    private final EmployeeDAO employeeDAO;

    // To track changes for undo functionality
    private final List<Employee> originalEmployees = new ArrayList<>();
    private final List<Employee> modifiedEmployees = new ArrayList<>();
    private List<Employee> removedEmployees = new ArrayList<>();

    public EmployeeShiftEditorController(int shiftID, EmployeeShiftEditorShell shell) {
        this.shiftID = shiftID;
        this.shell = shell;
        this.employeeDAO = new EmployeeDAO();
    }

    public void attachEventListeners(List<Button> buttons) {
        for (Button button : buttons) {
            String action = (String) button.getData("action");
            if (action != null) {
                button.addListener(SWT.Selection, event -> handleAction(action));
            }
        }
    }

    private void handleAction(String action) {
        switch (action) {
            case "add":
                handleAddEmployee();
                break;
            case "remove":
                handleRemoveEmployee();
                break;
            case "undo":
                handleUndoChanges();
                break;
            case "save":
                handleSaveChanges();
                break;
            default:
                throw new IllegalArgumentException("Unknown action: " + action);
        }
    }

    public void populateTable() {
        var employees = employeeDAO.getEmployeesByShift(shiftID);
        originalEmployees.clear();
        originalEmployees.addAll(employees);

        shell.getEmployeeTable().removeAll();

        for (var employee : employees) {
            var item = new TableItem(shell.getEmployeeTable(), org.eclipse.swt.SWT.NONE);
            item.setText(new String[]{
                String.valueOf(employee.getEmployeeID()),
                employee.getFirstName() + " " + employee.getLastName(),
                employee.getTitle()
            });
        }
    }

    public void handleAddEmployee() {
        // Open the Add Employee dialog
        AddEmployeeDialog dialog = new AddEmployeeDialog(shell);
        Employee newEmployee = dialog.openAndGetEmployee();

        if (newEmployee != null) {
            newEmployee.setEmployeeID(0); // Temporary ID for unsaved employees
            newEmployee.setShiftID(shiftID);
            modifiedEmployees.add(newEmployee); // Add to in-memory changes
            updateTable(newEmployee); // Add to the visible table
        }
    }

    public void handleRemoveEmployee() {
        int selectedIndex = shell.getEmployeeTable().getSelectionIndex();
        if (selectedIndex != -1) {
            TableItem selectedItem = shell.getEmployeeTable().getItem(selectedIndex);
            
            // Parse employeeID while accounting for unsaved added employee
            int employeeID; 
            try {
                employeeID = Integer.parseInt(selectedItem.getText(0));
            } catch (NumberFormatException e) {
                employeeID = 0;
            }

            // Check if the employee is a new addition (not yet saved to the database)
            Employee employeeToRemove = null;
            for (Employee emp : modifiedEmployees) {
                if (emp.getEmployeeID() == employeeID) {
                    employeeToRemove = emp;
                    break;
                }
            }

            if (employeeToRemove != null) {
                // New addition: remove from modifiedEmployees only
                modifiedEmployees.remove(employeeToRemove);
            } else {
                // Existing employee: mark for deletion
                for (Employee emp : originalEmployees) {
                    if (emp.getEmployeeID() == employeeID) {
                        employeeToRemove = emp;
                        break;
                    }
                }

                if (employeeToRemove != null) {
                    removedEmployees.add(employeeToRemove); // Mark for deletion
                    originalEmployees.remove(employeeToRemove); // Remove from originals
                }
            }

            // Remove the row from the table
            selectedItem.dispose();
        } else {
            System.out.println("No employee selected for removal.");
        }
    }


    public void handleUpdateEmployee(Employee updatedEmployee) {
        for (Employee emp : modifiedEmployees) {
            if (emp.getEmployeeID() == updatedEmployee.getEmployeeID()) {
                emp.setFirstName(updatedEmployee.getFirstName());
                emp.setLastName(updatedEmployee.getLastName());
                emp.setTitle(updatedEmployee.getTitle());
                emp.setShiftID(updatedEmployee.getShiftID());
            }
        }

        // Update the visible table
        for (TableItem item : shell.getEmployeeTable().getItems()) {
            if (Integer.parseInt(item.getText(0)) == updatedEmployee.getEmployeeID()) {
                item.setText(1, updatedEmployee.getFirstName() + " " + updatedEmployee.getLastName());
                item.setText(2, updatedEmployee.getTitle());
            }
        }
    }
    
    public void handleUndoChanges() {
        // Restore the original state of employees for this shift
        for (Employee modified : modifiedEmployees) {
            employeeDAO.removeEmployeeFromShift(modified.getEmployeeID());
        }
        for (Employee original : originalEmployees) {
            employeeDAO.assignEmployeeToShift(original.getEmployeeID(), shiftID);
        }

        // Clear the modified list and refresh the table
        modifiedEmployees.clear();
        populateTable();
        System.out.println("Changes undone.");
    }

    public void handleSaveChanges() {
        try (Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(false); // Start transaction

            // Process removed employees
            for (Employee employee : removedEmployees) {
                System.out.println("Deleting employee: " + employee);
                employeeDAO.deleteEmployee(employee.getEmployeeID(), conn);
            }

            // Process added or updated employees
            for (Employee employee : modifiedEmployees) {
                if (employee.getEmployeeID() == 0) {
                    System.out.println("Adding employee: " + employee);
                    employeeDAO.addEmployee(employee, conn);
                } else {
                    System.out.println("Updating employee: " + employee);
                    employeeDAO.updateEmployee(employee, conn);
                }
            }

            conn.commit(); // Commit all changes
            removedEmployees.clear(); // Clear removed employees
            modifiedEmployees.clear(); // Clear in-memory changes
            
            // Show toast notification on success
            Toast toast = new Toast(shell, "Changes saved successfully!");
            toast.showToast();
            
            System.out.println("Changes saved to the database.");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to save changes.");
        }
        this.populateTable();
    }


    private void updateTable(Employee employee) {
        TableItem item = new TableItem(shell.getEmployeeTable(), SWT.NONE);
        item.setText(new String[]{
            employee.getEmployeeID() == 0 ? "NEW" : String.valueOf(employee.getEmployeeID()),
            employee.getFirstName() + " " + employee.getLastName(),
            employee.getTitle()
        });
    }


}
