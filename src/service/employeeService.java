package service;

import dao.EmployeeDAO;
import model.Employee;
import model.leaveRequest;

import java.time.LocalDate;

public class employeeService {

    private EmployeeDAO employeeDAO;
    private leaveService leaveService;
    private authenticationService authenticationService;

    public employeeService(leaveService leaveService,
                           authenticationService authenticationService) {

        this.leaveService = leaveService;
        this.authenticationService = authenticationService;
        this.employeeDAO = new EmployeeDAO();
    }

    // Apply Leave
    public boolean applyLeave(leaveRequest request) {

        return leaveService.applyLeave(request);
    }

    // Extend Leave
    public boolean extendLeave(int leaveId,
                               LocalDate newEndDate) {

        return leaveService.extendLeave(
                leaveId,
                newEndDate
        );
    }

    // View Leave History
    public void viewLeaveHistory(int employeeId) {

        leaveService.viewLeaveHistory(employeeId);
    }

    // View Leave Balance
    public void viewLeaveBalance(int employeeId) {

        leaveService.viewLeaveBalance(employeeId);
    }

    // Change Password
    public boolean changePassword(int employeeId,
                                  String oldPassword,
                                  String newPassword) {

        Employee employee =
                authenticationService.employeeLogin(
                        employeeId,
                        oldPassword
                );

        if (employee == null) {

            System.out.println("Incorrect Old Password.");
            return false;
        }

        return employeeDAO.updatePassword(
                employeeId,
                newPassword
        );
    }
}