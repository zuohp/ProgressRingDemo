package com.example.progress_ring;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.progress_ring.view.Progress_ring;

public class MainActivity extends AppCompatActivity {

    private Progress_ring progress_ring;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progress_ring = findViewById(R.id.progressRing);
    }
}
