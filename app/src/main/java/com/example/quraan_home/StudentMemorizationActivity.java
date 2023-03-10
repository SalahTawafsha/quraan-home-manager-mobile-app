package com.example.quraan_home;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import entities.Student;

public class StudentMemorizationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_memorization);

        ListView listView = findViewById(R.id.parts);

        listView.setOnItemClickListener((adapterView, view, i, l) -> {
            Intent intent = new Intent(this, StudentMemorizationPartsActivity.class);
            intent.putExtra("part", i);
            startActivity(intent);
        });
    }

    public void finish(View view) {
        finish();
    }
}