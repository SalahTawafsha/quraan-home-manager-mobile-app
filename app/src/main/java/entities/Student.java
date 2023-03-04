package entities;

import java.util.ArrayList;
import java.util.Date;

public class Student extends Person {
    private String teacherName;
    private int absenceCount;
    private final ArrayList<Date> datesOfAbsence;

    public Student(String name, String teacherName) {
        super(name);
        this.teacherName = teacherName;
        datesOfAbsence = new ArrayList<>();
    }

    public Student(String name, String teacherName, int absenceCount, ArrayList<Date> datesOfAbsence) {
        super(name);
        this.teacherName = teacherName;
        this.absenceCount = absenceCount;
        this.datesOfAbsence = datesOfAbsence;
    }

    public Student() {
        datesOfAbsence = new ArrayList<>();
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public int getAbsenceCount() {
        return absenceCount;
    }

    public void addAbsence() {
        datesOfAbsence.add(new Date());
        absenceCount++;
    }

    public ArrayList<Date> getDatesOfAbsence() {
        return datesOfAbsence;
    }

}
