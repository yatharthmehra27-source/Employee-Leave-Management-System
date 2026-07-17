package service;

import dao.AdminDAO;
import dao.EmployeeDAO;
import dao.RegistrationDAO;
import model.Admin;
import model.Employee;
import model.Role;
import model.registrationRequest;

import java.util.ArrayList;

public class adminService {

    private EmployeeDAO employeeDAO;
    private AdminDAO adminDAO;
    private RegistrationDAO registrationDAO;

    private registrationService registrationService;
    private leaveService leaveService;

    public adminService(registrationService registrationService,
                        leaveService leaveService) {

        this.registrationService = registrationService;
        this.leaveService = leaveService;

        employeeDAO = new EmployeeDAO();
        adminDAO = new AdminDAO();
        registrationDAO = new RegistrationDAO();
    }

    // ==========================
    // Registration Management
    // ==========================

    public void viewPendingRegistrations() {

        ArrayList<registrationRequest> requests =
                registrationService.getPendingRegistrations();

        if (requests.isEmpty()) {
            System.out.println("No Pending Registrations.");
            return;
        }

        for (registrationRequest request : requests) {

            System.out.println("----------------------");
            System.out.println("Request ID : " + request.getRequestId());
            System.out.println("Name : " + request.getName());
            System.out.println("Role : " + request.getRole());
        }
    }

    public boolean approveRegistration(int requestId) {

        registrationRequest request =
                registrationService.findRegistration(requestId);

        if (request == null) {
            return false;
        }

        if (request.getRole() == Role.EMPLOYEE) {

            Employee employee =
                    new Employee(
                            employeeDAO.generateEmployeeId(),
                            request.getName(),
                            request.getPassword()
                    );

            employeeDAO.addEmployee(employee);

            System.out.println("Employee Approved.");
            System.out.println("Assigned Employee ID : "
                    + employee.getId());

        } else {

            Admin admin =
                    new Admin(
                            adminDAO.generateAdminId(),
                            request.getName(),
                            request.getPassword()
                    );

            adminDAO.addAdmin(admin);

            System.out.println("Admin Approved.");
            System.out.println("Assigned Admin ID : "
                    + admin.getId());
        }

        registrationDAO.deleteRequest(requestId);

        return true;
    }

    public boolean rejectRegistration(int requestId) {

        boolean removed =
                registrationService.removePendingRegistration(requestId);

        if (removed) {
            System.out.println("Registration Rejected.");
        }

        return removed;
    }

    // ==========================
    // Leave Management
    // ==========================

    public boolean approveLeave(int leaveId) {

        return leaveService.approveLeave(leaveId);
    }

    public boolean rejectLeave(int leaveId) {

        return leaveService.rejectLeave(leaveId);
    }

    public void viewPendingLeaves() {

        leaveService.viewPendingLeaves();
    }

    public void viewEmployeesOnLeave() {

        leaveService.viewEmployeesOnLeave();
    }

    // ==========================
    // Employee Management
    // ==========================

    public Employee addEmployee(String name,
                                String password) {

        Employee employee =
                new Employee(
                        employeeDAO.generateEmployeeId(),
                        name,
                        password
                );

        employeeDAO.addEmployee(employee);

        return employee;
    }

    public boolean removeEmployee(int employeeId) {

        return employeeDAO.deleteEmployee(employeeId);
    }

    public Employee getEmployee(int employeeId) {

        return employeeDAO.getEmployeeById(employeeId);
    }

    public ArrayList<Employee> getAllEmployees() {

        return employeeDAO.getAllEmployees();
    }

    // ==========================
    // Admin Management
    // ==========================

    public Admin addAdmin(String name,
                          String password) {

        Admin admin =
                new Admin(
                        adminDAO.generateAdminId(),
                        name,
                        password
                );

        adminDAO.addAdmin(admin);

        return admin;
    }

    public boolean removeAdmin(int adminId) {

        return adminDAO.deleteAdmin(adminId);
    }

    public Admin getAdmin(int adminId) {

        return adminDAO.getAdminById(adminId);
    }

    public ArrayList<Admin> getAllAdmins() {

        return adminDAO.getAllAdmins();
    }
}