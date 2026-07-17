package service;

import dao.AdminDAO;
import dao.EmployeeDAO;
import model.Admin;
import model.Employee;

public class authenticationService {

    private EmployeeDAO employeeDAO;
    private AdminDAO adminDAO;

    public authenticationService() {
        employeeDAO = new EmployeeDAO();
        adminDAO = new AdminDAO();
    }

    // Employee Login
    public Employee employeeLogin(int id, String password) {
        return employeeDAO.login(id, password);
    }

    // Admin Login
    public Admin adminLogin(int id, String password) {
        return adminDAO.login(id, password);
    }
}
