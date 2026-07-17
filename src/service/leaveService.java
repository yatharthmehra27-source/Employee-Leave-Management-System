package service;

import dao.EmployeeDAO;
import dao.LeaveDAO;
import model.Employee;
import model.leaveRequest;
import model.leaveStatus;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class leaveService {

    private LeaveDAO leaveDAO;
    private EmployeeDAO employeeDAO;

    public leaveService() {
        leaveDAO = new LeaveDAO();
        employeeDAO = new EmployeeDAO();
    }

    // Apply Leave
    public boolean applyLeave(leaveRequest request) {

        // Start date cannot be in the past
        if (request.getStartDate().isBefore(LocalDate.now())) {
            System.out.println("Cannot apply leave for past dates.");
            return false;
        }

        // End date cannot be before start date
        if (request.getEndDate().isBefore(request.getStartDate())) {
            System.out.println("End date cannot be before start date.");
            return false;
        }

        request.setLeaveId(leaveDAO.generateLeaveId());

        return leaveDAO.applyLeave(request);
    }

    // Extend Leave
    public boolean extendLeave(int leaveId, LocalDate newEndDate) {

        leaveRequest request = leaveDAO.getLeaveById(leaveId);

        if (request == null) {
            return false;
        }

        if (newEndDate.isBefore(request.getStartDate())) {
            System.out.println("Invalid Date!");
            return false;
        }

        if (request.getStatus() == leaveStatus.REJECTED) {
            System.out.println("Rejected leave cannot be extended.");
            return false;
        }

        int totalDays =
                (int) ChronoUnit.DAYS.between(
                        request.getStartDate(),
                        newEndDate
                ) + 1;

        return leaveDAO.extendLeave(
                leaveId,
                newEndDate,
                totalDays
        );
    }

    // Approve Leave
    public boolean approveLeave(int leaveId) {

        leaveRequest request = leaveDAO.getLeaveById(leaveId);

        if (request == null)
            return false;

        Employee employee =
                employeeDAO.getEmployeeById(
                        request.getEmployeeId()
                );

        if (employee == null)
            return false;

        switch (request.getLeaveType()) {

            case CASUAL:

                if (employee.getCasualLeaveBalance()
                        < request.getTotalDays()) {

                    System.out.println(
                            "Insufficient Casual Leave."
                    );

                    return false;
                }

                employee.deductCasualLeave(
                        request.getTotalDays()
                );

                break;

            case SICK:

                if (employee.getSickLeaveBalance()
                        < request.getTotalDays()) {

                    System.out.println(
                            "Insufficient Sick Leave."
                    );

                    return false;
                }

                employee.deductSickLeave(
                        request.getTotalDays()
                );

                break;

            case EARNED:

                if (employee.getEarnedLeaveBalance()
                        < request.getTotalDays()) {

                    System.out.println(
                            "Insufficient Earned Leave."
                    );

                    return false;
                }

                employee.deductEarnedLeave(
                        request.getTotalDays()
                );

                break;
        }

        employeeDAO.updateLeaveBalance(employee);

        return leaveDAO.approveLeave(leaveId);
    }

    // Reject Leave
    public boolean rejectLeave(int leaveId) {

        return leaveDAO.rejectLeave(leaveId);
    }

    // View Pending Leaves
    public void viewPendingLeaves() {

        ArrayList<leaveRequest> pendingLeaves = leaveDAO.getPendingLeaves();

        if (pendingLeaves.isEmpty()) {
            System.out.println("No Pending Leaves.");
            return;
        }

        for (leaveRequest request : pendingLeaves) {

            System.out.println("--------------------------------");
            System.out.println("Leave ID    : " + request.getLeaveId());
            System.out.println("Employee ID : " + request.getEmployeeId());
            System.out.println("Type        : " + request.getLeaveType());
            System.out.println("Start Date  : " + request.getStartDate());
            System.out.println("End Date    : " + request.getEndDate());
            System.out.println("Total Days  : " + request.getTotalDays());
            System.out.println("Reason      : " + request.getReason());
            System.out.println("Status      : " + request.getStatus());
            System.out.println("--------------------------------");
        }
    }
    // View Employees On Leave
    public void viewEmployeesOnLeave() {

        LocalDate today = LocalDate.now();

        for (leaveRequest request :
                leaveDAO.getApprovedLeaves()) {

            if (!today.isBefore(request.getStartDate())
                    &&
                    !today.isAfter(request.getEndDate())) {

                System.out.println("-------------------");

                System.out.println(
                        "Employee ID : "
                                + request.getEmployeeId()
                );

                System.out.println(
                        "Leave ID : "
                                + request.getLeaveId()
                );

                System.out.println(
                        "Leave Type : "
                                + request.getLeaveType()
                );
            }
        }
    }

    // View Leave History
    public void viewLeaveHistory(int employeeId) {

        ArrayList<leaveRequest> history =
                leaveDAO.getEmployeeLeaves(employeeId);

        if (history.isEmpty()) {

            System.out.println(
                    "No Leave History."
            );

            return;
        }

        for (leaveRequest request : history) {

            System.out.println("-------------------");
            System.out.println("Leave ID : "
                    + request.getLeaveId());

            System.out.println("Status : "
                    + request.getStatus());

            System.out.println("Type : "
                    + request.getLeaveType());

            System.out.println("Start : "
                    + request.getStartDate());

            System.out.println("End : "
                    + request.getEndDate());

            System.out.println("Days : "
                    + request.getTotalDays());

            System.out.println("Reason : "
                    + request.getReason());
        }
    }

    // View Leave Balance
    public void viewLeaveBalance(int employeeId) {

        Employee employee =
                employeeDAO.getEmployeeById(employeeId);

        if (employee == null) {

            System.out.println("Employee Not Found.");

            return;
        }

        System.out.println("-------------------");
        System.out.println("Casual : "
                + employee.getCasualLeaveBalance());

        System.out.println("Sick : "
                + employee.getSickLeaveBalance());

        System.out.println("Earned : "
                + employee.getEarnedLeaveBalance());
    }
}