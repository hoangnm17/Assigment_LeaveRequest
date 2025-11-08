package dal;

import java.util.ArrayList;
import model.leave.LeaveRequest;
import java.sql.*;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.leave.ApprovalStep;
import model.leave.LeaveType;
import model.employee.Employee;

public class LeaveRequestDAO extends DBContext<LeaveRequest> {

    public ArrayList<LeaveRequest> getRequestsByManagerHierarchy(int managerId, int pageIndex, int pageSize) {
        ArrayList<LeaveRequest> list = new ArrayList<>();
        String sql = """
        WITH Org AS (
            SELECT *, 0 AS lvl
            FROM Employee e
            WHERE e.EmployeeID = ?
            UNION ALL
            SELECT c.*, o.lvl + 1 AS lvl
            FROM Employee c
            JOIN Org o ON c.ManagerID = o.EmployeeID
        )
        SELECT DISTINCT
            r.RequestID,
            lt.TypeName,
            r.Created_by,
            e.EmployeeName AS Created_by_Name,
            r.Created_time,
            r.StartDate,
            r.EndDate,
            r.Reason,
            r.Status AS RequestStatus
        FROM LeaveRequest r
        JOIN LeaveType lt ON r.LeaveTypeID = lt.LeaveTypeID
        JOIN Org e ON r.Created_by = e.EmployeeID
        WHERE r.Status = 'In progress'
        ORDER BY r.Created_time DESC
        OFFSET ? ROWS FETCH NEXT ? ROWS ONLY
    """;

        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setInt(1, managerId);
            stm.setInt(2, (pageIndex - 1) * pageSize);
            stm.setInt(3, pageSize);

            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                LeaveRequest req = new LeaveRequest();
                req.setId(rs.getInt("RequestID"));
                req.setReason(rs.getString("Reason"));
                req.setStatus(rs.getString("RequestStatus"));

                Employee e = new Employee();
                e.setEmployeeName(rs.getString("Created_by_Name"));
                req.setCreated_by(e);

                LeaveType lt = new LeaveType();
                lt.setTypeName(rs.getString("TypeName"));
                req.setLeaveType(lt);
                req.setStartDate(rs.getDate("StartDate"));
                req.setEndDate(rs.getDate("EndDate"));
                req.setCreated_time(rs.getTimestamp("Created_time"));

                list.add(req);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return list;
    }

