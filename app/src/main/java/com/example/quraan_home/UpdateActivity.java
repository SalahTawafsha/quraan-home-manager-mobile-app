package com.example.quraan_home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
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

public class UpdateActivity extends AppCompatActivity {
    private EditText studentName;
    private ListView list;
    private final DatabaseReference database = FirebaseDatabase.getInstance().getReference();
    private SharedPreferences sharedPref;
    private String selector;
    private Button delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        studentName = findViewById(R.id.student_name);
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

    public void addStudent(View view) {
        if (studentName.getText().toString().isEmpty()) {
            Toast.makeText(this, "لا يمكن ان يكون اسم الطالب فارغ", Toast.LENGTH_SHORT).show();
            return;
        }

        database.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child("student").hasChild(studentName.getText().toString())) {
                    Toast.makeText(UpdateActivity.this, "هذا الطالب مسجل بالفعل مع المعلم: "
                            + Objects.requireNonNull(snapshot.child("student").child(studentName.getText().toString()).getValue(Student.class)).getTeacherName(), Toast.LENGTH_SHORT).show();
                    studentName.getText().clear();
                }
                else {
                    database.child("student").child(studentName.getText().toString()).setValue(new Student(studentName.getText().toString(), sharedPref.getString("logInID", "")));
                    Toast.makeText(UpdateActivity.this, "تمت الاضافة", Toast.LENGTH_SHORT).show();
                    loadStudents();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void delete(View view) {
        database.child("student").child(selector).removeValue();
        delete.setEnabled(false);
        loadStudents();
    }
}