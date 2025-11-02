package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.Role;

public class RoleDAO extends DBContext<Role> {

    public List<String> getRole(int userId) {
        List<String> permissions = new ArrayList<>();
        try {
            String sql = """
                     SELECT DISTINCT f.FeatureKey
                                     FROM [User] u 
                                     JOIN UserRole ur ON u.UserID = ur.UserID
                                     JOIN [Role] r ON ur.RoleID = r.RoleID
                                     JOIN RoleFeature rf ON r.RoleID = rf.RoleID
                                     JOIN Feature f ON rf.FeatureID = f.FeatureID
                                     WHERE u.UserID = ?
                     """;

            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, userId);

            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                permissions.add(rs.getString("FeatureKey"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return permissions;
    }

    @Override
    public ArrayList<Role> list() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Role get(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void insert(Role model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void update(Role model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void delete(Role model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
