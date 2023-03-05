package com.example.quraan_home;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

import entities.Student;

public class StudentAbsenceActivity extends AppCompatActivity {
    private ListView list;
    private TextView absenceCount;
    private final DatabaseReference database = FirebaseDatabase.getInstance().getReference();
    private SharedPreferences sharedPref;
    private ArrayList<Date> dates;
    private Date selector;
    private Button delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_absence);

        list = findViewById(R.id.absence);
        absenceCount = findViewById(R.id.absenceCount);
        delete = findViewById(R.id.delete);

        sharedPref = getSharedPreferences(getString(R.string.login), Context.MODE_PRIVATE);
        loadStudents();


    }

    @SuppressLint({"SimpleDateFormat", "SetTextI18n"})
    private void loadStudents() {
        Log.e("currStudent", sharedPref.getString("currStudent", ""));
        Log.e("student", sharedPref.getString("currStudent", ""));
        database.child("student").child(sharedPref.getString("currStudent", ""))
                .get().addOnCompleteListener(task -> {

                    dates = Objects.requireNonNull(task.getResult().getValue(Student.class)).getDatesOfAbsence();
                    ArrayList<String> formattedDates = new ArrayList<>(dates.size());
                    for (int i = 0; i < dates.size(); i++)
                        formattedDates.add(new SimpleDateFormat("dd-MM-yyyy").format(dates.get(i)));


                    absenceCount.setText("عدد الغيابات: " + formattedDates.size());
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, formattedDates);
                    list.setAdapter(adapter);

                });

        list.setOnItemClickListener((adapterView, view, i, l) -> {
            selector = dates.get(i);
            delete.setEnabled(true);
        });
    }

    @SuppressLint("SimpleDateFormat")
    public void deleteAbsence(View view) {
        dates.remove(selector);
        database.child("student").child(sharedPref.getString("currStudent", "")).child("datesOfAbsence").setValue(dates);
        ArrayList<String> formattedDates = new ArrayList<>(dates.size());
        for (int i = 0; i < dates.size(); i++)
            formattedDates.add(new SimpleDateFormat("dd-MM-yyyy").format(dates.get(i)));

        String count = "عدد الغيابات: " + formattedDates.size();
        absenceCount.setText(count);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, formattedDates);
        list.setAdapter(adapter);
        delete.setEnabled(false);
    }
}