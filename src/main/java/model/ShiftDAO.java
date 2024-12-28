package model;

import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ShiftDAO {

    // Fetch all shifts
    public List<Shift> getAllShifts() {
        List<Shift> shifts = new ArrayList<>();
        String sql = "SELECT * FROM Shifts";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Shift shift = new Shift();
                shift.setShiftID(rs.getInt("ShiftID"));
                shift.setShiftName(rs.getString("ShiftName"));
                shift.setStartTime(rs.getString("StartTime"));
                shift.setEndTime(rs.getString("EndTime"));
                shifts.add(shift);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return shifts;
    }

    // Clear all employees assigned to a shift
    public void clearShiftAssignments(int shiftID) {
        String sql = "UPDATE Employees SET ShiftID = NULL WHERE ShiftID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, shiftID);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Update shift details
    public void updateShift(Shift shift) {
        String sql = "UPDATE Shifts SET ShiftName = ?, StartTime = ?, EndTime = ? WHERE ShiftID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, shift.getShiftName());
            stmt.setString(2, shift.getStartTime());
            stmt.setString(3, shift.getEndTime());
            stmt.setInt(4, shift.getShiftID());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
