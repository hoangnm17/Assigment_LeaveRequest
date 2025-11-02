package dal;

import java.util.ArrayList;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.Employee;
import models.User;

public class UserDAO extends DBContext<User> {

    public User getUser(String email, String password) {
        try {
            String sql = """
                        SELECT u.UserID, u.UserName, u.[Password], u.IsActive, 
                                e.EmployeeID, e.EmployeeName, e.Email, e.DepartmentID
                            FROM [User] u
                            JOIN Employee e ON u.EmployeeID = e.EmployeeID
                            WHERE u.UserName = ? AND u.IsActive = 1
                        """;
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, email);

            ResultSet rs = stm.executeQuery();

            if (rs.next()) {
                Employee emp = new Employee();
                emp.setId(rs.getInt("EmployeeID"));
                emp.setEmployeeName(rs.getString("EmployeeName"));
                emp.setEmail(rs.getString("Email"));
                emp.setDepartmentID(rs.getInt("DepartmentID"));

                User user = new User();
                user.setId(rs.getInt("UserID"));
                user.setUserName(rs.getString("UserName"));
                user.setPassword(rs.getString("Password"));
                user.setActive(rs.getBoolean("IsActive"));
                user.setEmployee(emp);

                return user;
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            closeConnection();
        }

        return null;
    }

    @Override
    public ArrayList<User> list() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public User get(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void insert(User model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void update(User model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void delete(User model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
