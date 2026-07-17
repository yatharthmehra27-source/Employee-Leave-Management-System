package model;
public class User {
    protected int id;
    protected String name;
    protected String password;
    public User (int id,String name,String password){
        this.id = id;
        this.name = name;
        this.password = password;
    }
    public int getId(){
        return this.id;
    }
    public String getName(){
        return this.name;
    }
    public String getPassword(){
        return this.password;
    }
    public void setPassword(String password){
        this.password= password;
    }
    public void displayInfo(){
        System.out.println("ID: "+ getId());
        System.out.println("Name: "+ getName());
        System.out.println("Password: "+ getPassword());
    }


}
