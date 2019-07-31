package com.example.wil;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class TaskGroup extends AppCompatActivity {

    ImageButton imageButton;
    Util util;
    ListView tasks;
    TextView taskGroup_btn_back_toolbar_title;
    List<String> data = new ArrayList<>();
    ArrayAdapter<String> itemsAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        tasks = findViewById(R.id.taskGroup_listView);
        taskGroup_btn_back_toolbar_title = findViewById(R.id.taskGroup_btn_back_toolbar_title);

        Intent intent = getIntent();
        final String title = intent.getStringExtra("Task Group");
        Log.d("From:hhh", "onCreate: "+title);
        taskGroup_btn_back_toolbar_title.setText(title);

        util = Util.getInstance();

        if(title.equals("Task Group 1")){
            data = util.getTask1_description();
            itemsAdapter =
                    new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, data);

            tasks.setAdapter(itemsAdapter);
            Log.d("util.getDescription", "onCreate: Task 1: "+util.getTask1_description());
        }

        tasks.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                new AlertDialog.Builder(TaskGroup.this)
                        .setPositiveButton("Delete Task", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                data.remove(position);
                                itemsAdapter.notifyDataSetChanged();
                                Intent intent1 = new Intent(TaskGroup.this, MainActivity.class);
                                startActivity(intent1);
                            }
                        })


                        .setNegativeButton("Modify Task",  new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(TaskGroup.this,Task.class);
                                intent.putExtra("desc",data.get(position));
                                intent.putExtra("date","");
                                intent.putExtra("stat","");
                                intent.putExtra("buttonName","Edit");
                                intent.putExtra("index",position);
                                startActivity(intent);
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
                return false;
            }
        });

        imageButton = findViewById(R.id.taskGroup_btn_back);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TaskGroup.this, MainActivity.class);
                startActivity(intent);
            }
        });
        Button btn = findViewById(R.id.taskGroup_addTask);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TaskGroup.this, Task.class);
                intent.putExtra("Task Group",title);
                intent.putExtra("buttonName","save");
                startActivity(intent);
            }
        });
    }
}
