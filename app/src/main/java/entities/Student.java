package entities;

public class Student extends Person {
    private String teacherName;

    public Student(String name,String teacherName) {
        super(name);
        this.teacherName = teacherName;
    }

    public Student() {
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }
}
