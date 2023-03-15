package com.example.quraan_home;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class StudentHomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_home);

        TextView studentName = findViewById(R.id.student_home);

        SharedPreferences sharedPref = getSharedPreferences(
                getString(R.string.login)
                , Context.MODE_PRIVATE);

        studentName.setText(sharedPref.getString("currStudent", ""));


    }

    public void absence(View view) {
        Intent intent = new Intent(this, StudentAbsenceActivity.class);
        startActivity(intent);
    }

    public void notes(View view) {
        Intent intent = new Intent(this, StudentNotesActivity.class);
        startActivity(intent);
    }

    public void recitation(View view) {
        Intent intent = new Intent(this, StudentRecitationActivity.class);
        startActivity(intent);
    }

    public void memorizing(View view) {
        Intent intent = new Intent(this, StudentMemorizationActivity.class);
        startActivity(intent);
    }

    public void finish(View view) {
        finish();
    }

    public void update(View view) {
        Intent intent = new Intent(this, StudentUpdateInfoActivity.class);
        startActivity(intent);
    }

    public void showInfo(View view) {
        Intent intent = new Intent(this, StudentReportActivity.class);
        startActivity(intent);
    }
}