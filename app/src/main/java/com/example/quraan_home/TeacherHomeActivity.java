package com.example.quraan_home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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

                                print(students, Objects.requireNonNull(teacher));
                            });
                });


    }

    @SuppressLint("SimpleDateFormat")
    private void print(ArrayList<Student> input, Teacher teacher) {
        PdfDocument document = new PdfDocument();

        PdfDocument.PageInfo page = new PdfDocument.
                PageInfo.Builder(595, 842, 1).create();

        PdfDocument.Page screen = document.startPage(page);

        Paint design1 = new Paint();
        design1.setTextAlign(Paint.Align.LEFT);

        Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(),
                R.drawable.icon);
        Bitmap bitmap1 = Bitmap.createScaledBitmap(bitmap, 100, 100, true);
        screen.getCanvas().drawBitmap(bitmap1, 0, 10, design1);

        design1.setTextAlign(Paint.Align.RIGHT);
        int x = 585, y = 25;

        screen.getCanvas().drawText("بسم الله الرحمن الرحيم", 333, y, design1);
        y += 20;

        screen.getCanvas().drawText("تقرير حلقة " + teacher.getName(), x, y, design1);
        y += 20;

        screen.getCanvas().drawText("التاريخ: " + new SimpleDateFormat("dd-MM-yyyy").format(new Date()), x, y, design1);
        y += 20;

        screen.getCanvas().drawText("عدد أحكام التجويد للمدرس " + teacher.getRules().size(), x, y, design1);
        y += 20;

        screen.getCanvas().drawLine(0, y, 595, y, design1);
        y += 20;

        screen.getCanvas().drawLine(460, y, 460, 842, design1);
        screen.getCanvas().drawLine(370, y, 370, 842, design1);
        screen.getCanvas().drawLine(260, y, 260, 842, design1);
        screen.getCanvas().drawLine(190, y, 190, 842, design1);


        Paint design = new Paint();
        design.setTextAlign(Paint.Align.RIGHT);
        design.setTextSize(10);


        screen.getCanvas().drawText("اسم الطالب", 585, y, design);
        screen.getCanvas().drawText("الصفحات المحفوظة", 450, y, design);
        screen.getCanvas().drawText("احكام التجويد المتقنة", 350, y, design);
        screen.getCanvas().drawText("عدد الغيابات", 247, y, design);
        screen.getCanvas().drawText("ملاحظات", 125, y, design);
        y += 20;

        if (input.size() < 30) {
            for (Student student : input) {
                screen.getCanvas().drawText(student.getName(), 585, y, design);
                screen.getCanvas().drawText(student.countOfPageMemorized() + "", 420, y, design);
                screen.getCanvas().drawText(student.getRecitation().size() + "", 315, y, design);
                screen.getCanvas().drawText(student.getDatesOfAbsence().size() + "", 226, y, design);
                screen.getCanvas().drawText(student.getNotes(), 170, y, design);

                y += 20;
            }

            document.finishPage(screen);
        } else {
            for (int i = 0; i < input.size(); i++) {
                Student student = input.get(i);

                screen.getCanvas().drawText(student.getName(), 585, y, design);
                screen.getCanvas().drawText(student.countOfPageMemorized() + "", 420, y, design);
                screen.getCanvas().drawText(student.getRecitation().size() + "", 315, y, design);
                screen.getCanvas().drawText(student.getDatesOfAbsence().size() + "", 226, y, design);
                screen.getCanvas().drawText(student.getNotes(), 170, y, design);

                if (y + 20 > 842) {
                    document.finishPage(screen);
                    page = new PdfDocument.
                            PageInfo.Builder(595, 842, i / 34).create();

                    screen = document.startPage(page);
                    screen.getCanvas().drawLine(460, 0, 460, 842, design1);
                    screen.getCanvas().drawLine(370, 0, 370, 842, design1);
                    screen.getCanvas().drawLine(260, 0, 260, 842, design1);
                    screen.getCanvas().drawLine(190, 0, 190, 842, design1);
                    y = 25;
                }

                y += 20;
            }
            document.finishPage(screen);

        }
        String FilePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                + File.separator + teacher.getName() + "_" + new SimpleDateFormat("dd-MM-yyyy").format(new Date()) + ".pdf";
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