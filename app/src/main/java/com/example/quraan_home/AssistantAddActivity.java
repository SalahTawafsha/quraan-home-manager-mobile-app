package com.example.quraan_home;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import entities.Assistance;

public class AssistantAddActivity extends AppCompatActivity {
    private ListView assistants;
    private Button add;
    private final DatabaseReference database = FirebaseDatabase.getInstance().getReference();
    private final List<String> assistantsList = new ArrayList<>();
    private SharedPreferences sharedPref;
    private String selected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assistants_add);

        assistants = findViewById(R.id.list);
        add = findViewById(R.id.add);

        sharedPref = getSharedPreferences(
                getString(R.string.login)
                , Context.MODE_PRIVATE);

        database.child("assistants").orderByChild("teacherName").equalTo("")
                .get().addOnCompleteListener(task -> {
                    for (DataSnapshot ds : task.getResult().getChildren())
                        assistantsList.add(Objects.requireNonNull(ds.getValue(Assistance.class)).getName());

                    ArrayAdapter<String> adapter =
                            new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, assistantsList);
                    assistants.setAdapter(adapter);

                });

        assistants.setOnItemClickListener((adapterView, view, i, l) -> {
            add.setEnabled(true);
            selected = assistantsList.get(i);
            Log.e("selected", selected);
        });
    }

    public void addAssistants(View view) {
        database.child("assistants").child(selected).child("teacherName").setValue(sharedPref.getString("logInID", ""));
        assistantsList.remove(selected);
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, assistantsList);
        assistants.setAdapter(adapter);
        add.setEnabled(false);
        Toast.makeText(this, "تمت الإضافة !", Toast.LENGTH_SHORT).show();
    }
}