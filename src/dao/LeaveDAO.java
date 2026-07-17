package dao;

import model.leaveRequest;
import model.leaveStatus;
import model.leaveType;
import util.DBConnection;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class LeaveDAO {

    // Generate Leave ID (only if not using AUTO_INCREMENT)
    public int generateLeaveId() {

        String sql = "SELECT MAX(leave_id) FROM leave_requests";

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

    // Apply Leave
    public boolean applyLeave(leaveRequest request) {

        String sql = "INSERT INTO leave_requests VALUES (?,?,?,?,?,?,?,?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, request.getLeaveId());
            ps.setInt(2, request.getEmployeeId());
            ps.setDate(3, Date.valueOf(request.getStartDate()));
            ps.setDate(4, Date.valueOf(request.getEndDate()));
            ps.setInt(5, request.getTotalDays());
            ps.setString(6, request.getReason());
            ps.setString(7, request.getLeaveType().name());
            ps.setString(8, request.getStatus().name());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("ERROR: " + e.getMessage());
        }

        return false;
    }

    // Get Leave By ID
    public leaveRequest getLeaveById(int leaveId) {

        String sql = "SELECT * FROM leave_requests WHERE leave_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, leaveId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                leaveRequest request = new leaveRequest(
                        rs.getInt("employee_id"),
                        rs.getDate("start_date").toLocalDate(),
                        rs.getDate("end_date").toLocalDate(),
                        rs.getInt("total_days"),
                        rs.getString("reason"),
                        leaveType.valueOf(rs.getString("leave_type"))
                );

                request.setLeaveId(rs.getInt("leave_id"));
                request.setStatus(
                        leaveStatus.valueOf(rs.getString("leave_status"))
                );

                return request;
            }

        } catch (SQLException e) {
            System.out.println("ERROR: " + e.getMessage());
        }

        return null;
    }

    // Get All Leaves
    public ArrayList<leaveRequest> getAllLeaves() {

        ArrayList<leaveRequest> leaves = new ArrayList<>();

        String sql = "SELECT * FROM leave_requests";

        try (Connection con = DBConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {

                leaveRequest request = new leaveRequest(
                        rs.getInt("employee_id"),
                        rs.getDate("start_date").toLocalDate(),
                        rs.getDate("end_date").toLocalDate(),
                        rs.getInt("total_days"),
                        rs.getString("reason"),
                        leaveType.valueOf(rs.getString("leave_type"))
                );

                request.setLeaveId(rs.getInt("leave_id"));
                request.setStatus(
                        leaveStatus.valueOf(rs.getString("leave_status"))
                );

                leaves.add(request);
            }

        } catch (SQLException e) {
            System.out.println("ERROR: " + e.getMessage());
        }

        return leaves;
    }

    // Get Employee Leave History
    public ArrayList<leaveRequest> getEmployeeLeaves(int employeeId) {

        ArrayList<leaveRequest> leaves = new ArrayList<>();

        String sql = "SELECT * FROM leave_requests WHERE employee_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, employeeId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                leaveRequest request = new leaveRequest(
                        rs.getInt("employee_id"),
                        rs.getDate("start_date").toLocalDate(),
                        rs.getDate("end_date").toLocalDate(),
                        rs.getInt("total_days"),
                        rs.getString("reason"),
                        leaveType.valueOf(rs.getString("leave_type"))
                );

                request.setLeaveId(rs.getInt("leave_id"));
                request.setStatus(
                        leaveStatus.valueOf(rs.getString("leave_status"))
                );

                leaves.add(request);
            }

        } catch (SQLException e) {
            System.out.println("ERROR: " + e.getMessage());
        }

        return leaves;
    }

    // Pending Leaves
    public ArrayList<leaveRequest> getPendingLeaves() {

        ArrayList<leaveRequest> pending = new ArrayList<>();

        String sql = "SELECT * FROM leave_requests WHERE leave_status = 'PENDING'";

        try (Connection con = DBConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {

                leaveRequest request = new leaveRequest(
                        rs.getInt("employee_id"),
                        rs.getDate("start_date").toLocalDate(),
                        rs.getDate("end_date").toLocalDate(),
                        rs.getInt("total_days"),
                        rs.getString("reason"),
                        leaveType.valueOf(rs.getString("leave_type"))
                );

                request.setLeaveId(rs.getInt("leave_id"));
                request.setStatus(
                        leaveStatus.valueOf(rs.getString("leave_status"))
                );

                pending.add(request);
            }

        } catch (SQLException e) {
            System.out.println("ERROR: " + e.getMessage());
        }

        return pending;
    }

    // Approve Leave
    public boolean approveLeave(int leaveId) {

        String sql =
                "UPDATE leave_requests SET leave_status='APPROVE' WHERE leave_id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, leaveId);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("ERROR: " + e.getMessage());
        }

        return false;
    }

    // Reject Leave
    public boolean rejectLeave(int leaveId) {

        String sql =
                "UPDATE leave_requests SET leave_status='REJECTED' WHERE leave_id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, leaveId);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("ERROR: " + e.getMessage());
        }

        return false;
    }

    // Extend Leave
    public boolean extendLeave(int leaveId,
                               LocalDate newEndDate,
                               int totalDays) {

        String sql =
                "UPDATE leave_requests SET end_date=?, total_days=? WHERE leave_id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setDate(1, Date.valueOf(newEndDate));
            ps.setInt(2, totalDays);
            ps.setInt(3, leaveId);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("ERROR: " + e.getMessage());
        }

        return false;
    }

    // Delete Leave
    public boolean deleteLeave(int leaveId) {

        String sql = "DELETE FROM leave_requests WHERE leave_id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, leaveId);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("ERROR: " + e.getMessage());
        }

        return false;
    }

    // Get Approved Leaves
    public ArrayList<leaveRequest> getApprovedLeaves() {

        ArrayList<leaveRequest> approved = new ArrayList<>();

        String sql = "SELECT * FROM leave_requests WHERE leave_status='APPROVE'";

        try (Connection con = DBConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {

                leaveRequest request = new leaveRequest(
                        rs.getInt("employee_id"),
                        rs.getDate("start_date").toLocalDate(),
                        rs.getDate("end_date").toLocalDate(),
                        rs.getInt("total_days"),
                        rs.getString("reason"),
                        leaveType.valueOf(rs.getString("leave_type"))
                );

                request.setLeaveId(rs.getInt("leave_id"));
                request.setStatus(
                        leaveStatus.valueOf(rs.getString("leave_status"))
                );

                approved.add(request);
            }

        } catch (SQLException e) {
            System.out.println("ERROR: " + e.getMessage());
        }

        return approved;
    }
}