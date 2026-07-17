package model;

public class Employee extends User{
    private int casualLeaveBalance;
    private int sickLeaveBalance;
    private int earnedLeaveBalance;
    //constructor
    public Employee(int id, String name, String password) {

        super(id, name, password);
        this.casualLeaveBalance = 10;
        this.earnedLeaveBalance = 10;
        this.sickLeaveBalance = 10;
    }
    public Employee(int id, String name, String password, int casualLeaveBalance, int sickLeaveBalance, int earnedLeaveBalance) {

        super(id, name, password);
        this.casualLeaveBalance = casualLeaveBalance;
        this.sickLeaveBalance = sickLeaveBalance;
        this.earnedLeaveBalance = earnedLeaveBalance;


    }

    public int getCasualLeaveBalance(){
        return casualLeaveBalance;
    }
    public int getSickLeaveBalance(){
        return sickLeaveBalance;
    }
    public int getEarnedLeaveBalance(){
        return earnedLeaveBalance;
    }

    public void setCasualLeaveBalance(int cl){
        this.casualLeaveBalance = cl;
    }
    public void setSickLeaveBalance(int sl){
        this.sickLeaveBalance = sl;
    }
    public void setEarnedLeaveBalance(int el){
        this.earnedLeaveBalance = el;
    }
    public void deductCasualLeave(int days) {
        this.casualLeaveBalance -= days;
    }

    public void deductSickLeave(int days) {
        this.sickLeaveBalance -= days;
    }

    public void deductEarnedLeave(int days) {
        this.earnedLeaveBalance -= days;
    }
}
