package com.example.quraan_home;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class AssistantsSelectActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assistants_select);
    }

    public void addAssistants(View view) {
        Intent intent = new Intent(this, AssistantAddActivity.class);
        startActivity(intent);
    }

    public void deleteAssistants(View view) {
        Intent intent = new Intent(this, AssistantDeleteActivity.class);
        startActivity(intent);
    }
}