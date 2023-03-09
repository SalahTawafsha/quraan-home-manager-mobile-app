package com.example.quraan_home;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

import entities.Student;
import entities.Teacher;

public class TeacherHomeActivity extends AppCompatActivity {
    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_home);

        sharedPref = getSharedPreferences(
                getString(R.string.login)
                , Context.MODE_PRIVATE);

        editor = sharedPref.edit();
        Button updateAssistants = findViewById(R.id.updateAssistants);

        if (!getIntent().getBooleanExtra("isTeacher", false)) {
            updateAssistants.setVisibility(View.GONE);
        }
    }

    @Override
    public void onBackPressed() {
        editor.putString("logInID", "");
        editor.commit();
        super.onBackPressed();
    }

    public void updateStudents(View view) {
        Intent intent = new Intent(this, StudentsUpdateActivity.class);
        startActivity(intent);
    }

    public void studentList(View view) {
        Intent intent = new Intent(this, StudentsListActivity.class);
        startActivity(intent);
    }

    public void audience(View view) {
        Intent intent = new Intent(this, TeacherAbsenceActivity.class);
        startActivity(intent);
    }

    public void updateAssistants(View view) {
        Intent intent = new Intent(this, AssistantsSelectActivity.class);
        startActivity(intent);
    }

    public void updateRules(View view) {
        Intent intent = new Intent(this, TeacherRolesActivity.class);
        startActivity(intent);
    }

    public void finish(View view) {
        finish();
        editor.putString("logInID", "");
        editor.commit();
    }

    public void saveReport(View view) {


        DatabaseReference database = FirebaseDatabase.getInstance().getReference();

        database.child("teachers").child(sharedPref.getString("logInID", ""))
                .get().addOnCompleteListener(task -> {
                    Teacher teacher = task.getResult().getValue(Teacher.class);

                    database.child("student").orderByChild("teacherName").equalTo(sharedPref.getString("logInID", ""))
                            .get().addOnCompleteListener(task1 -> {
                                ArrayList<Student> students = new ArrayList<>();
                                task1.getResult().getChildren().forEach(dataSnapshot -> {
                                    Student s = dataSnapshot.getValue(Student.class);
                                    students.add(s);
                                });

                                StringBuilder s = new StringBuilder();
                                for (int i = 0; i < students.size(); i++) {
                                    s.append(students.get(i).toString()).append("\n");
                                }

                                print(s.toString(), Objects.requireNonNull(teacher).getName());
                            });
                });


    }

    @SuppressLint("SimpleDateFormat")
    private void print(String input, String name) {
        PdfDocument document = new PdfDocument();

        PdfDocument.PageInfo page = new PdfDocument.
                PageInfo.Builder(595, 842, 100).create();

        PdfDocument.Page screen = document.startPage(page);

        Paint design = new Paint();

        int x = 10, y = 25;

        for (String line : input.split("\n")) {
            screen.getCanvas().drawText(line, x, y, design);
            y += design.descent() - design.ascent();
        }

        document.finishPage(screen);

        String FilePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                + File.separator + name + "_" + new SimpleDateFormat("dd-MM-yyyy").format(new Date()) + ".pdf";
        File output = new File(FilePath);
        try {
            document.writeTo(new FileOutputStream(output));
            Toast.makeText(this, "تم الحفظ", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "خطا: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        document.close();
    }
}