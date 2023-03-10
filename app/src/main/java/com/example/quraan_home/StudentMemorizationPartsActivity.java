package com.example.quraan_home;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import entities.Student;

public class StudentMemorizationPartsActivity extends AppCompatActivity {
    private LinearLayout surah;
    private final DatabaseReference database = FirebaseDatabase.getInstance().getReference();
    private SharedPreferences sharedPref;
    private Student student;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_memorization_parts);

        surah = findViewById(R.id.surah);

        sharedPref = getSharedPreferences(
                getString(R.string.login)
                , Context.MODE_PRIVATE);

        String[] lines;

        int x = getIntent().getIntExtra("part", 1) + 1;
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

        database.child("student").child(sharedPref.getString("currStudent", ""))
                .get().addOnCompleteListener(task -> {
                    student = task.getResult().getValue(Student.class);
                    for (String line : lines) {
                        CheckBox checkBox = new CheckBox(this);
                        checkBox.setId(View.AUTOFILL_TYPE_NONE);
                        checkBox.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
                        checkBox.setTextColor(Color.WHITE);
                        if (student.getPagesMemorized().contains(line))
                            checkBox.setChecked(true);
                        checkBox.setButtonTintList(ColorStateList.valueOf(Color.WHITE));
                        checkBox.setTextSize(20);
                        checkBox.setPadding(0, 25, 0, 25);
                        checkBox.setText(line);
                        checkBox.setLayoutParams(new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.WRAP_CONTENT
                                , LinearLayout.LayoutParams.WRAP_CONTENT));


                        EditText rate = new EditText(this);
                        rate.setInputType(InputType.TYPE_CLASS_NUMBER);
                        rate.setLayoutParams(new LinearLayout.LayoutParams(
                                100
                                , LinearLayout.LayoutParams.WRAP_CONTENT));


                        LinearLayout linearLayout = new LinearLayout(this);
                        linearLayout.addView(rate);
                        linearLayout.addView(checkBox);


                        surah.addView(linearLayout);
                    }
                });

    }

    public void save(View view) {
        ArrayList<String> pagesMemorized = new ArrayList<>();
        for (int i = 0; i < surah.getChildCount(); i++)
            if (((CheckBox) surah.getChildAt(i)).isChecked()) {
                pagesMemorized.add(((CheckBox) surah.getChildAt(i)).getText().toString());
            }
        database.child("student").child(sharedPref.getString("currStudent", ""))
                .child("pagesMemorized").setValue(pagesMemorized);

        Toast.makeText(this, "تم الحفظ", Toast.LENGTH_SHORT).show();
    }

    public void finish(View view) {
        finish();
    }
}