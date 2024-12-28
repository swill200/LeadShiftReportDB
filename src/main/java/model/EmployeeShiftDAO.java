package model;

import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeShiftDAO {
    // Get all employees assigned to a specific shift
    public List<Employee> getEmployeesByShift(int shiftID)
    {
        List<Employee> employees = new ArrayList<>();
        String sql = "SELECT e.EmployeeID, e.FirstName, e.LastName, e.Title FROM Employees e INNER JOIN Shifts es ON e.EmployeeID = es.EmployeeID WHERE es.ShiftID = ?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql))
        {
            pstmt.setInt(1, shiftID);
            try (ResultSet rs = pstmt.executeQuery())
            {
                while (rs.next())
                {
                    Employee emp = new Employee();
                    emp.setEmployeeID(rs.getInt("EmployeeID"));
                    emp.setFirstName(rs.getString("FirstName"));
                    emp.setLastName(rs.getString("LastName"));
                    emp.setTitle(rs.getString("Title"));
                    employees.add(emp);
                }
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        return employees;
    }

    // Assign an employee to a shift
    public void assignEmployeeToShift(int employeeID, int shiftID)
    {
        String sql = "INSERT OR IGNORE INTO Shifts (EmployeeID, ShiftID) VALUES (?, ?)";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql))
        {
            pstmt.setInt(1, employeeID);
            pstmt.setInt(2, shiftID);
            pstmt.executeUpdate();
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    // Remove an employee from a shift
    public void removeEmployeeFromShift(int employeeID, int shiftID)
    {
        String sql = "DELETE FROM Shifts WHERE EmployeeID = ? AND ShiftID = ?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql))
        {
            pstmt.setInt(1, employeeID);
            pstmt.setInt(2, shiftID);
            pstmt.executeUpdate();
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
    
    public void addEmployee(Employee employee) {
        String sql = "INSERT INTO Employees (FirstName, LastName, Title) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, employee.getFirstName());
            pstmt.setString(2, employee.getLastName());
            pstmt.setString(3, employee.getTitle());
            pstmt.executeUpdate();

            // Retrieve the auto-generated EmployeeID
            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    employee.setEmployeeID(rs.getInt(1)); // Set the generated ID back to the object
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void clearShiftAssignments(int shiftID) {
        String sql = "DELETE FROM Shifts WHERE ShiftID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, shiftID);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    
}
