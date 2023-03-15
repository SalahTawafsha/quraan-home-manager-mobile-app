package com.example.quraan_home;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Objects;

import entities.Student;
import entities.Teacher;

public class StudentRecitationActivity extends AppCompatActivity {
    private LinearLayout surah;
    private final DatabaseReference database = FirebaseDatabase.getInstance().getReference();
    private SharedPreferences sharedPref;
    private Student student;
    private Button save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_recitation);

        surah = findViewById(R.id.tajweed);
        save = findViewById(R.id.saveRecitation);

        sharedPref = getSharedPreferences(
                getString(R.string.login)
                , Context.MODE_PRIVATE);

        database.child("student").child(sharedPref.getString("currStudent", ""))
                .get().addOnCompleteListener(task -> student = task.getResult().getValue(Student.class));

        database.child("teachers").child(sharedPref.getString("logInID", ""))
                .get().addOnCompleteListener(task -> {
                    ArrayList<String> rules = Objects.requireNonNull(task.getResult().getValue(Teacher.class)).getRules();
                    Log.e("test", rules.toString());
                    if (rules.size() > 0)
                        for (String line : rules) {
                            CheckBox checkBox = new CheckBox(this);
                            checkBox.setId(View.AUTOFILL_TYPE_NONE);
                            checkBox.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
                            checkBox.setTextColor(Color.WHITE);
                            if (student.getRecitation().contains(line))
                                checkBox.setChecked(true);
                            checkBox.setButtonTintList(ColorStateList.valueOf(Color.WHITE));
                            checkBox.setTextSize(20);
                            checkBox.setPadding(0, 25, 0, 25);
                            checkBox.setText(line);
                            checkBox.setLayoutParams(new LinearLayout.LayoutParams(
                                    LinearLayout.LayoutParams.WRAP_CONTENT
                                    , LinearLayout.LayoutParams.WRAP_CONTENT));
                            surah.addView(checkBox);
                        }
                    else {
                        TextView textView = new TextView(this);
                        textView.setId(View.AUTOFILL_TYPE_NONE);
                        textView.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
                        textView.setTextColor(Color.WHITE);
                        textView.setTextSize(20);
                        textView.setPadding(0, 25, 0, 25);
                        textView.setText("لم تقم باضافة اي حكم للتجويد،\nيجب ان تقوم باضفاتها من الصفحة الرئيسية");
                        textView.setLayoutParams(new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.WRAP_CONTENT
                                , LinearLayout.LayoutParams.WRAP_CONTENT));
                        surah.addView(textView);
                        save.setEnabled(false);
                    }
                });

    }

    public void save(View view) {
        ArrayList<String> recitation = new ArrayList<>();
        for (int i = 0; i < surah.getChildCount(); i++)
            if (((CheckBox) surah.getChildAt(i)).isChecked()) {
                recitation.add(((CheckBox) surah.getChildAt(i)).getText().toString());
            } else
                recitation.remove(((CheckBox) surah.getChildAt(i)).getText().toString());
        database.child("student").child(sharedPref.getString("currStudent", ""))
                .child("recitation").setValue(recitation);

        Toast.makeText(this, "تم الحفظ", Toast.LENGTH_SHORT).show();
    }

    public void finish(View view) {
        finish();
    }
}