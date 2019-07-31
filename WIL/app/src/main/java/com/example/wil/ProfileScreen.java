package com.example.wil;

import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

public class ProfileScreen extends AppCompatActivity {

    InfoVModel infoViewModel;
    Button save;
    EditText phoneNumber, email;
    TextView name;
    ImageView imageView, cover_pic;
    ImageButton imageButton;
    byte[] profilePic;
    byte[] coverPic;

    int val = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_screen);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        name = findViewById(R.id.profile_edt_name);
        phoneNumber = findViewById(R.id.profile_edt_phone);
        email = findViewById(R.id.profile_edt_email);
        save = findViewById(R.id.profile_save);
        imageView = findViewById(R.id.profile_pic);
        cover_pic = findViewById(R.id.t_cover_pic);

        imageButton = findViewById(R.id.profile_btn_back);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileScreen.this, MainActivity.class);
                startActivity(intent);
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(ProfileScreen.this);
                builder1.setMessage("Choose first");
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "Update Profile Pic ?",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                val = 1;
                                showOption();
                            }
                        });

                builder1.setNegativeButton(
                        "Update Cover Pic ?",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                val = 2;
                                showOption();
                            }
                        });

                AlertDialog alert11 = builder1.create();
                alert11.show();
                //showOption();

            }
        });

        String buttonName = getIntent().getStringExtra("buttonName");

        if (buttonName.equals("save")){
            Log.d("hihihi", "onClick: inside save");
           save.setText("save");
        }
        else if(buttonName.equals("update") ){
            Log.d("hihihi", "onClick: inside update");
            save.setText("update");
        }

        infoViewModel = ViewModelProviders.of(ProfileScreen.this).get(InfoVModel.class);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = name.getText().toString();
                String userPhoneNumber = phoneNumber.getText().toString();
                String userEmail = email.getText().toString();

                if( userName.isEmpty() || userPhoneNumber.isEmpty() || userEmail.isEmpty() ){
                    Toast.makeText(ProfileScreen.this, "Fields can not be empty", Toast.LENGTH_SHORT).show();
                }
                else{

                    if(save.getText().toString() == "save"){
                        Log.d("hihihi", "onClick: inside save");
                        infoViewModel.insert(new Info(1,userName,userPhoneNumber,userEmail,profilePic,coverPic));
                        Toast.makeText(ProfileScreen.this, "Data stored in database", Toast.LENGTH_SHORT).show();
                    }
                    else if(save.getText().toString() == "update"){
                        Log.d("hihihi", "onClick: inside update");
                        infoViewModel.updateFirstId();
                        infoViewModel.insert(new Info(1,userName,userPhoneNumber,userEmail,profilePic,coverPic));
                        Toast.makeText(ProfileScreen.this, "Data updated in database", Toast.LENGTH_SHORT).show();
                    }

                    name.setText("");
                    phoneNumber.setText("");
                    email.setText("");
                    List<Info> data = infoViewModel.getAllNotes();
                    for(int i=0;i<data.size();i++){
                        Log.d("DATABASE:::::", "onClick: "+data.get(i).name);
                    }

                }
            }
        });

    }

    private void showOption() {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(ProfileScreen.this);
        builder1.setMessage("Update Image?");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Open Camera",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(cameraIntent, 11);
                    }
                });

        builder1.setNegativeButton(
                "Open Galary",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent();
                        intent.setType("image/*");
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(Intent.createChooser(intent, "Select Picture"),111);
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (requestCode == 111) {
        if (resultCode == Activity.RESULT_OK) {
            if (data != null) {
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), data.getData());
                    ImageView imageview = (ImageView) findViewById(R.id.profile_pic);
                    ImageView imageview1 = (ImageView) findViewById(R.id.t_cover_pic);

                    if(val == 1){
                        profilePic = getBytes(bitmap);
                        imageview.setImageBitmap(bitmap);
                        val = 0;
                    }
                    if( val == 2 ){
                        coverPic = getBytes(bitmap);
                        imageview1.setImageBitmap(bitmap);
                        val = 0;
                    }



                } catch (IOException e) {
                    val = 0;
                    e.printStackTrace();
                }
            }
        } else if (resultCode == Activity.RESULT_CANCELED)  {
            val = 0;
            Toast.makeText(this, "Canceled", Toast.LENGTH_SHORT).show();
        }
    }
    if (requestCode == 11) {
        Bitmap image = (Bitmap) data.getExtras().get("data");
        ImageView imageview = (ImageView) findViewById(R.id.profile_pic);
        ImageView imageview1 = (ImageView) findViewById(R.id.t_cover_pic);

        if(val == 1){
            profilePic = getBytes(image);
            imageview.setImageBitmap(image);
            val = 0;
        }
        if( val == 2 ){
            coverPic = getBytes(image);
            imageview1.setImageBitmap(image);
            val = 0;
        }
    }
}

    // convert from bitmap to byte array
    public byte[] getBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        return stream.toByteArray();
    }


}
