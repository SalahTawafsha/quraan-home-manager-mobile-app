package com.example.quraan_home;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Objects;

import entities.Memorization;
import entities.Student;

public class StudentMemorizationActivity extends AppCompatActivity {
    private final DatabaseReference database = FirebaseDatabase.getInstance().getReference();
    private SharedPreferences sharedPref;
    private LinearLayout linearLayout;
    private int i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_memorization);

        linearLayout = findViewById(R.id.parts);
        sharedPref = getSharedPreferences(
                getString(R.string.login)
                , Context.MODE_PRIVATE);

    }

    @Override
    protected void onResume() {
        super.onResume();
        String[] parts = getResources().getStringArray(R.array.parts);
        linearLayout.removeAllViews();

        database.child("student").child(sharedPref.getString("currStudent", ""))
                .get().addOnCompleteListener(task -> {
                    Student student = task.getResult().getValue(Student.class);

                    for (i = 0; i < parts.length; i++) {
                        CheckBox checkBox = new CheckBox(this);
                        checkBox.setId(View.AUTOFILL_TYPE_NONE);
                        checkBox.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
                        checkBox.setTextColor(Color.BLACK);
                        checkBox.setButtonTintList(ColorStateList.valueOf(Color.BLACK));
                        checkBox.setTextSize(20);
                        checkBox.setTag(i);
                        checkBox.setPadding(0, 25, 0, 25);
                        checkBox.setText(parts[i]);
                        checkBox.setOnClickListener(view -> {
                            Intent intent = new Intent(StudentMemorizationActivity.this, StudentMemorizationPartsActivity.class);
                            intent.putExtra("part",
                                    Integer.parseInt(String.valueOf(view.getTag())));
                            startActivity(intent);
                        });
                        checkBox.setChecked(getChecked(Objects.requireNonNull(student).getPagesMemorized(), i + 1));
                        checkBox.setLayoutParams(new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.WRAP_CONTENT
                                , LinearLayout.LayoutParams.WRAP_CONTENT));
                        linearLayout.addView(checkBox);
                    }
                });
    }

    private boolean getChecked(ArrayList<Memorization> pagesMemorized, int x) {
        String[] lines;

        switch (x) {
            case 1:
                lines = getResources().getStringArray(R.array.part1);
                break;
            case 2:
                lines = getResources().getStringArray(R.array.part2);
                break;
            case 3:
                lines = getResources().getStringArray(R.array.part3);
                break;
            case 4:
                lines = getResources().getStringArray(R.array.part4);
                break;
            case 5:
                lines = getResources().getStringArray(R.array.part5);
                break;
            case 6:
                lines = getResources().getStringArray(R.array.part6);
                break;
            case 7:
                lines = getResources().getStringArray(R.array.part7);
                break;
            case 8:
                lines = getResources().getStringArray(R.array.part8);
                break;
            case 9:
                lines = getResources().getStringArray(R.array.part9);
                break;
            case 10:
                lines = getResources().getStringArray(R.array.part10);
                break;
            case 11:
                lines = getResources().getStringArray(R.array.part11);
                break;
            case 12:
                lines = getResources().getStringArray(R.array.part12);
                break;
            case 13:
                lines = getResources().getStringArray(R.array.part13);
                break;
            case 14:
                lines = getResources().getStringArray(R.array.part14);
                break;
            case 15:
                lines = getResources().getStringArray(R.array.part15);
                break;
            case 16:
                lines = getResources().getStringArray(R.array.part16);
                break;
            case 17:
                lines = getResources().getStringArray(R.array.part17);
                break;
            case 18:
                lines = getResources().getStringArray(R.array.part18);
                break;
            case 19:
                lines = getResources().getStringArray(R.array.part19);
                break;
            case 20:
                lines = getResources().getStringArray(R.array.part20);
                break;
            case 21:
                lines = getResources().getStringArray(R.array.part21);
                break;
            case 22:
                lines = getResources().getStringArray(R.array.part22);
                break;
            case 23:
                lines = getResources().getStringArray(R.array.part23);
                break;
            case 24:
                lines = getResources().getStringArray(R.array.part24);
                break;
            case 25:
                lines = getResources().getStringArray(R.array.part25);
                break;
            case 26:
                lines = getResources().getStringArray(R.array.part26);
                break;
            case 27:
                lines = getResources().getStringArray(R.array.part27);
                break;
            case 28:
                lines = getResources().getStringArray(R.array.part28);
                break;
            case 29:
                lines = getResources().getStringArray(R.array.part29);
                break;
            default:
                lines = getResources().getStringArray(R.array.part30);
                break;


        }

        for (String line : lines) {
            if (!pagesMemorized.contains(new Memorization(line, 0)))
                return false;
        }

        return true;

    }

    public void finish(View view) {
        finish();
    }
}