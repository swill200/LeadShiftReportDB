package controller;

import view.ElvisTicketDialog;
import view.ReportEntryShell;
import model.ReportEntry;
import model.ReportEntryDAO;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.swing.JOptionPane;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

public class ReportEntryController {
    private ReportEntryShell entryShell;
    private ReportEntryDAO reportDAO;
    private boolean shellChanged = false;

    public ReportEntryController(ReportEntryShell entryShell, ReportEntryDAO reportDAO) {
        this.entryShell = entryShell;
        this.reportDAO = (reportDAO != null) ? reportDAO : new ReportEntryDAO(); // Initialize DAO if null
    }
    
    private void openElvisTicketDialog(TableItem item, Table table) {
        ElvisTicketDialog dialog = new ElvisTicketDialog(entryShell);
        System.out.println("Opening Elvis Ticket Entry Dialog");

        // Populate dialog fields if editing an existing item
        if (item != null) {
            dialog.setService(item.getText(0));
            dialog.setTicketNumber(item.getText(1));
        }

        dialog.openDialog(); // This blocks until the dialog is closed
        System.out.println("Returned from dialog");

        // Check if OK was pressed and process the data
        if (dialog.isOkPressed()) {
            String service = dialog.getService(); // Retrieve before interacting with disposed widgets
            String ticketNumber = dialog.getTicketNumber();
            System.out.println("Service: " + service + ", Ticket #: " + ticketNumber);

            // Add a new item or update the existing one
            if (item == null) {
                TableItem newItem = new TableItem(table, SWT.NONE);
                newItem.setText(new String[] { service, ticketNumber });
            } else {
                item.setText(new String[] { service, ticketNumber });
            }
        }
    }

    
    public boolean handleCloseRequest() {
        if (isShellChanged()) {
            int response = JOptionPane.showConfirmDialog(null,
                    "The report has been changed. Are you sure you want to close without saving?", "Close Window?",
                    JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            if (response == JOptionPane.YES_OPTION) {
                setShellChanged(false);
                return true;
            } else {
                return false;
            }
        }
        return true;
    }

    private void handleTextChange(Text textField) {
        System.out.println("Text changed for textfield: " + textField);
        if (textField != null) {
            String action = (String) textField.getData("action");
            if (action != null) {
                System.out.println("Text changed for action: " + action);
                setShellChanged(true);
                updateEditTime();
            }
        }
    }

    public void handleAction(String action) {
        System.out.println("Action received in controller: " + action);
        if (action != null) {
            switch (action)
            {
            case "markComplete":
                System.out.println("Task marked as complete.");
                updateEditTime();
                break;
            case "markIncomplete":
                System.out.println("Task marked as incomplete.");
                updateEditTime();
                break;
            default:
                System.out.println("Unknown action: " + action);
                break;
            }
        }
    }

    public void attachEventListeners(ReportEntryShell shell) {
        if (shell != null) {
            // Close Listener
            shell.addListener(SWT.Close, event -> {
                boolean canClose = handleCloseRequest();
                event.doit = canClose;
            });
            // Attach listeners to checklist buttons
            List<Button> buttons = shell.getChecklistButtons();
            if (buttons != null) {
                for (Button button : buttons) {
                    String action = (String) button.getData("action");
                    if (action != null) {
                        button.addListener(SWT.Selection, event -> handleAction(action));
                    }
                }
            }
            // Attach listeners to text fields
            List<Text> textFields = shell.getTextFields();
            if (textFields != null) {
                for (Text textField : textFields) {
                    if (textField != null) {
                        textField.addModifyListener(event -> handleTextChange((Text) event.getSource()));
                    }
                }
            }
            
            shell.getAddTicketButton().addListener(SWT.Selection, event -> openElvisTicketDialog(null, shell.getTicketTable()));

            shell.getRemoveTicketButton().addListener(SWT.Selection, event -> {
                Table table = shell.getTicketTable();
                int selectionIndex = table.getSelectionIndex();
                if (selectionIndex != -1) {
                    table.remove(selectionIndex);
                } else {
                    MessageBox warning = new MessageBox(shell, SWT.ICON_WARNING | SWT.OK);
                    warning.setText("No Selection");
                    warning.setMessage("Please select a ticket to remove.");
                    warning.open();
                }
            });
        }
    }
    
    public void setShellChanged(boolean changed) {
        this.shellChanged = changed;
    }

    public boolean isShellChanged() {
        return shellChanged;
    }
    
    private void updateEditTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/dd/yyyy, HH:mm:ss");
        String currentTime = LocalDateTime.now().format(formatter);
        entryShell.updateEditTimeLabel(currentTime);
    }

}