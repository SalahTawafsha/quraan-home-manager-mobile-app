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
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Objects;

import entities.Student;

public class MemorizationActivity extends AppCompatActivity {
    private LinearLayout surah;
    private final DatabaseReference database = FirebaseDatabase.getInstance().getReference();
    private SharedPreferences sharedPref;
    private Student student;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memorization);

        surah = findViewById(R.id.surah);

        sharedPref = getSharedPreferences(
                getString(R.string.login)
                , Context.MODE_PRIVATE);

        String s = getString(R.string.surah);

        String[] lines = s.split("[,]");

        database.child("student").child(sharedPref.getString("currStudent", ""))
                .get().addOnCompleteListener(task -> {
                    student = task.getResult().getValue(Student.class);
                    for (String line : lines) {
                        CheckBox checkBox = new CheckBox(this);
                        checkBox.setId(View.AUTOFILL_TYPE_NONE);
                        checkBox.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
                        checkBox.setTextColor(Color.WHITE);
                        if (student.getPagesMemorized().contains(Integer.valueOf(line.substring(0, line.indexOf(':')).trim())))
                            checkBox.setChecked(true);
                        checkBox.setButtonTintList(ColorStateList.valueOf(Color.WHITE));
                        checkBox.setTextSize(20);
                        checkBox.setPadding(0, 25, 0, 25);
                        checkBox.setText(line);
                        checkBox.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                        surah.addView(checkBox);
                    }
                });

    }

    public void save(View view) {
        ArrayList<Integer> pagesMemorized = new ArrayList<>();
        for (int i = 0; i < surah.getChildCount(); i++)
            if (((CheckBox) surah.getChildAt(i)).isChecked()) {
                String[] tokens = ((CheckBox) surah.getChildAt(i)).getText().toString().split("[:]");
                pagesMemorized.add(Integer.valueOf(tokens[0]));
            }
        database.child("student").child(sharedPref.getString("currStudent", ""))
                .child("pagesMemorized").setValue(pagesMemorized);

        Toast.makeText(this, "تم الحفظ", Toast.LENGTH_SHORT).show();
    }
}