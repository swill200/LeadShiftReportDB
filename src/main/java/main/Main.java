package main;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import controller.MainController;
import util.DatabaseInitializer;
import view.MainShell;

public class Main {
    public static void main(String[] args) {
        
        // Initialize the database
        DatabaseInitializer.initializeDatabase();
        // Create the SWT Display
        Display display = new Display();

        // Create t view (the shell)
        Shell mainShell = new MainShell(display);

        // Create the main controller and link it to the view
        MainController mainController = new MainController((MainShell) mainShell);

        // 4. Open the shell and enter SWT event loop
        mainShell.open();
        mainShell.layout();

        while (!mainShell.isDisposed()) {
            if (!display.readAndDispatch()) {
                display.sleep();
            }
        }
        display.dispose();
    }
}
//package main;
//
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.PreparedStatement;
//import java.sql.SQLException;
//
//public class Main {
//public static void main(String[] args) {
//    try (Connection conn = DriverManager.getConnection("jdbc:sqlite:src/main/resources/database/leters.db")) {
//        conn.setAutoCommit(false);
//
//        String sql = "INSERT INTO Employees (FirstName, LastName, Title, ShiftID) VALUES (?, ?, ?, ?)";
//        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
//            pstmt.setString(1, "John");
//            pstmt.setString(2, "Doe");
//            pstmt.setString(3, "Technician");
//            pstmt.setInt(4, 1);
//            pstmt.executeUpdate();
//        }
//
//        conn.commit();
//        System.out.println("Employee added successfully.");
//    } catch (SQLException e) {
//        e.printStackTrace();
//    }
//}
//}