package entities;

public class Assistance extends Person {
    private String password;
    private String teacherName;

    public Assistance() {
    }

    public Assistance(String name, String password) {
        super(name);
        this.password = password;
        this.teacherName = "";
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }
}
