package dal;

import java.util.ArrayList;
import models.LeaveRequest;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.LeaveType;
import models.User;

public class LeaveRequestDBContext extends DBContext<LeaveRequest> {

    @Override
    public ArrayList<LeaveRequest> list() {
        ArrayList<LeaveRequest> list_apps = new ArrayList<>();
        try {
            String sql = """
                            SELECT u.FullName, lp.LeaveTypeName, lr.StartDate, lr.EndDate, lr.Reason, lr.Status, lr.CreatedAt  FROM LeaveRequests lr
                            JOIN Users u ON lr.UserID = u.UserID
                            JOIN LeaveTypes lp ON lr.LeaveTypeID = lp.LeaveTypeID
                            JOIN Departments d ON u.DepartmentID = d.DepartmentID
                         """;

            PreparedStatement stm_list_app = connection.prepareStatement(sql);
            ResultSet rs = stm_list_app.executeQuery();

            while (rs.next()) {
                LeaveRequest appplication = new LeaveRequest();
                User user = new User();
                user.setUserName(rs.getNString("FullName"));
                appplication.setUser(user);
                LeaveType leaveType = new LeaveType();
                leaveType.setTypeName(rs.getNString("LeaveTypeName"));
                appplication.setLeaveType(leaveType);
                appplication.setStartDate(rs.getDate("StartDate"));
                appplication.setEndDate(rs.getDate("EndDate"));
                appplication.setReason(rs.getNString("Reason"));
                appplication.setStatus(rs.getString("Status"));
                appplication.setCreateAt(rs.getDate("CreatedAt"));

                list_apps.add(appplication);
            }

            return list_apps;
        } catch (SQLException ex) {
            Logger.getLogger(LeaveRequestDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            closeConnection();
        }
        return null;
    }

    @Override
    public LeaveRequest get(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void insert(LeaveRequest model) {

        try {
            beginTransaction();
            String sql = """
                         INSERT INTO [LeaveRequests]
                            ([UserID],
                            [LeaveTypeID],
                            [StartDate],
                            [EndDate],
                            [Reason],
                            [Status])
                         VALUES
                            (?,
                            ?,
                            ?,
                            ?,
                            ?,
                            ?)
                         """;
            PreparedStatement stm_insert_app = connection.prepareStatement(sql);
            stm_insert_app.setInt(1, model.getUser().getId());
            stm_insert_app.setInt(2, model.getLeaveType().getId());
            stm_insert_app.setDate(3, model.getStartDate());
            stm_insert_app.setDate(4, model.getEndDate());
            stm_insert_app.setString(5, model.getReason());
            stm_insert_app.setString(6, "In progress");

            stm_insert_app.executeUpdate();

            String sql_get_sid = "SELECT @@IDENTITY as [RequestID]";
            PreparedStatement stm_get_sid = connection.prepareStatement(sql_get_sid);
            ResultSet rs = stm_get_sid.executeQuery();

            if (rs.next()) {
                model.setId(rs.getInt("RequestID"));
            }

            commitTransaction();

        } catch (SQLException ex) {
            rollbackTransaction();
            Logger.getLogger(LeaveRequestDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            resetTransactionMode();
            closeConnection();
        }

    }

    @Override
    public void update(LeaveRequest model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void delete(LeaveRequest model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
