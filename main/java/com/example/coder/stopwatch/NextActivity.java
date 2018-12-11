package com.example.coder.stopwatch;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import android.content.Intent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NextActivity extends AppCompatActivity{

    TextView textView;
    Button start, pause, reset, lap;
    ListView listView;
    long MillisecondTime, StartTime, TimeBuff, UpdateTime = 0L ;
    Handler handler;
    int Seconds, Minutes, MilliSeconds ;
    String[] ListElements = new String[] {  };
    List<String> ListElementsArrayList ;
    ArrayAdapter<String> adapter ;

    Runnable runnable = new Runnable() {

        public void run() {
            MillisecondTime = SystemClock.uptimeMillis() - StartTime;
            UpdateTime = TimeBuff + MillisecondTime;
            Seconds = (int) (UpdateTime / 1000);
            Minutes = Seconds / 60;
            Seconds = Seconds % 60;
            MilliSeconds = (int) (UpdateTime % 1000);
            textView.setText("" + Minutes + ":"
                    + String.format("%02d", Seconds) + ":"
                    + String.format("%03d", MilliSeconds));
            handler.postDelayed(this, 0);
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next);

        textView = (TextView) findViewById(R.id.textView);
        start = (Button) findViewById(R.id.start_button);
        pause = (Button) findViewById(R.id.pause_button);
        reset = (Button) findViewById(R.id.reset_button);
        lap = (Button) findViewById(R.id.save_lap_button);
        listView = (ListView) findViewById(R.id.listview);

        handler = new Handler() ;

        ListElementsArrayList = new ArrayList<String>(Arrays.asList(ListElements));

        adapter = new ArrayAdapter<String>(NextActivity.this,
                android.R.layout.simple_list_item_multiple_choice,
                ListElementsArrayList);

        listView.setAdapter(adapter);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StartTime = SystemClock.uptimeMillis();
                handler.postDelayed(runnable ,0);

                reset.setEnabled(false);
            }
        });

        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimeBuff += MillisecondTime;
                handler.removeCallbacks(runnable);
                reset.setEnabled(true);
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                MillisecondTime = 0L ;
                StartTime = 0L ;
                TimeBuff = 0L ;
                UpdateTime = 0L ;
                Seconds = 0 ;
                Minutes = 0 ;
                MilliSeconds = 0 ;

                textView.setText("00:00:00");

                ListElementsArrayList.clear();

                adapter.notifyDataSetChanged();
            }
        });

        lap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ListElementsArrayList.add(textView.getText().toString());

                adapter.notifyDataSetChanged();

            }
        });

        Button deleteButton = (Button)findViewById(R.id.del_button) ;
        deleteButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                SparseBooleanArray chekedItems = listView.getCheckedItemPositions();
                int count, checked ;
                count = adapter.getCount() ;

                for(int i=count-1; i>=0; i--){
                    if(chekedItems.get(i)){
                        ListElementsArrayList.remove(i);
                    }
                }

                listView.clearChoices();

                adapter.notifyDataSetChanged();
            }

        }) ;

        Button selectAllButton = (Button)findViewById(R.id.selectall_button) ;
        selectAllButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                int count = 0 ;
                count = adapter.getCount() ;

                for (int i=0; i<count; i++) {
                    listView.setItemChecked(i, true);

                }
            }
        }) ;

        Button saveallButton = (Button)findViewById(R.id.saveall_button);
        saveallButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(NextActivity.this);
                builder.setTitle("SAVE ALL")
                        .setMessage("저장할 폴더 이름을 입력하세요");
                final EditText editText = new EditText(NextActivity.this);
                builder.setView(editText);

                builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String foldername = editText.getText().toString();
                        Intent intent = new Intent(NextActivity.this, FolderActivity.class);
                        intent.putExtra("data", foldername);
                        startActivity(intent);

                    }
                });
                builder.setNegativeButton("no", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.show();

            }

        }) ;

        }

}
