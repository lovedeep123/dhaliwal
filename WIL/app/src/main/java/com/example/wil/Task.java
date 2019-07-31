package com.example.wil;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

public class Task extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    ImageButton imageButton;
    Spinner spinner;
    CalendarView calendarView;
    Button task_save;
    Util util;
    EditText editText;
    String date = "";
    String status;
    TextView task_btn_back_toolbar_title;
    int update_index = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task2);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        util = Util.getInstance();
        imageButton = findViewById(R.id.task_btn_back);
        spinner = findViewById(R.id.m_spinner);
        calendarView = findViewById(R.id.calendarView);
        task_save = findViewById(R.id.task_save);
        editText = findViewById(R.id.editText3);
        task_btn_back_toolbar_title = findViewById(R.id.task_btn_back_toolbar_title);



        Intent intent = getIntent();
        final String title = intent.getStringExtra("Task Group");
        String desc = intent.getStringExtra("desc");
        String date1 = intent.getStringExtra("date");
        String stat = intent.getStringExtra("stat");
        String buttonName = intent.getStringExtra("buttonName");
        if(buttonName.equals("Edit")){
            task_save.setText(buttonName);
            editText.setText(desc);
            update_index = intent.getIntExtra("index",0);
            //calendarView.setDate(Long.parseLong(date1));
            if(stat.equals("Pending")){
                spinner.post(new Runnable() {
                    @Override
                    public void run() {
                        spinner.setSelection(0);
                    }
                });
            }
            else if(stat.equals("Done")){
                spinner.post(new Runnable() {
                    @Override
                    public void run() {
                        spinner.setSelection(1);
                    }
                });
            }
            else if(stat.equals("In Progress")){
                spinner.post(new Runnable() {
                    @Override
                    public void run() {
                        spinner.setSelection(2);
                    }
                });
            }
        }

        Log.d("From : hh", "onCreate: "+title);
        task_btn_back_toolbar_title.setText(title);

        calendarView.setOnDateChangeListener( new CalendarView.OnDateChangeListener() {
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                Toast.makeText(getApplicationContext(),dayOfMonth+ "/"+(month+1)+"/"+year,Toast.LENGTH_SHORT).show();
                date = dayOfMonth+ "/"+(month+1)+"/"+year;
            }
        });

        task_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(task_save.getText().toString().equals("save")){
                    Log.d("save button clickd", "onClick: clicked");
                    String desc = editText.getText().toString();

                    if(desc.isEmpty() || date.isEmpty() || status.isEmpty()){
                        Toast.makeText(Task.this, "Make sure to enter each field first", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        if(title.equals("Task Group 1")){
                            util.setTask1_description(desc);
                            util.setTask1_dates(date);
                            util.setTask1_status(status);
                            Intent intent = new Intent(Task.this,MainActivity.class);
                            startActivity(intent);
                        }

                    }
                }
                else {

                    String desc1 = editText.getText().toString();

                    if(desc1.isEmpty() || date.isEmpty() || status.isEmpty()){
                        Toast.makeText(Task.this, "Make sure to enter each field first", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        util.updateDescAtIndex(update_index,desc1);
                        util.updateStatAtIndex(update_index,status);
                        util.updateDatAtIndex(update_index,date);
                        Intent intent = new Intent(Task.this,MainActivity.class);
                        startActivity(intent);
                    }
                }
            }
        });

        List<String> categories = new ArrayList<String>();
        categories.add("Pending");
        categories.add("Done");
        categories.add("In Progress");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(dataAdapter);

        spinner.setOnItemSelectedListener(this);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Task.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        status = parent.getItemAtPosition(position).toString();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
