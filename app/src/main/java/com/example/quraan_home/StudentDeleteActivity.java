package com.example.quraan_home;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import entities.Student;

public class StudentDeleteActivity extends AppCompatActivity {
    private ListView list;
    private final DatabaseReference database = FirebaseDatabase.getInstance().getReference();
    private SharedPreferences sharedPref;
    private String selector;
    private Button delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_delete);

        list = findViewById(R.id.students);
        delete = findViewById(R.id.deleteStudent);

        sharedPref = getSharedPreferences(
                getString(R.string.login)
                , Context.MODE_PRIVATE);

        loadStudents();

        list.setOnItemClickListener((adapterView, view, i, l) -> {
            selector = String.valueOf(adapterView.getAdapter().getItem(i));
            delete.setEnabled(true);
        });

    }

    private void loadStudents() {
        database.child("student").orderByChild("teacherName").equalTo(sharedPref.getString("logInID", ""))
                .get().addOnCompleteListener(task -> {
                    List<String> students = new ArrayList<>();
                    for (DataSnapshot ds : task.getResult().getChildren())
                        students.add(Objects.requireNonNull(ds.getValue(Student.class)).getName());

                    ArrayAdapter<String> adapter =
                            new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, students);
                    list.setAdapter(adapter);

                });
    }

    public void delete(View view) {
        database.child("student").child(selector).removeValue();
        delete.setEnabled(false);
        loadStudents();
    }

    public void finish(View view) {
        finish();
    }
}