package model;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
public class leaveRequest {
    private int leaveId;
    private int employeeId;
    protected LocalDate startDate;
    protected LocalDate endDate;
    private int totalDays;
    private String reason;
    private leaveType LeaveType;
    private leaveStatus LeaveStatus;

    public leaveRequest( int employeeId, LocalDate startDate, LocalDate endDate, int totalDays, String reason,leaveType LeaveType) {
            this.employeeId = employeeId;
            this.startDate = startDate;
            this.endDate = endDate;
            this.totalDays = totalDays;
            this.reason = reason;
            this.LeaveType= LeaveType;
            this.LeaveStatus= LeaveStatus.PENDING;
    }

    public int getLeaveId() {
        return leaveId;
    }
    public int getEmployeeId() {
        return employeeId;
    }
    public leaveStatus getStatus() {return LeaveStatus;}
    public String getReason() {return reason;}
    public LocalDate getStartDate() {return startDate;}
    public LocalDate getEndDate() {return endDate;}
    public leaveType getLeaveType() {return LeaveType;}
    public int getTotalDays() {return totalDays;}
    public void displayLeave() {
        System.out.println("Leave ID: " + leaveId);
        System.out.println("Employee ID: " + employeeId);
        System.out.println("Days: " + totalDays);
        System.out.println("Reason: " + reason);
        System.out.println("Status: " + LeaveStatus);
    }

    public void setStatus(leaveStatus status) {
        this.LeaveStatus = status;
    }
    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
    public void setTotalDays(int totalDays) {
        this.totalDays = totalDays;
    }
    public void setLeaveId(int leaveId) {this.leaveId = leaveId;}
}