package entities;

public class Teacher extends Person {
    private String password;

    public Teacher() {
    }

    public Teacher(String name, String password) {
        super(name);
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
