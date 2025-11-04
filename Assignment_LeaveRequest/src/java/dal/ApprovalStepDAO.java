package dal;

import dal.DBContext;
import model.leave.LeaveRequest;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.leave.ApprovalStep;
import model.employee.Employee;

public class ApprovalStepDAO extends DBContext<ApprovalStep> {

    public ArrayList<ApprovalStep> getRequestsByManager(int managerId) {
    ArrayList<ApprovalStep> list = new ArrayList<>();
    String sql = """
        SELECT s.ApproveID AS StepID, s.RequestID, s.StepStatus, s.Notes,
               r.RequestID AS LeaveID, r.Reason, r.Status AS LeaveStatus,
               e.EmployeeName
        FROM ApprovalStep s
        JOIN LeaveRequest r ON s.RequestID = r.RequestID
        JOIN Employee e ON r.Created_by = e.EmployeeID
        WHERE e.ManagerID = ? AND s.StepStatus = 'In progress'
    """;

    try (PreparedStatement stm = connection.prepareStatement(sql)) {
        stm.setInt(1, managerId);
        ResultSet rs = stm.executeQuery();
        while (rs.next()) {
            LeaveRequest req = new LeaveRequest();
            req.setId(rs.getInt("LeaveID"));
            req.setReason(rs.getString("Reason"));
            req.setStatus(rs.getString("LeaveStatus"));
            
            Employee e = new Employee();
            e.setEmployeeName(rs.getString("EmployeeName"));
            req.setCreated_by(e); // nếu có thuộc tính này trong model

            ApprovalStep step = new ApprovalStep();
            step.setId(rs.getInt("ApproveID"));
            step.setRequest(req);
            step.setStatus(rs.getString("StepStatus"));
            step.setNotes(rs.getString("Notes"));

            list.add(step);
        }
    } catch (SQLException ex) {
        Logger.getLogger(ApprovalStepDAO.class.getName()).log(Level.SEVERE, null, ex);
    }
    return list;
}



    public void updateStepStatus(int requestId, int approverId, String status, String note) {
        String sql = """
                        UPDATE ApprovalStep
                        SET StepStatus = ?, Notes = ?, ApproverID = ?, ActionDate = GETDATE(), IsViewed = 1
                        WHERE RequestID = ? AND StepStatus = 'In progress'
                    """;
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setString(1, status);  // 'Waiting' hoặc 'Approved'/'Rejected'
            stm.setString(2, note);
            stm.setInt(3, approverId);
            stm.setInt(4, requestId);
            
            stm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<ApprovalStep> list() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ApprovalStep get(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void insert(ApprovalStep model) {
        String sql = """
                        INSERT INTO [ApprovalStep]
                               ([RequestID]
                               ,[StepStatus]
                               ,[IsViewed])
                         VALUES
                               (?
                               ,?
                               ,?)
                     """;

        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setInt(1, model.getRequest().getId());
            stm.setString(2, model.getStatus());
            stm.setBoolean(3, false);
            stm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(ApprovalStep model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void delete(ApprovalStep model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
