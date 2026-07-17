package dao;
import model.Employee;

import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;


public class EmployeeDAO {
    public boolean addEmployee(Employee employee) {
        String s = "INSERT INTO EMPLOYEES VALUES (?,?,?,?,?,?)";
        Connection con = DBConnection.getConnection();
        try (PreparedStatement p = con.prepareStatement(s)) {
            p.setInt(1, employee.getId());
            p.setString(2, employee.getName());
            p.setString(3, employee.getPassword());
            p.setInt(4, employee.getCasualLeaveBalance());
            p.setInt(5, employee.getSickLeaveBalance());
            p.setInt(6, employee.getEarnedLeaveBalance());

            return p.executeUpdate() > 0;
        } catch (Exception e) {
            System.out.println("error" + e.getMessage());
        }
        return false;
    }

    public Employee getEmployeeById(int id) {
        String s = "SELECT * FROM EMPLOYEES WHERE employee_id =?";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement p = c.prepareStatement(s);) {
            p.setInt(1, id);

            ResultSet rs = p.executeQuery();
            if (rs.next()) {
                return new Employee(rs.getInt("employee_id"),
                        rs.getString("name"),
                        rs.getString("password"),
                        rs.getInt("casual_leave_balance"),
                        rs.getInt("sick_leave_balance"),
                        rs.getInt("earned_leave_balance"));

            }
        } catch (Exception e) {
            System.out.println("ERROR" + e.getMessage());
        }
        return null;
    }

    public ArrayList<Employee> getAllEmployees() {
        ArrayList<Employee> employees = new ArrayList<>();
        String s = "SELECT * FROM employees";
        try (Connection c = DBConnection.getConnection();
             Statement st = c.createStatement();
             ResultSet rs = st.executeQuery(s)) {
            while (rs.next()) {
                Employee emp = new Employee(rs.getInt("employee_id"),
                        rs.getString("name"),
                        rs.getString("password"),
                        rs.getInt("casual_leave_balance"),
                        rs.getInt("sick_leave_balance"),
                        rs.getInt("earned_leave_balance"));

                employees.add(emp);
            }
        } catch (Exception e) {
            System.out.println("Error" + e.getMessage());
        }
        return employees;
    }

    public boolean updatePassword(int id, String newPassword) {
        String s = "UPDATE employees SET password = ? WHERE employee_id = ? ";

        try (Connection c = DBConnection.getConnection();
             PreparedStatement p = c.prepareStatement(s)) {
            p.setString(1, newPassword);
            p.setInt(2, id);

            return p.executeUpdate() > 0;
        } catch (Exception e) {
            System.out.println("ERROR" + e.getMessage());
        }
        return false;
    }

    public boolean deleteEmployee(int employeeId) {
        String sql = "DELETE FROM employees WHERE employee_id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, employeeId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("ERROR" + e.getMessage());
        }

        return false;
    }
    public boolean updateLeaveBalance(Employee e){
        String s = "UPDATE employees SET casual_leave_balance=?, sick_leave_balance=?, earned_leave_balance=?  WHERE employee_id=?";
        try(Connection c = DBConnection.getConnection();
            PreparedStatement p = c.prepareStatement(s)){
           p.setInt(1,e.getCasualLeaveBalance());
           p.setInt (2,e.getSickLeaveBalance());
            p.setInt(3,e.getEarnedLeaveBalance());
            p.setInt(4,e.getId());

           return p.executeUpdate()>0;
        }catch(Exception a) {
            System.out.println("Error" + a.getMessage());

            return false;
        }
    }
    public boolean employeeExists(int employeeId) {

        String sql = "SELECT * FROM employees WHERE employee_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, employeeId);

            ResultSet rs = ps.executeQuery();

            return rs.next();

        } catch (SQLException e) {
            System.out.println("ERROR"+e.getMessage());
        }

        return false;
    }
    public Employee login(int id,String newPassword){
        String s = "SELECT * FROM EMPLOYEES WHERE EMPLOYEE_ID = ? AND PASSWORD=?";
        try(Connection con = DBConnection.getConnection();
            PreparedStatement p = con.prepareStatement(s)){
            p.setInt(1,id);
            p.setString(2,newPassword);

            ResultSet rs = p.executeQuery();
            if (rs.next()){
                return new Employee(rs.getInt("employee_id"),rs.getString("name"),rs.getString("password"), rs.getInt("casual_leave_balance"), rs.getInt("sick_leave_balance"), rs.getInt("earned_leave_balance"));
            }
        }catch(Exception e){
            System.out.println("ERROR"+e.getMessage());
        }
        return null;
    }

    public int generateEmployeeId() {

        String sql = "SELECT MAX(employee_id) FROM employees";

        try (Connection con = DBConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            if (rs.next()) {
                return rs.getInt(1) + 1;
            }

        } catch (SQLException e) {
            System.out.println("ERROR: " + e.getMessage());
        }

        return 1001;
    }

}