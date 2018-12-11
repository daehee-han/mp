package com.example.coder.stopwatch;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;


public class FolderActivity extends AppCompatActivity {
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_folder);
        String data = getIntent().getStringExtra("data");
        textView  = (TextView) findViewById(R.id.data);
        textView.setText(data);

    }


}
