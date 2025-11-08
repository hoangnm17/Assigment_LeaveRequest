package dal;

import java.util.ArrayList;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.employee.Department;
import model.employee.Employee;
import model.employee.JobTitle;

public class EmployeeDAO extends DBContext<Employee> {

    public Employee getEmployeeById(int employeeId) {
        String sql = "SELECT [EmployeeID], [EmployeeName], [Email], [DepartmentID], [JobTitleID], [ManagerID] "
                + "FROM [Employee] WHERE [EmployeeID] = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, employeeId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Employee emp = new Employee();
                    emp.setId(rs.getInt("EmployeeID")); // Giả sử model của bạn có setId
                    emp.setEmployeeName(rs.getString("EmployeeName"));
                    emp.setEmail(rs.getString("Email"));

                    Department dept = new Department();
                    dept.setId(rs.getInt("DepartmentID"));
                    emp.setDepartment(dept);

                    JobTitle jobt = new JobTitle();
                    jobt.setId(rs.getInt("JobTitleID"));

                    emp.setJobTitle(jobt);
                    emp.setManagerID(rs.getInt("ManagerID")); // Rất quan trọng
                    return emp;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(EmployeeDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public ArrayList<Employee> listByManagerHierarchy(int managerId) {
        ArrayList<Employee> list = new ArrayList<>();
        String sql = """
        WITH Org AS (
            SELECT *, 0 AS lvl
            FROM Employee
            WHERE EmployeeID = ?
            UNION ALL
            SELECT e.*, o.lvl + 1 AS lvl
            FROM Employee e
            INNER JOIN Org o ON e.ManagerID = o.EmployeeID
        )
        SELECT *
        FROM Org
        ORDER BY lvl, EmployeeName
    """;

        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setInt(1, managerId);
            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                Employee emp = new Employee();
                emp.setId(rs.getInt("EmployeeID"));
                emp.setEmployeeName(rs.getString("EmployeeName"));
                emp.setEmail(rs.getString("Email"));
                emp.setHireDate(rs.getDate("HireDate"));
                emp.setTerminationDate(rs.getDate("TerminationDate"));
                Department dept = new Department();
                dept.setId(rs.getInt("DepartmentID"));
                emp.setDepartment(dept);

                JobTitle jobt = new JobTitle();
                jobt.setId(rs.getInt("JobTitleID"));

                emp.setJobTitle(jobt);
                emp.setManagerID(rs.getInt("ManagerID"));
                list.add(emp);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }

        return list;
    }

    @Override
    public ArrayList<Employee> list() {
        ArrayList<Employee> list = new ArrayList<>();
        String sql = "SELECT * FROM Employee";

        try (PreparedStatement stm = connection.prepareStatement(sql); ResultSet rs = stm.executeQuery()) {

            while (rs.next()) {
                Employee emp = new Employee();
                emp.setId(rs.getInt("EmployeeID"));
                emp.setEmployeeName(rs.getString("EmployeeName"));
                emp.setEmail(rs.getString("Email"));
                emp.setHireDate(rs.getDate("HireDate"));
                emp.setTerminationDate(rs.getDate("TerminationDate"));
                Department dept = new Department();
                dept.setId(rs.getInt("DepartmentID"));
                emp.setDepartment(dept);

                JobTitle jobt = new JobTitle();
                jobt.setId(rs.getInt("JobTitleID"));

                emp.setJobTitle(jobt);
                emp.setManagerID(rs.getInt("ManagerID"));
                list.add(emp);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }

        return list;
    }

    @Override
    public Employee get(int id
    ) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void insert(Employee model
    ) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void update(Employee model
    ) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void delete(Employee model
    ) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
