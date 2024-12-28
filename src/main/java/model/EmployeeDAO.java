package model;

import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAO {

    // Fetch all employees for a specific shift
    public List<Employee> getEmployeesByShift(int shiftID) {
        List<Employee> employees = new ArrayList<>();
        String sql = "SELECT * FROM Employees WHERE ShiftID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, shiftID);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Employee emp = new Employee();
                    emp.setEmployeeID(rs.getInt("EmployeeID"));
                    emp.setFirstName(rs.getString("FirstName"));
                    emp.setLastName(rs.getString("LastName"));
                    emp.setTitle(rs.getString("Title"));
                    emp.setShiftID(rs.getInt("ShiftID"));
                    employees.add(emp);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employees;
    }

    // Assign an employee to a shift
    public void assignEmployeeToShift(int employeeID, int shiftID) {
        String sql = "UPDATE Employees SET ShiftID = ? WHERE EmployeeID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, shiftID);
            pstmt.setInt(2, employeeID);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Remove an employee from a shift
    public void removeEmployeeFromShift(int employeeID) {
        String sql = "UPDATE Employees SET ShiftID = 0 WHERE EmployeeID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, employeeID);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    
    // Add a new employee
    public void addEmployee(Employee employee, Connection conn) throws SQLException {
        String sql = "INSERT INTO Employees (FirstName, LastName, Title, ShiftID) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, employee.getFirstName());
            pstmt.setString(2, employee.getLastName());
            pstmt.setString(3, employee.getTitle());
            pstmt.setInt(4, employee.getShiftID());
            pstmt.executeUpdate();

            // Retrieve the generated EmployeeID
            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    employee.setEmployeeID(rs.getInt(1));
                }
            }
        }
    }

    
    // Delete an employee
    public void deleteEmployee(int employeeID, Connection conn) throws SQLException {
        String sql = "DELETE FROM Employees WHERE EmployeeID = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, employeeID);
            int rowsAffected = pstmt.executeUpdate();
            System.out.println("Employee deleted, rows affected: " + rowsAffected);
        }
    }
    
    // Update an employee TODO: add button to shell
    public void updateEmployee(Employee employee, Connection conn) throws SQLException {
        String sql = "UPDATE Employees SET FirstName = ?, LastName = ?, Title = ?, ShiftID = ? WHERE EmployeeID = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, employee.getFirstName());
            pstmt.setString(2, employee.getLastName());
            pstmt.setString(3, employee.getTitle());
            pstmt.setInt(4, employee.getShiftID());
            pstmt.setInt(5, employee.getEmployeeID());
            pstmt.executeUpdate();
        }
    }

    // Fetch a single employee by ID
    public Employee getEmployeeByID(int employeeID) {
        String sql = "SELECT * FROM Employees WHERE EmployeeID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, employeeID);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Employee emp = new Employee();
                    emp.setEmployeeID(rs.getInt("EmployeeID"));
                    emp.setFirstName(rs.getString("FirstName"));
                    emp.setLastName(rs.getString("LastName"));
                    emp.setTitle(rs.getString("Title"));
                    emp.setShiftID(rs.getInt("ShiftID"));
                    return emp;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
