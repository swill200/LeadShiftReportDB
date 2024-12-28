package model;

import util.DBConnection;
import java.sql.*;

public class ReportEntryDAO {
    public int insertDailyReport(ReportEntry report) {
        String sql = "INSERT INTO DailyReports(DailyReportDate, OncomingLead, PassdownAccepted, CreatedBy, ShiftID) "
                + "VALUES(?,?,?,?,?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, report.getDailyReportDate().toString()); // Might need formatting
            pstmt.setString(2, report.getOncomingLead());
            pstmt.setBoolean(3, report.isPassdownAccepted());
            pstmt.setString(4, report.getCreatedBy());
            pstmt.setInt(5, report.getShiftID());

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet keys = pstmt.getGeneratedKeys()) {
                    if (keys.next()) {
                        return keys.getInt(1);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public DailyReport getDailyReportById(int id) {
        String sql = "SELECT * FROM DailyReports WHERE DailyReportID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    DailyReport dr = new DailyReport();
                    dr.setDailyReportID(rs.getInt("DailyReportID"));
                    dr.setDailyReportDate(rs.getTimestamp("DailyReportDate").toLocalDateTime());
                    dr.setOncomingLead(rs.getString("OncomingLead"));
                    dr.setPassdownAccepted(rs.getBoolean("PassdownAccepted"));
                    dr.setCreatedBy(rs.getString("CreatedBy"));
                    dr.setShiftID(rs.getInt("ShiftID"));
                    return dr;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Add other methods like update, delete, or queries by date/shift as needed
}
