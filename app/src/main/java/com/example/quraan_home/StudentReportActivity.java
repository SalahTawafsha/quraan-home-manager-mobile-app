package com.example.quraan_home;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import entities.Memorization;
import entities.Student;

public class StudentReportActivity extends AppCompatActivity {
    private TextView name;
    private TextView absence;
    private TextView memorization;
    private TextView recitation;
    private final DatabaseReference database = FirebaseDatabase.getInstance().getReference();
    private Student s;
    private Button save;

    @SuppressLint("SimpleDateFormat")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_report);

        name = findViewById(R.id.name);
        absence = findViewById(R.id.absence);
        memorization = findViewById(R.id.memorization);
        recitation = findViewById(R.id.recitation);
        save = findViewById(R.id.savePDF);

        SharedPreferences sharedPref = getSharedPreferences(getString(R.string.login), Context.MODE_PRIVATE);

        database.child("student").child(sharedPref.getString("currStudent", ""))
                .get().addOnCompleteListener(task -> {
                    s = task.getResult().getValue(Student.class);
                    if (s != null) {
                        name.setText(s.getName());
                        StringBuilder text = new StringBuilder("عدد الغيابات:" + s.getDatesOfAbsence().size() + "\n");
                        for (int i = 0; i < s.getDatesOfAbsence().size(); i++)
                            text.append(new SimpleDateFormat("dd-MM-yyyy").format(s.getDatesOfAbsence().get(i))).append("\n");

                        absence.setText(text);


                        text = new StringBuilder("عدد الصفحات المحفوظة:" + s.getPagesMemorized().size() + "\n");
                        for (int i = 0; i < s.getPagesMemorized().size(); i++)
                            text.append(s.getPagesMemorized().get(i)).append("\n");
                        memorization.setText(text);


                        text = new StringBuilder("عدد احكام التجويد التي تم تطبيقها:" + s.getRecitation().size() + "\n");
                        for (int i = 0; i < s.getRecitation().size(); i++)
                            text.append(s.getRecitation().get(i)).append("\n");

                        recitation.setText(text);

                        save.setEnabled(true);
                    }
                });
    }

    public void savePDF(View view) {
        print();
    }

    @SuppressLint("SimpleDateFormat")
    private void print() {
        PdfDocument document = new PdfDocument();

        PdfDocument.PageInfo page = new PdfDocument.
                PageInfo.Builder(595, 842, 1).create();

        PdfDocument.Page screen = document.startPage(page);

        Paint design1 = new Paint();
        design1.setTextAlign(Paint.Align.LEFT);

        Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(),
                R.drawable.icon);
        Bitmap bitmap1 = Bitmap.createScaledBitmap(bitmap, 100, 100, true);
        screen.getCanvas().drawBitmap(bitmap1, 0, 20, design1);

        design1.setTextAlign(Paint.Align.RIGHT);
        int x = 585, y = 25;

        screen.getCanvas().drawText("بسم الله الرحمن الرحيم", 333, y, design1);
        y += 20;

        screen.getCanvas().drawText("تقرير الطالب: " + s.getName(), x, y, design1);
        y += 20;

        screen.getCanvas().drawText("التاريخ: " + new SimpleDateFormat("dd-MM-yyyy").format(new Date()), x, y, design1);
        y += 20;

        screen.getCanvas().drawText("عدد الغيابات: " + s.getDatesOfAbsence().size(), x, y, design1);
        y += 20;

        screen.getCanvas().drawText("عدد الصفحات المحفوظة: " + s.getPagesMemorized().size(), x, y, design1);
        y += 20;

        screen.getCanvas().drawText("عدد احكام التجويد التي تم تطبيقها: " + s.getRecitation().size(), x, y, design1);
        y += 20;

        screen.getCanvas().drawLine(0, y, 595, y, design1);
        y += 20;

        screen.getCanvas().drawLine(420, y, 420, 842, design1);
        screen.getCanvas().drawLine(300, y, 300, 842, design1);


        Paint design = new Paint();
        design.setTextAlign(Paint.Align.RIGHT);
        design.setTextSize(10);


        screen.getCanvas().drawText("تواريخ الغيابات", 540, y, design);
        screen.getCanvas().drawText("احكام التجويد المتقنة", 400, y, design);
        screen.getCanvas().drawText("الصفحات المحفوظة", 250, y, design);
        y += 20;

        int y1 = y;
        if (s.getDatesOfAbsence().size() < 30) {
            for (Date date : s.getDatesOfAbsence()) {
                screen.getCanvas().drawText(new SimpleDateFormat("dd-MM-yyyy").format(date), 540, y, design);
                y += 20;
            }

        } else {
            for (int i = 0; i < s.getDatesOfAbsence().size(); i++) {
                Date date = s.getDatesOfAbsence().get(i);

                screen.getCanvas().drawText(new SimpleDateFormat("dd-MM-yyyy").format(date), 540, y, design);

                if (y + 20 > 842) {
                    document.finishPage(screen);
                    page = new PdfDocument.
                            PageInfo.Builder(595, 842, i / 34).create();

                    screen = document.startPage(page);
                    screen.getCanvas().drawLine(420, 0, 420, 842, design1);
                    screen.getCanvas().drawLine(300, 0, 300, 842, design1);
                    y = 25;
                }

                y += 20;
            }

        }

        y = y1;

        if (s.getRecitation().size() < 30) {
            for (String recitation : s.getRecitation()) {
                screen.getCanvas().drawText(recitation, 360 + recitation.length() * 2.3F, y, design);
                y += 20;
            }

        } else {
            for (int i = 0; i < s.getRecitation().size(); i++) {
                String recitation = s.getRecitation().get(i);

                screen.getCanvas().drawText(recitation, 360 + recitation.length() * 2.3F, y, design);

                if (y + 20 > 842) {
                    document.finishPage(screen);
                    page = new PdfDocument.
                            PageInfo.Builder(595, 842, i / 34).create();

                    screen = document.startPage(page);
                    screen.getCanvas().drawLine(420, 0, 420, 842, design1);
                    screen.getCanvas().drawLine(300, 0, 300, 842, design1);
                    y = 25;
                }

                y += 20;
            }

        }

        y = y1;

        if (s.getPagesMemorized().size() < 30) {
            for (Memorization memorization : s.getPagesMemorized()) {
                screen.getCanvas().drawText(memorization.toString(), 250, y, design);
                y += 20;
            }

            document.finishPage(screen);
        } else {
            for (int i = 0; i < s.getPagesMemorized().size(); i++) {
                Memorization memorization = s.getPagesMemorized().get(i);

                screen.getCanvas().drawText(memorization.toString(), 250, y, design);

                if (y + 20 > 842) {
                    document.finishPage(screen);
                    page = new PdfDocument.
                            PageInfo.Builder(595, 842, i / 34).create();

                    screen = document.startPage(page);
                    screen.getCanvas().drawLine(420, 0, 420, 842, design1);
                    screen.getCanvas().drawLine(300, 0, 300, 842, design1);
                    y = 25;
                }

                y += 20;
            }
            document.finishPage(screen);

        }

        String FilePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                + File.separator + s.getName() + "_" + new SimpleDateFormat("dd-MM-yyyy").format(new Date()) + ".pdf";
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

    public void finish(View view) {
        finish();
    }
}