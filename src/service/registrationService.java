package service;

import dao.RegistrationDAO;
import model.registrationRequest;

import java.util.ArrayList;

public class registrationService {

    private RegistrationDAO registrationDAO;

    public registrationService() {
        registrationDAO = new RegistrationDAO();
    }

    // Register
    public boolean register(registrationRequest request) {

        request.setRequestId(registrationDAO.generateRequestId());

        return registrationDAO.addRequest(request);
    }

    // View Pending Registrations
    public ArrayList<registrationRequest> getPendingRegistrations() {
        return registrationDAO.getAllRequest();
    }

    // Remove Registration
    public boolean removePendingRegistration(int requestId) {
        return registrationDAO.deleteRequest(requestId);
    }

    // Find Registration
    public registrationRequest findRegistration(int requestId) {
        return registrationDAO.getRequestById(requestId);
    }
}