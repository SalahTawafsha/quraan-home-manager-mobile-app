package com.example.quraan_home;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class StudentHomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_home);
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
    }

    public void memorizing(View view) {
        Intent intent = new Intent(this, StudentMemorizationActivity.class);
        startActivity(intent);
    }
}