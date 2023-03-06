package com.example.quraan_home;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import entities.Assistance;
import entities.Teacher;

public class TeacherLoginActivity extends Activity {
    private EditText name;
    private EditText password;
    private SharedPreferences.Editor editor;
    private final DatabaseReference database = FirebaseDatabase.getInstance().getReference();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_login);

        name = findViewById(R.id.loginTeacherName);
        password = findViewById(R.id.loginPassword);

        SharedPreferences sharedPref = getSharedPreferences(
                getString(R.string.login)
                , Context.MODE_PRIVATE);

        editor = sharedPref.edit();


        if (!sharedPref.getString("logInID", "").equals("")) {
            Intent intent = new Intent(this, TeacherHomeActivity.class);
            startActivity(intent);
        }
    }

    public void signUp(View view) {
        Intent intent = new Intent(this, TeacherSignUpActivity.class);
        intent.putExtra("type", "add");
        startActivity(intent);
    }

    public void logIn(View view) {
        if (name.getText().toString().isEmpty()) {
            Toast.makeText(this, "لا يمكن ان يكون اسم المعلم فارغ", Toast.LENGTH_SHORT).show();
            return;
        }
        database.child("teachers").child(name.getText().toString()).get().addOnCompleteListener(task -> {
            Teacher t = task.getResult().getValue(Teacher.class);
            if (t != null) {
                String password = t.getPassword();
                if (this.password.getText().toString().equals(password)) {
                    editor.putString("logInID", name.getText().toString());
                    editor.commit();
                    Intent intent = new Intent(this, TeacherHomeActivity.class);
                    intent.putExtra("isTeacher", true);
                    startActivity(intent);
                } else
                    Toast.makeText(this, "كلمة السر خاطئة", Toast.LENGTH_SHORT).show();
            } else {
                database.child("assistants").child(name.getText().toString())
                        .get().addOnCompleteListener(task1 -> {
                            Assistance a = task1.getResult().getValue(Assistance.class);
                            if (a != null) {
                                String password = a.getPassword();
                                if (this.password.getText().toString().equals(password)) {
                                    if (!a.getTeacherName().isEmpty()) {
                                        editor.putString("logInID", a.getTeacherName());
                                        editor.commit();
                                        Intent intent = new Intent(this, TeacherHomeActivity.class);
                                        intent.putExtra("isTeacher", false);
                                        startActivity(intent);
                                    } else
                                        Toast.makeText(this, "لا يمكنك تسجيل الدخول.\nلست مسجلاً لدى اي مدرس"
                                                , Toast.LENGTH_SHORT).show();
                                } else
                                    Toast.makeText(this, "كلمة السر خاطئة", Toast.LENGTH_SHORT).show();
                            } else
                                Toast.makeText(this, "اسم المعلم غير موجود", Toast.LENGTH_SHORT).show();
                        });
            }
        });
    }

}
