package com.example.quraan_home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Date;
import java.util.Objects;

import entities.Student;

public class StudentAddActivity extends AppCompatActivity {
    private EditText studentName;
    private EditText yearOfBirth;
    private EditText phoneNumber;
    private final DatabaseReference database = FirebaseDatabase.getInstance().getReference();
    private SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_add);

        studentName = findViewById(R.id.student_name);
        yearOfBirth = findViewById(R.id.yearOfBirth);
        phoneNumber = findViewById(R.id.phoneNumber);

        sharedPref = getSharedPreferences(
                getString(R.string.login)
                , Context.MODE_PRIVATE);

    }

    public void addStudent(View view) {
        if (studentName.getText().toString().isEmpty()) {
            Toast.makeText(this, "لا يمكن ان يكون اسم الطالب فارغ", Toast.LENGTH_SHORT).show();
            return;
        }

        database.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child("student").hasChild(studentName.getText().toString())) {
                    Toast.makeText(StudentAddActivity.this, "هذا الطالب مسجل بالفعل مع المعلم: "
                            + Objects.requireNonNull(snapshot.child("student").child(studentName.getText().toString()).getValue(Student.class)).getTeacherName(), Toast.LENGTH_SHORT).show();
                } else {
                    database.child("student").child(studentName.getText().toString()).setValue(new Student(studentName.getText().toString(), sharedPref.getString("logInID", ""), new Date(),
                            Integer.parseInt(yearOfBirth.getText().toString()), phoneNumber.getText().toString()));
                    Toast.makeText(StudentAddActivity.this, "تمت الاضافة", Toast.LENGTH_SHORT).show();
                    studentName.getText().clear();
                    yearOfBirth.getText().clear();
                    phoneNumber.getText().clear();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void finish(View view) {
        finish();
    }
}