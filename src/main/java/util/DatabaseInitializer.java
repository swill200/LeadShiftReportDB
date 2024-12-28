package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class DatabaseInitializer {
    public static void initializeDatabase() {
        String schema = 
            "CREATE TABLE IF NOT EXISTS Employees (" +
            "    EmployeeID INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "    FirstName VARCHAR(50) NOT NULL, " +
            "    LastName VARCHAR(50) NOT NULL, " +
            "    Title VARCHAR(50), " +
            "    ShiftID INTEGER, " +
            "    FOREIGN KEY (ShiftID) REFERENCES Shifts(ShiftID)" +
            "); " +
            "CREATE TABLE IF NOT EXISTS Shifts (" +
            "    ShiftID INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "    ShiftName VARCHAR(50) NOT NULL UNIQUE, " +
            "    ShiftStart TIME NOT NULL, " +
            "    ShiftEnd TIME NOT NULL, " +
            "    SupervisorID INTEGER, " +
            "    FOREIGN KEY (SupervisorID) REFERENCES Employees(EmployeeID)" +
            "); " +
            "CREATE TABLE IF NOT EXISTS OpAssignments (" +
            "    OpAssignmentID INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "    Assignment VARCHAR(50) NOT NULL, " +
            "    OpAssignmentDate DATE NOT NULL, " +
            "    DailyReportID INTEGER, " +
            "    FOREIGN KEY (DailyReportID) REFERENCES DailyReports(DailyReportID)" +
            "); " +
            "CREATE TABLE IF NOT EXISTS DailyChecklist (" +
            "    DailyChecklistID INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "    ArcITXAccuracy BOOLEAN, " +
            "    ArcITXVerified BOOLEAN, " +
            "    ChannelLaunch BOOLEAN, " +
            "    WeatherMaps BOOLEAN, " +
            "    InteractiveChannel BOOLEAN, " +
            "    DailyLeadSweep BOOLEAN, " +
            "    MaintenanceTicket BOOLEAN, " +
            "    RestoreVerified BOOLEAN, " +
            "    STBTurner BOOLEAN, " +
            "    PreliminaryKCI BOOLEAN, " +
            "    SKDL BOOLEAN, " +
            "    Takedown BOOLEAN, " +
            "    DailyReportID INTEGER, " +
            "    FOREIGN KEY (DailyReportID) REFERENCES DailyReports(DailyReportID)" +
            "); " +
            "CREATE TABLE IF NOT EXISTS DailyReports (" +
            "    DailyReportID INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "    DailyReportDate DATETIME NOT NULL, " +
            "    OncomingLead VARCHAR(50), " +
            "    PassdownAccepted BOOLEAN, " +
            "    CreatedBy VARCHAR(50), " +
            "    ShiftID INTEGER, " +
            "    FOREIGN KEY (ShiftID) REFERENCES Shifts(ShiftID)" +
            "); ";

        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:src/main/resources/database/leters.db");
             Statement stmt = conn.createStatement()) {

            stmt.executeUpdate(schema);
            System.out.println("Database schema created successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to initialize the database.");
        }
    }

    public static void main(String[] args) {
        initializeDatabase();
    }
}
