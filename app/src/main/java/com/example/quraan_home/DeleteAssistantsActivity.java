package com.example.quraan_home;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
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

public class DeleteAssistantsActivity extends AppCompatActivity {
    private ListView assistants;
    private Button delete;
    private final DatabaseReference database = FirebaseDatabase.getInstance().getReference();
    private final List<String> assistantsList = new ArrayList<>();
    private String selected;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_assistants);

        assistants = findViewById(R.id.list);
        delete = findViewById(R.id.delete);

        SharedPreferences sharedPref = getSharedPreferences(
                getString(R.string.login)
                , Context.MODE_PRIVATE);

        database.child("assistants").orderByChild("teacherName").equalTo(sharedPref.getString("logInID", ""))
                .get().addOnCompleteListener(task -> {
                    for (DataSnapshot ds : task.getResult().getChildren())
                        assistantsList.add(Objects.requireNonNull(ds.getValue(Assistance.class)).getName());

                    ArrayAdapter<String> adapter =
                            new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, assistantsList);
                    assistants.setAdapter(adapter);

                });

        assistants.setOnItemClickListener((adapterView, view, i, l) -> {
            delete.setEnabled(true);
            selected = assistantsList.get(i);
        });

    }

    public void deleteAssistants(View view) {
        database.child("assistants").child(selected).child("teacherName").setValue("");
        assistantsList.remove(selected);
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, assistantsList);
        assistants.setAdapter(adapter);
        delete.setEnabled(false);
        Toast.makeText(this, "تم الحذف !", Toast.LENGTH_SHORT).show();
    }
}