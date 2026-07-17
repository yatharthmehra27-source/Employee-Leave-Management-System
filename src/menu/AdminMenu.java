package menu;

import model.Admin;
import model.Employee;
import service.adminService;
import java.util.Scanner;

public class AdminMenu {
    public static void show(Admin admin, adminService adminService, Scanner sc) {

        while (true) {
            System.out.println("\n===== Admin Menu =====");
            System.out.println("1. View Pending Registrations");
            System.out.println("2. Approve Registration");
            System.out.println("3. Reject Registration");
            System.out.println("4. Add Employee");
            System.out.println("5. Remove Employee");
            System.out.println("6. Add Admin");
            System.out.println("7. Remove Admin");
            System.out.println("8. View Employees");
            System.out.println("9. View Pending Leaves");
            System.out.println("10. Approve Leave");
            System.out.println("11. Reject Leave");
            System.out.println("12. View Employees On Leave");
            System.out.println("13. Logout");

            System.out.print("Enter Choice: ");
            int choice = sc.nextInt();
            sc.nextLine();
            switch (choice) {
                case 1:
                    adminService.viewPendingRegistrations();
                    break;
                case 2:
                    System.out.print("Enter Request ID: ");
                    int requestId = sc.nextInt();
                    if (adminService.approveRegistration(requestId)) {
                        System.out.println("Registration Approved.");
                    } else {
                        System.out.println("Request Not Found.");
                    }
                    break;
                case 3:
                    System.out.print("Enter Request ID: ");
                    requestId = sc.nextInt();
                    if (adminService.rejectRegistration(requestId)) {
                        System.out.println("Registration Rejected.");
                    } else {
                        System.out.println("Request Not Found.");
                    }
                    break;
                case 4:

                    System.out.print("Enter Employee Name: ");
                    String employeeName = sc.nextLine();
                    System.out.print("Enter Password: ");
                    String employeePassword = sc.nextLine();

                    Employee employee = adminService.addEmployee(employeeName, employeePassword);

                    System.out.println("Employee Added Successfully.");
                    System.out.println("Assigned Employee ID : "
                            + employee.getId());

                    break;

                case 5:
                    System.out.print("Enter Employee ID: ");
                    int employeeId = sc.nextInt();
                    if (adminService.removeEmployee(employeeId)) {
                        System.out.println("Employee Removed.");
                    } else {
                        System.out.println("Employee Not Found.");
                    }
                    break;
                case 6:

                    System.out.print("Enter Admin Name: ");
                    String adminName = sc.nextLine();
                    System.out.print("Enter Password: ");
                    String adminPassword = sc.nextLine();

                    Admin newAdmin = adminService.addAdmin(adminName, adminPassword);

                    System.out.println("Admin Added Successfully.");
                    System.out.println("Assigned Admin ID : " + newAdmin.getId());
                    break;
                case 7:
                    System.out.print("Enter Admin ID: ");
                    int adminId = sc.nextInt();
                    if (adminService.removeAdmin(adminId)) {
                        System.out.println("Admin Removed.");
                    } else {
                        System.out.println("Admin Not Found.");
                    }
                    break;
                case 8:
                    for (Employee emp : adminService.getAllEmployees()) {
                        System.out.println("----------------------");
                        System.out.println("ID : " + emp.getId());
                        System.out.println("Name : " + emp.getName());
                        System.out.println("Casual : " + emp.getCasualLeaveBalance());
                        System.out.println("Sick    : " + emp.getSickLeaveBalance());
                        System.out.println("Earned  : " + emp.getEarnedLeaveBalance());
                    }
                    break;
                case 9:

                    adminService.viewPendingLeaves();
                    break;
                case 10:
                    System.out.print("Enter Leave ID: ");
                    int leaveId = sc.nextInt();
                    if (adminService.approveLeave(leaveId)) {
                        System.out.println("Leave Approved.");
                    } else {
                        System.out.println("Unable to Approve Leave.");
                    }
                    break;
                case 11:
                    System.out.print("Enter Leave ID: ");
                    leaveId = sc.nextInt();

                    if (adminService.rejectLeave(leaveId)) {
                        System.out.println("Leave Rejected.");
                    } else {
                        System.out.println("Leave Not Found.");
                    }
                    break;
                case 12:
                    adminService.viewEmployeesOnLeave();
                    break;
                case 13:
                    System.out.println("Logged Out Successfully.");
                    return;
                default:
                    System.out.println("Invalid Choice.");
            }
        }
    }
}