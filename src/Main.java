import menu.AdminMenu;
import menu.EmployeeMenu;

import model.*;
import service.*;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        // Services
        authenticationService authenticationService = new authenticationService();

        registrationService registrationService = new registrationService();

        leaveService leaveService = new leaveService();

        employeeService employeeService =
                new employeeService(
                        leaveService,
                        authenticationService
                );

        adminService adminService =
                new adminService(
                        registrationService,
                        leaveService
                );

        while (true) {

            System.out.println("\n===== Employee Leave Management System =====");
            System.out.println("1. Employee Login");
            System.out.println("2. Admin Login");
            System.out.println("3. Register");
            System.out.println("4. Exit");
            System.out.print("Enter Choice: ");

            int choice = sc.nextInt();

            switch (choice) {

                case 1:

                    System.out.print("ID : ");
                    int empId = sc.nextInt();

                    System.out.print("Password : ");
                    String empPassword = sc.next();

                    Employee employee =
                            authenticationService.employeeLogin(
                                    empId,
                                    empPassword
                            );

                    if (employee != null) {

                        System.out.println("Login Successful!");

                        EmployeeMenu.show(
                                employee,
                                employeeService,
                                sc
                        );

                    } else {

                        System.out.println("Invalid Credentials");
                    }

                    break;

                case 2:

                    System.out.print("ID : ");
                    int adminId = sc.nextInt();

                    System.out.print("Password : ");
                    String adminPassword = sc.next();

                    Admin admin =
                            authenticationService.adminLogin(
                                    adminId,
                                    adminPassword
                            );

                    if (admin != null) {

                        System.out.println("Login Successful!");

                        AdminMenu.show(
                                admin,
                                adminService,
                                sc
                        );

                    } else {

                        System.out.println("Invalid Credentials");
                    }

                    break;

                case 3:

                    sc.nextLine();

                    System.out.print("Enter Name : ");
                    String name = sc.nextLine();

                    System.out.print("Enter Password : ");
                    String password = sc.nextLine();

                    System.out.print("Enter Role (EMPLOYEE/ADMIN) : ");

                    Role role =
                            Role.valueOf(
                                    sc.nextLine().toUpperCase()
                            );

                    registrationRequest request =
                            new registrationRequest(
                                    name,
                                    password,
                                    role
                            );

                    if (registrationService.register(request)) {

                        System.out.println(
                                "Registration Submitted Successfully.");
                        System.out.println("Registration ID : " + request.getRequestId());

                    } else {

                        System.out.println(
                                "Registration Failed."
                        );
                    }

                    break;

                case 4:

                    System.out.println("Thank You!");

                    sc.close();

                    return;

                default:

                    System.out.println("Invalid Choice");
            }
        }
    }
}