package com.example.quraan_home;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import entities.Student;

public class TeacherAbsenceActivity extends AppCompatActivity {
    private LinearLayout students;
    private final DatabaseReference database = FirebaseDatabase.getInstance().getReference();
    private SharedPreferences sharedPref;
    List<Student> studentList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_absence);

        students = findViewById(R.id.students_audience);

        sharedPref = getSharedPreferences(
                getString(R.string.login)
                , Context.MODE_PRIVATE);

        loadStudents();

    }

    private void loadStudents() {
        database.child("student").orderByChild("teacherName").equalTo(sharedPref.getString("logInID", ""))
                .get().addOnCompleteListener(task -> {
                    for (DataSnapshot ds : task.getResult().getChildren()) {
                        CheckBox checkBox = new CheckBox(this);
                        checkBox.setId(View.AUTOFILL_TYPE_NONE);
                        checkBox.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
                        checkBox.setButtonTintList(ColorStateList.valueOf(Color.WHITE));
                        checkBox.setTextColor(Color.WHITE);
                        checkBox.setTextSize(20);
                        checkBox.setPadding(0, 25, 0, 25);
                        Student s = Objects.requireNonNull(ds.getValue(Student.class));
                        studentList.add(s);
                        String text = "(" + s.getDatesOfAbsence().size() + ") " + s.getName();
                        checkBox.setText(text);
                        checkBox.setChecked(true);
                        checkBox.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                        students.addView(checkBox);
                    }
                });
    }

    public void save(View view) {
        for (int i = 0; i < students.getChildCount(); i++)
            if (!((CheckBox) students.getChildAt(i)).isChecked()) {
                String[] tokens = ((CheckBox) students.getChildAt(i)).getText().toString().split("[()]");
                studentList.get(i).addAbsence();
                database.child("student").child(tokens[2].trim()).setValue(studentList.get(i));
            }
        finish();
    }

    public void finish(View view) {
        finish();
    }
}