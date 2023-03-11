package entities;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Date;

public class Student extends Person {
    private String teacherName;
    private final ArrayList<Date> datesOfAbsence;
    private final ArrayList<Memorization> pagesMemorized;
    private final ArrayList<String> recitation;
    private Date dateOfRegister;
    private int yearOfBirth;
    private String phoneNumber;
    private String notes = "";

    public Student(String name, String teacherName, Date dateOfRegister, int yearOfBirth, String phoneNumber) {
        super(name);
        this.teacherName = teacherName;
        this.dateOfRegister = dateOfRegister;
        this.yearOfBirth = yearOfBirth;
        this.phoneNumber = phoneNumber;
        datesOfAbsence = new ArrayList<>();
        pagesMemorized = new ArrayList<>();
        recitation = new ArrayList<>();

    }

    public Student(String name, String teacherName, ArrayList<Date> datesOfAbsence, ArrayList<Memorization> pagesMemorized, String notes) {
        super(name);
        this.teacherName = teacherName;
        this.datesOfAbsence = datesOfAbsence;
        this.pagesMemorized = pagesMemorized;
        recitation = new ArrayList<>();
        this.notes = notes;
    }

    public Student() {
        datesOfAbsence = new ArrayList<>();
        pagesMemorized = new ArrayList<>();
        recitation = new ArrayList<>();
    }

    public Student(String name, String teacherName) {
        super(name);
        this.teacherName = teacherName;
        dateOfRegister = new Date();
        datesOfAbsence = new ArrayList<>();
        pagesMemorized = new ArrayList<>();
        recitation = new ArrayList<>();
    }

    public String getTeacherName() {
        return teacherName;
    }

    public Date getDateOfRegister() {
        return dateOfRegister;
    }

    public int getYearOfBirth() {
        return yearOfBirth;
    }

    public String getPhoneNumber() {
        return phoneNumber;
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

    public ArrayList<Memorization> getPagesMemorized() {
        return pagesMemorized;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public ArrayList<String> getRecitation() {
        return recitation;
    }

    @SuppressLint("DefaultLocale")
    @NonNull
    @Override
    public String toString() {
        return String.format("%-30s|%-30d|%-30d|%-30d|%-40s", getName(), datesOfAbsence.size(), recitation.size(), pagesMemorized.size(), notes);

    }
}
