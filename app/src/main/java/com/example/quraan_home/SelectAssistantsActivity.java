package com.example.quraan_home;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class SelectAssistantsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_assistants);
    }

    public void addAssistants(View view) {
        Intent intent = new Intent(this, AddAssistantsActivity.class);
        startActivity(intent);
    }

    public void deleteAssistants(View view) {
        Intent intent = new Intent(this, DeleteAssistantsActivity.class);
        startActivity(intent);
    }
}