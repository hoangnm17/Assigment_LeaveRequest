package dal;

import java.util.ArrayList;
import java.sql.*;
import java.util.List;
import model.agenda.EmployeeAttendance;

public class AttendanceDAO extends DBContext<EmployeeAttendance> {

    public List<EmployeeAttendance> getAttendanceByDepartment(Integer managerId, Date startDate, Date endDate) {
        List<EmployeeAttendance> list = new ArrayList<>();
        try {
            // 1. Lấy danh sách nhân viên
            String sqlEmp = "SELECT EmployeeID, EmployeeName FROM Employee";
            if (managerId != null) {
                sqlEmp += " WHERE DepartmentID = (SELECT DepartmentID FROM Employee WHERE EmployeeID = ?)";
            }

            PreparedStatement stmEmp = connection.prepareStatement(sqlEmp);
            if (managerId != null) {
                stmEmp.setInt(1, managerId);
            }
            ResultSet rsEmp = stmEmp.executeQuery();

            while (rsEmp.next()) {
                EmployeeAttendance ea = new EmployeeAttendance();
                ea.setId(rsEmp.getInt("EmployeeID"));
                ea.setEmployeeName(rsEmp.getString("EmployeeName"));
                list.add(ea);
            }

            // 2. Lấy các đơn nghỉ trong khoảng thời gian
            String sqlLeave = "SELECT Created_by, StartDate, EndDate FROM LeaveRequest "
                    + "WHERE (StartDate <= ? AND EndDate >= ?)";

            PreparedStatement stmLeave = connection.prepareStatement(sqlLeave);
            stmLeave.setDate(1, endDate);
            stmLeave.setDate(2, startDate);

            ResultSet rsLeave = stmLeave.executeQuery();

            while (rsLeave.next()) {
                int empId = rsLeave.getInt("Created_by");
                Date start = rsLeave.getDate("StartDate");
                Date end = rsLeave.getDate("EndDate");

                // Tìm employee trong list
                for (EmployeeAttendance ea : list) {
                    if (ea.getId()== empId) {
                        // đánh dấu các ngày nghỉ
                        Date current = start;
                        while (!current.after(end)) {
                            ea.addStatus(current, "LEAVE");
                            current = new Date(current.getTime() + 24 * 60 * 60 * 1000); // tăng 1 ngày
                        }
                        break;
                    }
                }
            }

            // 3. Các ngày không có nghỉ thì mặc định WORK
            for (EmployeeAttendance ea : list) {
                Date current = startDate;
                while (!current.after(endDate)) {
                    ea.getStatusMap().putIfAbsent(current, "WORK");
                    current = new Date(current.getTime() + 24 * 60 * 60 * 1000);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public ArrayList<EmployeeAttendance> list() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public EmployeeAttendance get(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void insert(EmployeeAttendance model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void update(EmployeeAttendance model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void delete(EmployeeAttendance model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
