package com.example.wil;

import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Struct;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    InfoVModel infoViewModel;
    ListView allTaskListView;
    AllTaskAdapter allTaskAdapter;
    Util util;
    List<String>  desc = new ArrayList<>();
    List<String> stat = new ArrayList<>();
    List<String> dat = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("All Tasks");

        infoViewModel = ViewModelProviders.of(MainActivity.this).get(InfoVModel.class);
        final List<Info> data = infoViewModel.getAllNotes();
        //Log.d("FROM DB", "Key 1: "+data.get(1).name);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.getHeaderView(0);
        TextView navUsername = headerView.findViewById(R.id.header_name);
        TextView navEmail = headerView.findViewById(R.id.header_email);
        ImageView profilePic = headerView.findViewById(R.id.header_profilepic);
        if(data.size() > 0) {
            navUsername.setText("" + data.get(0).name);
            navEmail.setText(data.get(0).email);
            Bitmap profPic = getImage(data.get(0).profilePic);
            profilePic.setImageBitmap(profPic);
            Bitmap coverPic = getImage(data.get(0).coverPic);
            BitmapDrawable background = new BitmapDrawable(getResources(), coverPic);
            headerView.setBackground(background);
        }
        headerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Info> data1 = infoViewModel.getAllNotes();
                Log.d("heyhey", "onClick: "+data1.size());
                if(data1.size() > 0){
                    Intent intent = new Intent(MainActivity.this, ProfileScreen.class);
                    intent.putExtra("buttonName", "update");
                    startActivity(intent);
                }
                else{
                    Intent intent = new Intent(MainActivity.this, ProfileScreen.class);
                    intent.putExtra("buttonName", "save");
                    startActivity(intent);
                }

            }
        });

        Button btn = drawer.findViewById(R.id.add_task_group);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddTaskGroup.class);
                startActivity(intent);
            }
        });

        allTaskListView = findViewById(R.id.allTaskListView);
        util = Util.getInstance();


        desc = new ArrayList<>();
        stat = new ArrayList<>();
        dat = new ArrayList<>();

        if(desc.size()>0){
            desc.clear();
            stat.clear();
            dat.clear();
        }


        desc = util.getTask1_description();
        stat = util.getTask1_status();
        dat = util.getTask1_dates();

        for(int i=0; i<util.getTask2_description().size(); i++){
            desc.add(util.getTask2_description().get(i));
        }
        for(int i=0; i<util.getTask2_description().size(); i++){
            stat.add(util.getTask2_status().get(i));
        }
        for(int i=0; i<util.getTask2_description().size(); i++){
            dat.add(util.getTask2_dates().get(i));
        }
        allTaskAdapter = new AllTaskAdapter(this,desc,stat,dat);
        allTaskListView.setAdapter(allTaskAdapter);
        allTaskListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                new AlertDialog.Builder(MainActivity.this)
                        .setPositiveButton("Delete Task", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                desc.remove(position);
                                allTaskAdapter.notifyDataSetChanged();
                            }
                        })


                        .setNegativeButton("Modify Task",  new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(MainActivity.this,Task.class);
                                intent.putExtra("desc",desc.get(position));
                                intent.putExtra("date",dat.get(position));
                                intent.putExtra("stat",stat.get(position));
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

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            Intent intent = new Intent(MainActivity.this, TaskGroup.class);
            intent.putExtra("Task Group","Task Group 1");
            startActivity(intent);
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    // convert from byte array to bitmap
    public Bitmap getImage(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }
}
