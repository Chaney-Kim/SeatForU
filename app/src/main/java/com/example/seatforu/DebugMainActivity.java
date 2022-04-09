package com.example.seatforu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class DebugMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_debugmain);

        Button goto_home = (Button)findViewById(R.id.button_debug_tohome);
        Button goto_editor = (Button)findViewById(R.id.button_debug_toeditor);

        goto_home.setOnClickListener(v -> {
            // 상황에 맞게 작성하세요
//            Intent intent = new Intent(DebugMainActivity.class, 홈화면액티비티.class);
//            startActivity(intent);
        });

        goto_editor.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), EditorActivity.class);
            startActivity(intent);
        });
    }
}