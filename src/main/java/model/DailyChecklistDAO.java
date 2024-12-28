package model;

import util.DBConnection;
import java.sql.*;

public class DailyChecklistDAO {
    public int insertDailyChecklist(DailyChecklist checklist) {
        String sql = "INSERT INTO DailyChecklist(ArcITXAccuracy, ArcITXVerified, ChannelLaunch, WeatherMaps, "
                + "InteractiveChannel, DailyLeadSweep, MaintenanceTicket, RestoreVerified, STBTurner, PreliminaryKCI, "
                + "SKDL, Takedown, DailyReportID) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setBoolean(1, checklist.isArcITXAccuracy());
            pstmt.setBoolean(2, checklist.isArcITXVerified());
            pstmt.setBoolean(3, checklist.isChannelLaunch());
            pstmt.setBoolean(4, checklist.isWeatherMaps());
            pstmt.setBoolean(5, checklist.isInteractiveChannel());
            pstmt.setBoolean(6, checklist.isDailyLeadSweep());
            pstmt.setBoolean(7, checklist.isMaintenanceTicket());
            pstmt.setBoolean(8, checklist.isRestoreVerified());
            pstmt.setBoolean(9, checklist.isStbTurner());
            pstmt.setBoolean(10, checklist.isPreliminaryKCI());
            pstmt.setBoolean(11, checklist.isSkdl());
            pstmt.setBoolean(12, checklist.isTakedown());
            pstmt.setInt(13, checklist.getDailyReportID());

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
    // Similarly implement getById, update, delete if needed
}
