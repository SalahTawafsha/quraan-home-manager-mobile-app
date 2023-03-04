package com.example.quraan_home;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_absence);

        list = findViewById(R.id.absence);
        absenceCount = findViewById(R.id.absenceCount);

        sharedPref = getSharedPreferences(getString(R.string.login), Context.MODE_PRIVATE);
        loadStudents();


    }

    @SuppressLint({"SimpleDateFormat", "SetTextI18n"})
    private void loadStudents() {
        Log.e("currStudent", sharedPref.getString("currStudent", ""));
        database.child("student").child(sharedPref.getString("currStudent", ""))
                .get().addOnCompleteListener(task -> {

                    ArrayList<Date> dates = Objects.requireNonNull(task.getResult().getValue(Student.class)).getDatesOfAbsence();
                    ArrayList<String> formattedDates = new ArrayList<>(dates.size());
                    for (int i = 0; i < dates.size(); i++) {
                        formattedDates.add(new SimpleDateFormat("dd-MM-yyyy").format(dates.get(i)));
                    }

                    absenceCount.setText(formattedDates.size() + "");
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, formattedDates);
                    list.setAdapter(adapter);
//            for (DataSnapshot ds : task.getResult().getChildren())
//                if (Objects.requireNonNull(ds.getValue(Student.class)).getDatesOfAbsence() != null) {
//                    break;
//                }
        });
    }
}