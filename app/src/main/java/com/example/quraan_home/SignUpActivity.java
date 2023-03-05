package com.example.quraan_home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import entities.Assistance;
import entities.Teacher;


public class SignUpActivity extends AppCompatActivity {
    private RadioButton teacher;
    private EditText name;
    private EditText firstPass;
    private EditText secondPass;
    private final DatabaseReference database = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        name = findViewById(R.id.teacherName);
        firstPass = findViewById(R.id.firstPassword);
        secondPass = findViewById(R.id.secondPassword);
        teacher = findViewById(R.id.teacher);

    }

    public void add(View view) {
        if (name.getText().toString().isEmpty()) {
            Toast.makeText(this, "لا يمكن أن يكون اسم المستخدم فارغ", Toast.LENGTH_SHORT).show();
            return;
        }
        if (firstPass.getText().toString().isEmpty()) {
            Toast.makeText(this, "لا يمكن ان تكون كلمة السر  فارغة", Toast.LENGTH_SHORT).show();
            return;
        }

        if (firstPass.getText().toString().equals(secondPass.getText().toString())) {
            database.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.child("teachers").hasChild(name.getText().toString()) || snapshot.child("assistants").hasChild(name.getText().toString()))
                        Toast.makeText(SignUpActivity.this, "اسم المعلم تم تسجيله من قبل", Toast.LENGTH_SHORT).show();
                    else if (teacher.isChecked()) {
                        database.child("teachers").child(name.getText().toString()).setValue(new Teacher(name.getText().toString(), firstPass.getText().toString()));
                        Toast.makeText(SignUpActivity.this, "تمت الاضافة", Toast.LENGTH_SHORT).show();
                    } else {
                        database.child("assistants").child(name.getText().toString()).setValue(new Assistance(name.getText().toString(), firstPass.getText().toString()));
                        Toast.makeText(SignUpActivity.this, "تمت الاضافة", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        } else
            Toast.makeText(this, "كلمة السر غير متطابة", Toast.LENGTH_SHORT).show();
    }
}