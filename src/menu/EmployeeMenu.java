package menu;

import model.Employee;
import model.leaveRequest;
import model.leaveType;
import service.employeeService;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Scanner;

public class EmployeeMenu {
    public static void show(Employee employee, employeeService employeeService, Scanner sc){
        while(true){
            System.out.println("\n ======== Employee Menu ========");
            System.out.println("1. Apply Leave");
            System.out.println("2. Extend Leave");
            System.out.println("3. View Leave History");
            System.out.println("4. View Leave Balance");
            System.out.println("5. Change Password");
            System.out.println("6. Logout");
            System.out.print("Enter Choice: ");
            int choice = sc.nextInt();
            sc.nextLine();
            switch(choice){
                case 1:
                    System.out.println("Leave Type");
                    System.out.println("1. CASUAL");
                    System.out.println("2. SICK");
                    System.out.println("3. EARNED");
                    int typeChoice = sc.nextInt();
                    sc.nextLine();
                    leaveType LType;

                    switch (typeChoice) {
                        case 1:
                            LType = leaveType.CASUAL;
                            break;
                        case 2:
                            LType = leaveType.SICK;
                            break;
                        case 3:
                            LType = leaveType.EARNED;
                            break;
                        default:
                            System.out.println("Invalid Leave Type.");
                            continue;
                    }
                    System.out.print("Start Date (yyyy-mm-dd): ");
                    LocalDate startDate = LocalDate.parse(sc.nextLine());

                    System.out.print("End Date (yyyy-mm-dd): ");
                    LocalDate endDate = LocalDate.parse(sc.nextLine());

                    System.out.print("Reason: ");
                    String reason = sc.nextLine();
                    int totalDays = (int) ChronoUnit.DAYS.between(startDate, endDate) + 1;

                    leaveRequest request = new leaveRequest(employee.getId(), startDate, endDate, totalDays, reason, LType);

                    if (employeeService.applyLeave(request)) {
                        System.out.println("Leave Applied Successfully.");
                        System.out.println("Leave ID : " + request.getLeaveId());
                    } else {
                        System.out.println("Unable to Apply Leave.");
                    }

                    break;
                case 2:
                    System.out.print("Enter Leave ID: ");
                    int leaveId = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Enter New End Date (yyyy-mm-dd): ");
                    LocalDate newEndDate = LocalDate.parse(sc.nextLine());
                    if (employeeService.extendLeave(leaveId, newEndDate)) {
                        System.out.println("Leave Extended Successfully.");
                    } else {
                        System.out.println("Unable to Extend Leave.");
                    }
                    break;
                case 3:
                    employeeService.viewLeaveHistory(employee.getId());
                    break;
                case 4:
                    employeeService.viewLeaveBalance(employee.getId());
                    break;
                case 5:
                    System.out.print("Enter Old Password: ");
                    String oldPassword = sc.nextLine();
                    System.out.print("Enter New Password: ");
                    String newPassword = sc.nextLine();
                    if (employeeService.changePassword(
                            employee.getId(),
                            oldPassword,
                            newPassword)) {

                        System.out.println("Password Changed Successfully.");
                    } else {
                        System.out.println("Incorrect Old Password.");
                    }
                    break;
                case 6:
                    System.out.println("Logged Out Successfully.");
                    return;
                default:
                    System.out.println("INVALID CHOICE!");
            }
        }
    }
}