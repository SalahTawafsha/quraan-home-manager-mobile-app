package com.example.quraan_home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import entities.Student;

public class StudentsUpdateActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_students_update);

    }


    public void delete(View view) {
        Intent intent = new Intent(this, StudentDeleteActivity.class);
        startActivity(intent);
    }

    public void finish(View view) {
        finish();
    }

    public void add(View view) {
        Intent intent = new Intent(this, StudentAddActivity.class);
        startActivity(intent);
    }

    public void update(View view) {
        Intent intent = new Intent(this, StudentUpdateInfoActivity.class);
        startActivity(intent);
    }
}