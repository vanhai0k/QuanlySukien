package com.example.quanlysukien.UserFunctions;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.quanlysukien.API.URL;
import com.example.quanlysukien.R;

public class TKMK_Activity extends AppCompatActivity {

    ImageView back,image;
    TextView username;
    LinearLayout starttUpdatepassword,startUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tkmk);
        SharedPreferences sharedPreferences = getSharedPreferences("MyUser", Context.MODE_PRIVATE);


        starttUpdatepassword = findViewById(R.id.starttUpdatepassword);
        startUser = findViewById(R.id.startUser);
        back = findViewById(R.id.back);
        username = findViewById(R.id.username);
        image = findViewById(R.id.image);

        String iamges = sharedPreferences.getString("image",null);
        String names = sharedPreferences.getString("username",null);
        username.setText(names);
        Glide.with(getBaseContext()).load(URL.image + iamges).into(image);


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        starttUpdatepassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(), Update_Password.class));
            }
        });
        startUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(), Update_Infomation_User.class));
            }
        });
    }
}