    public int countRequestsByManagerHierarchy(int managerId) {
        // SQL này phải sử dụng logic CTE y hệt như phương thức get
        String sql = """
            WITH Org AS (
                SELECT *, 0 AS lvl
                FROM Employee e
                WHERE e.EmployeeID = ?
                UNION ALL
                SELECT c.*, o.lvl + 1 AS lvl
                FROM Employee c
                JOIN Org o ON c.ManagerID = o.EmployeeID
            )
            SELECT COUNT(DISTINCT r.RequestID)
            FROM LeaveRequest r
            JOIN Org e ON r.Created_by = e.EmployeeID
            WHERE r.Status = 'In progress'
        """;

        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setInt(1, managerId); // Chỉ có 1 tham số
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                return rs.getInt(1); // Trả về kết quả đếm
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public ArrayList<LeaveRequest> getRequestsByEmployee(int employeeId, String statusFilter, int pageIndex, int pageSize) {
        ArrayList<LeaveRequest> list = new ArrayList<>();

        StringBuilder sql = new StringBuilder("""
        SELECT 
            lr.RequestID,
            e.EmployeeID,                                  
            e.EmployeeName,
            lt.TypeName,
            lr.StartDate,
            lr.EndDate,
            lr.Reason,
            lr.Status,
            lr.Created_time,
            ap.Notes
        FROM LeaveRequest lr
        JOIN LeaveType lt ON lr.LeaveTypeID = lt.LeaveTypeID
        JOIN Employee e ON lr.Created_by = e.EmployeeID
        LEFT JOIN ApprovalStep ap ON lr.RequestID = ap.RequestID
        WHERE lr.Created_by = ?
    """);

        if (statusFilter != null && !"All".equalsIgnoreCase(statusFilter)) {
            sql.append(" AND lr.Status = ?");
        }

        sql.append(" ORDER BY lr.Created_time DESC OFFSET ? ROWS FETCH NEXT ? ROWS ONLY");

        try (PreparedStatement stm = connection.prepareStatement(sql.toString())) {
            int paramIndex = 1;
            stm.setInt(paramIndex++, employeeId);

            if (statusFilter != null && !"All".equalsIgnoreCase(statusFilter)) {
                stm.setString(paramIndex++, statusFilter);
            }

            stm.setInt(paramIndex++, (pageIndex - 1) * pageSize);
            stm.setInt(paramIndex++, pageSize);

            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                LeaveRequest req = new LeaveRequest();

                Employee e = new Employee();
                e.setEmployeeName(rs.getNString("EmployeeName"));
                e.setId(rs.getInt("EmployeeID"));
                req.setCreated_by(e);

                LeaveType lt = new LeaveType();
                lt.setTypeName(rs.getNString("TypeName"));
                req.setLeaveType(lt);

                req.setId(rs.getInt("RequestID"));
                req.setStartDate(rs.getDate("StartDate"));
                req.setEndDate(rs.getDate("EndDate"));
                req.setReason(rs.getNString("Reason"));
                req.setStatus(rs.getString("Status"));
                req.setCreated_time(rs.getTimestamp("Created_time"));

                ApprovalStep appStep = new ApprovalStep();
                appStep.setNotes(rs.getNString("Notes"));
                req.setAppStep(appStep);

                list.add(req);
            }

        } catch (SQLException ex) {
            Logger.getLogger(LeaveRequestDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return list;
    }

    public int countRequestsByEmployee(int employeeId, String statusFilter) {
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM LeaveRequest WHERE Created_by = ?");
        if (statusFilter != null && !"All".equalsIgnoreCase(statusFilter)) {
            sql.append(" AND Status = ?");
        }

        try (PreparedStatement stm = connection.prepareStatement(sql.toString())) {
            stm.setInt(1, employeeId);
            if (statusFilter != null && !"All".equalsIgnoreCase(statusFilter)) {
                stm.setString(2, statusFilter);
            }
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(LeaveRequestDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    @Override
    public ArrayList<LeaveRequest> list() {
        ArrayList<LeaveRequest> list_apps = new ArrayList<>();

        String sql = """
                     SELECT lr.RequestID,
                            e.EmployeeName, 
                            lt.TypeName, 
                            lr.StartDate, 
                            lr.EndDate, 
                            lr.Reason, 
                            lr.Status, 
                            lr.Created_time
                     FROM LeaveRequest lr
                     JOIN Employee e ON lr.Created_by = e.EmployeeID
                     JOIN LeaveType lt ON lr.LeaveTypeID = lt.LeaveTypeID
                     """;

        try (PreparedStatement stm = connection.prepareStatement(sql); ResultSet rs = stm.executeQuery()) {

            while (rs.next()) {
                LeaveRequest application = new LeaveRequest();

                // === Thông tin người tạo đơn ===
                Employee emp = new Employee();
                emp.setEmployeeName(rs.getNString("EmployeeName"));
                application.setCreated_by(emp);

                // === Thông tin loại nghỉ phép ===
                LeaveType leaveType = new LeaveType();
                leaveType.setTypeName(rs.getNString("TypeName")); // <-- sửa ở đây
                application.setLeaveType(leaveType);

                // === Thông tin đơn nghỉ ===
                application.setId(rs.getInt("RequestID"));
                application.setStartDate(rs.getDate("StartDate"));
                application.setEndDate(rs.getDate("EndDate"));
                application.setReason(rs.getNString("Reason"));
                application.setStatus(rs.getString("Status"));
                application.setCreated_time(rs.getTimestamp("Created_time"));

                list_apps.add(application);
            }

        } catch (SQLException ex) {
            Logger.getLogger(LeaveRequestDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            closeConnection(); // nếu connection là độc lập
        }

        return list_apps;
    }

    @Override
    public LeaveRequest get(int requestId
    ) {
        LeaveRequest lr = null;
        String sql = """
                     SELECT e.EmployeeName, 
                                        lt.TypeName, 
                                        lr.StartDate, 
                                        lr.EndDate, 
                                        lr.Reason, 
                                        lr.Status, 
                                        lr.Created_time
                                 FROM LeaveRequest lr
                                 JOIN Employee e ON lr.Created_by = e.EmployeeID
                                 JOIN LeaveType lt ON lr.LeaveTypeID = lt.LeaveTypeID
            WHERE lr.RequestID = ?
        """;

        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setInt(1, requestId);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                LeaveRequest application = new LeaveRequest();

                // === Thông tin người tạo đơn ===
                Employee emp = new Employee();
                emp.setEmployeeName(rs.getNString("EmployeeName"));
                application.setCreated_by(emp);

                // === Thông tin loại nghỉ phép ===
                LeaveType leaveType = new LeaveType();
                leaveType.setTypeName(rs.getNString("TypeName")); // <-- sửa ở đây
                application.setLeaveType(leaveType);

                // === Thông tin đơn nghỉ ===
                application.setStartDate(rs.getDate("StartDate"));
                application.setEndDate(rs.getDate("EndDate"));
                application.setReason(rs.getNString("Reason"));
                application.setStatus(rs.getString("Status"));
                application.setCreated_time(rs.getTimestamp("Created_time"));

                return application;
            }
        } catch (SQLException e) {
            Logger.getLogger(LeaveRequestDAO.class.getName()).log(Level.SEVERE, null, e);
        }

        return lr;
    }

    public LeaveRequest getByRequestId(int id) {
        LeaveRequest lr = null;
        String sql = """
        SELECT 
            lr.RequestID,
            lr.Created_by AS EmployeeID,
            e.EmployeeName, 
            lr.LeaveTypeID,
            lt.TypeName, 
            lr.StartDate, 
            lr.EndDate, 
            lr.Reason, 
            lr.Status, 
            lr.Created_time
        FROM LeaveRequest lr
        JOIN Employee e ON lr.Created_by = e.EmployeeID
        JOIN LeaveType lt ON lr.LeaveTypeID = lt.LeaveTypeID
        WHERE lr.RequestID = ?
    """;

        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                lr = new LeaveRequest();
                lr.setId(rs.getInt("RequestID"));
                lr.setReason(rs.getNString("Reason"));
                lr.setStartDate(rs.getDate("StartDate"));
                lr.setEndDate(rs.getDate("EndDate"));
                lr.setStatus(rs.getString("Status"));
                lr.setCreated_time(rs.getTimestamp("Created_time"));

                Employee emp = new Employee();
                emp.setId(rs.getInt("EmployeeID"));
                emp.setEmployeeName(rs.getNString("EmployeeName"));
                lr.setCreated_by(emp);

                LeaveType lt = new LeaveType();
                lt.setId(rs.getInt("LeaveTypeID"));
                lt.setTypeName(rs.getNString("TypeName"));
                lr.setLeaveType(lt);
            }
        } catch (SQLException e) {
            Logger.getLogger(LeaveRequestDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return lr;
    }

    @Override
    public void insert(LeaveRequest model) {
        try {
            beginTransaction();

            String sql = """
                         INSERT INTO [LeaveRequest]
                             ([Created_by],
                              [LeaveTypeID],
                              [StartDate],
                              [EndDate],
                              [Reason],
                              [Status])
                         VALUES (?, ?, ?, ?, ?, ?)
                         """;

            try (PreparedStatement stm = connection.prepareStatement(sql)) {
                stm.setInt(1, model.getCreated_by().getId());
                stm.setInt(2, model.getLeaveType().getId());
                stm.setDate(3, model.getStartDate());
                stm.setDate(4, model.getEndDate());
                stm.setString(5, model.getReason());
                stm.setString(6, "In progress");
                stm.executeUpdate();
            }

            // === Lấy lại ID vừa insert ===
            String sql_get_sid = "SELECT @@IDENTITY as [RequestID]";
            try (PreparedStatement stm2 = connection.prepareStatement(sql_get_sid); ResultSet rs = stm2.executeQuery()) {
                if (rs.next()) {
                    model.setId(rs.getInt("RequestID"));
                }
            }

            commitTransaction();

        } catch (SQLException ex) {
            rollbackTransaction();
            Logger.getLogger(LeaveRequestDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            resetTransactionMode();
            closeConnection();
        }
    }

    public ArrayList<LeaveRequest> listByDateRange(LocalDate startDate, LocalDate endDate) {
        ArrayList<LeaveRequest> list = new ArrayList<>();

        String sql = """
        SELECT lr.RequestID,
               e.EmployeeName,
               e.EmployeeID,
               lt.TypeName,
               lr.StartDate,
               lr.EndDate,
               lr.Reason,
               lr.Status,
               lr.Created_time,
               ap.Notes
        FROM LeaveRequest lr
        JOIN LeaveType lt ON lr.LeaveTypeID = lt.LeaveTypeID
        JOIN Employee e ON lr.Created_by = e.EmployeeID
        LEFT JOIN ApprovalStep ap ON lr.RequestID = ap.RequestID
        WHERE (lr.StartDate <= ? AND lr.EndDate >= ?)
        ORDER BY lr.StartDate
    """;

        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            // chuyển LocalDate -> java.sql.Date
            stm.setDate(1, Date.valueOf(endDate));
            stm.setDate(2, Date.valueOf(startDate));

            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                LeaveRequest req = new LeaveRequest();

                // Thông tin nhân viên
                Employee e = new Employee();
                e.setId(rs.getInt("EmployeeID"));
                e.setEmployeeName(rs.getNString("EmployeeName"));
                req.setCreated_by(e);

                // Loại nghỉ
                LeaveType lt = new LeaveType();
                lt.setTypeName(rs.getNString("TypeName"));
                req.setLeaveType(lt);

                // Thông tin đơn
                req.setId(rs.getInt("RequestID"));
                req.setStartDate(rs.getDate("StartDate"));
                req.setEndDate(rs.getDate("EndDate"));
                req.setReason(rs.getNString("Reason"));
                req.setStatus(rs.getString("Status"));
                req.setCreated_time(rs.getTimestamp("Created_time"));

                // ApprovalStep
                ApprovalStep appStep = new ApprovalStep();
                appStep.setNotes(rs.getNString("Notes"));
                req.setAppStep(appStep);

                list.add(req);
            }

        } catch (SQLException ex) {
            Logger.getLogger(LeaveRequestDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return list;
    }

    public void updateStatus(LeaveRequest model
    ) {
        String sql = "UPDATE LeaveRequest SET Status = ? WHERE RequestID = ?";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setString(1, model.getStatus());
            st.setInt(2, model.getId());
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(LeaveRequest model
    ) {
        try {
            String sql = """
                    UPDATE LeaveRequest
                    SET 
                        Reason = ?, 
                        StartDate = ?, 
                        EndDate = ?, 
                        LeaveTypeID = ?
                    WHERE RequestID = ? AND Status = 'In progress'
        """;
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, model.getReason());
            stm.setDate(2, model.getStartDate());
            stm.setDate(3, model.getEndDate());
            stm.setInt(4, model.getLeaveType().getId());
            stm.setInt(5, model.getId());
            stm.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(LeaveRequest model
    ) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
