package dao;

import model.Role;
import model.registrationRequest;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;

public class RegistrationDAO {

    // Add Registration Request
    public boolean addRequest(registrationRequest request) {

        String sql = "INSERT INTO registration_requests(request_id, name, password, role) VALUES (?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, request.getRequestId());
            ps.setString(2, request.getName());
            ps.setString(3, request.getPassword());
            ps.setString(4, request.getRole().name());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("ERROR: " + e.getMessage());
        }

        return false;
    }

    // Delete Registration Request
    public boolean deleteRequest(int requestId) {

        String sql = "DELETE FROM registration_requests WHERE request_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, requestId);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("ERROR: " + e.getMessage());
        }

        return false;
    }

    // Get Registration Request By ID
    public registrationRequest getRequestById(int requestId) {

        String sql = "SELECT * FROM registration_requests WHERE request_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, requestId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                registrationRequest request =
                        new registrationRequest(
                                rs.getString("name"),
                                rs.getString("password"),
                                Role.valueOf(rs.getString("role"))
                        );

                request.setRequestId(rs.getInt("request_id"));

                return request;
            }

        } catch (SQLException e) {
            System.out.println("ERROR: " + e.getMessage());
        }

        return null;
    }

    // Get All Registration Requests
    public ArrayList<registrationRequest> getAllRequest() {

        ArrayList<registrationRequest> requests = new ArrayList<>();

        String sql = "SELECT * FROM registration_requests";

        try (Connection con = DBConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {

                registrationRequest request =
                        new registrationRequest(
                                rs.getString("name"),
                                rs.getString("password"),
                                Role.valueOf(rs.getString("role"))
                        );

                request.setRequestId(rs.getInt("request_id"));

                requests.add(request);
            }

        } catch (SQLException e) {
            System.out.println("ERROR: " + e.getMessage());
        }

        return requests;
    }

    // Check If Request Exists
    public boolean requestExists(int requestId) {

        String sql = "SELECT * FROM registration_requests WHERE request_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, requestId);

            ResultSet rs = ps.executeQuery();

            return rs.next();

        } catch (SQLException e) {
            System.out.println("ERROR: " + e.getMessage());
        }

        return false;
    }

    public int generateRequestId() {

        String sql = "SELECT MAX(request_id) FROM registration_requests";

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