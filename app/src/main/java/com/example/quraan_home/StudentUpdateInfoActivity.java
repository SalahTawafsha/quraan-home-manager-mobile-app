package com.example.quraan_home;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Objects;

import entities.Student;

public class StudentUpdateInfoActivity extends AppCompatActivity {
    private EditText yearOfBirth;
    private EditText phoneNumber;
    private TextView dateOfRegister;
    private final DatabaseReference database = FirebaseDatabase.getInstance().getReference();
    private SharedPreferences sharedPref;


    @SuppressLint({"SetTextI18n", "SimpleDateFormat"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_update_info);


        EditText studentName = findViewById(R.id.student_name);
        yearOfBirth = findViewById(R.id.yearOfBirth);
        phoneNumber = findViewById(R.id.phoneNumber);
        dateOfRegister = findViewById(R.id.date_of_register);

        sharedPref = getSharedPreferences(
                getString(R.string.login)
                , Context.MODE_PRIVATE);

        studentName.setText(sharedPref.getString("currStudent", ""));
        studentName.setEnabled(false);
        database.child("student").child(sharedPref.getString("currStudent", ""))
                .get().addOnCompleteListener(task -> {
                    Student s = task.getResult().getValue(Student.class);
                    phoneNumber.setText(Objects.requireNonNull(s).getPhoneNumber());
                    if (s.getYearOfBirth() != 0)
                        yearOfBirth.setText(s.getYearOfBirth() + "");
                    if (s.getDateOfRegister() != null)
                        dateOfRegister.setText(new SimpleDateFormat("dd-MM-yyyy").format(s.getDateOfRegister()));
                });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                dateOfRegister.setText(data.getStringExtra("date"));
            }
        }
    }

    @SuppressLint("SimpleDateFormat")
    public void save(View view) {
        if (!phoneNumber.getText().toString().isEmpty())
            database.child("student").child(sharedPref.getString("currStudent", "")).child("phoneNumber")
                    .setValue(phoneNumber.getText().toString());

        if (!yearOfBirth.getText().toString().isEmpty())
            database.child("student").child(sharedPref.getString("currStudent", "")).child("yearOfBirth")
                    .setValue(Integer.parseInt(yearOfBirth.getText().toString()));
        if (!dateOfRegister.getText().toString().isEmpty())
            try {
                database.child("student").child(sharedPref.getString("currStudent", "")).child("dateOfRegister")
                        .setValue(Objects.requireNonNull(new SimpleDateFormat("dd-MM-yyyy").parse(this.dateOfRegister.getText().toString())));
            } catch (ParseException ignored) {
            }

        Toast.makeText(this, "تم الحفظ !", Toast.LENGTH_SHORT).show();
    }

    public void finish(View view) {
        finish();
    }

    public void openCal(View view) {
        Intent intent = new Intent(this, Calender.class);
        startActivityForResult(intent, 1);
    }
}