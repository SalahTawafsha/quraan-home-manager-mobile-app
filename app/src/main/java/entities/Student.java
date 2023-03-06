package entities;

import java.util.ArrayList;
import java.util.Date;

public class Student extends Person {
    private String teacherName;
    private final ArrayList<Date> datesOfAbsence;
    private final ArrayList<Integer> pagesMemorized;
    private String notes = "";

    public Student(String name, String teacherName) {
        super(name);
        this.teacherName = teacherName;
        datesOfAbsence = new ArrayList<>();
        pagesMemorized = new ArrayList<>();
    }

    public Student(String name, String teacherName, ArrayList<Date> datesOfAbsence, ArrayList<Integer> pagesMemorized, String notes) {
        super(name);
        this.teacherName = teacherName;
        this.datesOfAbsence = datesOfAbsence;
        this.pagesMemorized = pagesMemorized;
        this.notes = notes;
    }

    public Student() {
        datesOfAbsence = new ArrayList<>();
        pagesMemorized = new ArrayList<>();
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public void addAbsence() {
        datesOfAbsence.add(new Date());
    }

    public ArrayList<Date> getDatesOfAbsence() {
        return datesOfAbsence;
    }

    public ArrayList<Integer> getPagesMemorized() {
        return pagesMemorized;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
