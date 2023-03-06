package com.example.quraan_home;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class TeacherHomeActivity extends AppCompatActivity {
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_home);

        SharedPreferences sharedPref = getSharedPreferences(
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
}