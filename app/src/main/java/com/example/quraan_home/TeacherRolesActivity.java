package com.example.quraan_home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
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

import java.util.List;
import java.util.Objects;

import entities.Teacher;

public class TeacherRolesActivity extends AppCompatActivity {
    private EditText ruleName;
    private ListView list;
    private final DatabaseReference database = FirebaseDatabase.getInstance().getReference();
    private SharedPreferences sharedPref;
    private String selector;
    private Button delete;
    private Button add;
    private List<String> rules;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_roles);

        ruleName = findViewById(R.id.rule_name);
        list = findViewById(R.id.rules);
        delete = findViewById(R.id.deleteRule);
        add = findViewById(R.id.add_rule);

        sharedPref = getSharedPreferences(
                getString(R.string.login)
                , Context.MODE_PRIVATE);

        loadRules();

        list.setOnItemClickListener((adapterView, view, i, l) -> {
            selector = String.valueOf(adapterView.getAdapter().getItem(i));
            delete.setEnabled(true);
        });

    }

    private void loadRules() {
        database.child("teachers").child(sharedPref.getString("logInID", ""))
                .get().addOnCompleteListener(task -> {
                    if (task.getResult().getValue(Teacher.class) == null)
                        Log.e("test", "null");
                    rules = Objects.requireNonNull(task.getResult().getValue(Teacher.class)).getRules();

                    ArrayAdapter<String> adapter =
                            new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, rules);
                    list.setAdapter(adapter);
                    add.setEnabled(true);

                });
    }

    public void addRule(View view) {
        if (ruleName.getText().toString().isEmpty()) {
            Toast.makeText(this, "لا يمكن ان يكون اسم الحكم فارغ", Toast.LENGTH_SHORT).show();
            return;
        }

        database.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child("teachers").hasChild(ruleName.getText().toString())) {
                    Toast.makeText(TeacherRolesActivity.this, "هذا الحكم موجود بالفعل", Toast.LENGTH_SHORT).show();
                    ruleName.getText().clear();
                } else {
                    rules.add(ruleName.getText().toString());
                    database.child("teachers").child(sharedPref.getString("logInID", "")).child("rules").setValue(rules);
                    Toast.makeText(TeacherRolesActivity.this, "تمت الاضافة", Toast.LENGTH_SHORT).show();
                    setAdapter();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void setAdapter() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, rules);
        list.setAdapter(adapter);
    }

    public void delete(View view) {
        database.child("teachers").child(selector).removeValue();
        delete.setEnabled(false);
        loadRules();
    }
}