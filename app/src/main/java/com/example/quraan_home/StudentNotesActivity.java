package com.example.quraan_home;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import entities.Student;

public class StudentNotesActivity extends AppCompatActivity {
    private EditText notes;
    private final DatabaseReference database = FirebaseDatabase.getInstance().getReference();
    private SharedPreferences sharedPref;
    private Student student;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_notes);

        notes = findViewById(R.id.notes);

        sharedPref = getSharedPreferences(
                getString(R.string.login)
                , Context.MODE_PRIVATE);

        database.child("student").child(sharedPref.getString("currStudent", ""))
                .get().addOnCompleteListener(task -> {
                    student = task.getResult().getValue(Student.class);
                    assert student != null;
                    notes.setText(student.getNotes());
                });
    }

    public void save(View view) {
        database.child("student").child(sharedPref.getString("currStudent", ""))
                .child("notes").setValue(notes.getText().toString());
        Toast.makeText(this, "تم الحفظ !", Toast.LENGTH_SHORT).show();
    }

    public void finish(View view) {
        finish();
    }
}