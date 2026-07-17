package model;

public class registrationRequest extends User {

    private int requestId;
    private Role role;
    public registrationRequest(String name,
                               String password,
                               Role role) {

        super(0, name, password);
        this.role = role;
    }

    public int getRequestId() {
        return requestId;
    }

    public Role getRole() {
        return role;
    }

    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}