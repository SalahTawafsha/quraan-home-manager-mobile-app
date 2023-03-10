package com.example.quraan_home;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;

public class Calender extends AppCompatActivity {
    private DatePicker calendarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_callender);

        calendarView = findViewById(R.id.calendarView2);

    }

    public void selectDate(View view) {
        String day = calendarView.getDayOfMonth() + "";
        String month = calendarView.getMonth() + 1 + "";
        if (calendarView.getDayOfMonth() < 10)
            day = "0" + day;
        if (calendarView.getMonth() + 1 < 10)
            month = "0" + month;

        String date = day + "-" + month + "-" + calendarView.getYear();
        Intent intent = new Intent();
        intent.putExtra("date", date);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }
}