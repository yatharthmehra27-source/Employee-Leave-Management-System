package dao;

import model.Admin;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;

public class AdminDAO {

    // Add Admin
    public boolean addAdmin(Admin admin) {

        String sql = "INSERT INTO admins VALUES (?,?,?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, admin.getId());
            ps.setString(2, admin.getName());
            ps.setString(3, admin.getPassword());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("ERROR: " + e.getMessage());
        }

        return false;
    }

    // Get Admin by ID
    public Admin getAdminById(int adminId) {

        String sql = "SELECT * FROM admins WHERE admin_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, adminId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                return new Admin(
                        rs.getInt("admin_id"),
                        rs.getString("name"),
                        rs.getString("password")
                );
            }

        } catch (SQLException e) {
            System.out.println("ERROR: " + e.getMessage());
        }

        return null;
    }

    // Get All Admins
    public ArrayList<Admin> getAllAdmins() {

        ArrayList<Admin> admins = new ArrayList<>();

        String sql = "SELECT * FROM admins";

        try (Connection con = DBConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {

                Admin admin = new Admin(
                        rs.getInt("admin_id"),
                        rs.getString("name"),
                        rs.getString("password")
                );

                admins.add(admin);
            }

        } catch (SQLException e) {
            System.out.println("ERROR: " + e.getMessage());
        }

        return admins;
    }

    // Delete Admin
    public boolean deleteAdmin(int adminId) {

        String sql = "DELETE FROM admins WHERE admin_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, adminId);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("ERROR: " + e.getMessage());
        }

        return false;
    }

    // Check if Admin Exists
    public boolean adminExists(int adminId) {

        String sql = "SELECT * FROM admins WHERE admin_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, adminId);

            ResultSet rs = ps.executeQuery();

            return rs.next();

        } catch (SQLException e) {
            System.out.println("ERROR: " + e.getMessage());
        }

        return false;
    }

    // Login
    public Admin login(int adminId, String password) {

        String sql = "SELECT * FROM admins WHERE admin_id = ? AND password = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, adminId);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                return new Admin(
                        rs.getInt("admin_id"),
                        rs.getString("name"),
                        rs.getString("password")
                );
            }

        } catch (SQLException e) {
            System.out.println("ERROR: " + e.getMessage());
        }

        return null;
    }

    // Generate Admin ID (only if you're NOT using AUTO_INCREMENT)
    public int generateAdminId() {

        String sql = "SELECT MAX(admin_id) FROM admins";

        try (Connection con = DBConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            if (rs.next()) {
                return rs.getInt(1) + 1;
            }

        } catch (SQLException e) {
            System.out.println("ERROR: " + e.getMessage());
        }

        return 1;
    }


}