package service;

import model.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

import java.io.FileWriter;
import java.io.IOException;

public class FileService {

    public void saveEmployees(HashMap<Integer, Employee> employees) {
        try (FileWriter fw = new FileWriter("data/employees.txt")) {
            for (Employee employee : employees.values()) {
                fw.write(employee.getId() + "," + employee.getName() + "," + employee.getPassword() + "," + employee.getCasualLeaveBalance() + "," + employee.getSickLeaveBalance() + "," + employee.getEarnedLeaveBalance() + "\n");
            }

        } catch (IOException e) {
            System.out.println("Error saving employees: " + e.getMessage());
        }
    }

    public void loadEmployees(HashMap<Integer, Employee> employees) {
        try (BufferedReader br = new BufferedReader(new FileReader("data/employees.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");

                int Id = Integer.parseInt(data[0]);
                String name = data[1];
                String password = data[2];
                int casualLeave = Integer.parseInt(data[3]);
                int sickLeave = Integer.parseInt(data[4]);
                int earnedLeave = Integer.parseInt(data[5]);

                Employee employee = new Employee(Id, name, password);
                employee.setCasualLeaveBalance(casualLeave);
                employee.setSickLeaveBalance(sickLeave);
                employee.setEarnedLeaveBalance(earnedLeave);

                employees.put(Id, employee);
            }

        } catch (Exception e) {
            System.out.println("Error reading file!" + e.getMessage());
        }
    }

    // Admin
    public void saveAdmins(HashMap<Integer, Admin> admins) {
        try (FileWriter fw = new FileWriter("data/admins.txt")) {
            for (Admin admin : admins.values()) {
                fw.write(admin.getId() + "," + admin.getName() + "," + admin.getPassword() + "\n");
            }


        } catch (IOException e) {
            System.out.println("Error saving employees: " + e.getMessage());
        }
    }

    public void loadAdmins(HashMap<Integer, Admin> admins) {
        try (BufferedReader br = new BufferedReader(new FileReader("data/admins.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");

                int Id = Integer.parseInt(data[0]);
                String name = data[1];
                String password = data[2];

                Admin admin = new Admin(Id, name, password);
                admins.put(Id, admin);
            }

        } catch (IOException e) {
            System.out.println("Error reading file!" + e.getMessage());
        }

    }

    // Registration Requests
    public void savePendingRequests(ArrayList<registrationRequest> pendingRequests) {
        try (FileWriter fw = new FileWriter("data/registrations.txt")) {
            for (registrationRequest p : pendingRequests) {
                fw.write(p.getRequestId() + "," + p.getName() + "," + p.getPassword() + "," + p.getRole() + "\n");
            }


        } catch (IOException e) {
            System.out.println("Error saving request " + e.getMessage());
        }
    }

    public void loadPendingRequests(ArrayList<registrationRequest> pendingRequests) {
        try (BufferedReader br = new BufferedReader(new FileReader("data/registrations.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");

                int requestId = Integer.parseInt(data[0]);
                String name = data[1];
                String password = data[2];
                Role role = Role.valueOf(data[3].trim().toUpperCase());
                registrationRequest request = new registrationRequest(name, password, role);
                request.setRequestId(requestId);
                pendingRequests.add(request);
            }

        } catch (Exception e) {
            System.out.println("Failed to load requests" + e.getMessage());
        }
    }

    // Leave Requests
    public void saveLeaveRequests(ArrayList<leaveRequest> leaveRequests) {
        try(FileWriter fw = new FileWriter("data/leavex.txt")){
            for(leaveRequest lr:leaveRequests) {
                fw.write(lr.getLeaveId() + "," + lr.getEmployeeId() + "," + lr.getStartDate() + "," + lr.getEndDate() + "," + lr.getTotalDays() + "," + lr.getReason() + "," + lr.getLeaveType() + "," + lr.getStatus() + "\n");
            }

        }catch (IOException e){
            System.out.println("Error saving leave request" + e.getMessage());
        }
    }

    public void loadLeaveRequests(ArrayList<leaveRequest> leaveRequests) {
        try(BufferedReader br = new BufferedReader(new FileReader("data/leavex.txt"))){

                String line;
                while((line = br.readLine()) != null){
                    String [] data = line.split(",");
                    int leaveId = Integer.parseInt(data[0]);
                    int empId = Integer.parseInt(data[1]);
                    LocalDate startDate = LocalDate.parse(data[2]);
                    LocalDate endDate = LocalDate.parse(data[3]);
                    int totalDays = Integer.parseInt(data[4]);
                    String reason = data[5];
                    leaveType type = leaveType.valueOf(data[6]);
                    leaveStatus status = leaveStatus.valueOf(data[7]);

                    leaveRequest request = new leaveRequest(empId, startDate, endDate, totalDays, reason, type);

                    request.setLeaveId(leaveId);
                    request.setStatus(status);

                    leaveRequests.add(request);
                }

        }catch(Exception e){
            System.out.println("Error loading  leave requests"+ e.getMessage());
        }
    }
}