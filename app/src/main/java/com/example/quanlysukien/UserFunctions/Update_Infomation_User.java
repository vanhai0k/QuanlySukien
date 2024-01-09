package com.example.quanlysukien.UserFunctions;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.quanlysukien.API.URL;
import com.example.quanlysukien.R;

public class Update_Infomation_User extends AppCompatActivity {

    String emails,dates,names,sexs;
    ImageView back;
    TextView email,username,date,sex;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_infomation_user);
        SharedPreferences sharedPreferences = getSharedPreferences("MyUser", Context.MODE_PRIVATE);

        back = findViewById(R.id.back);
        email = findViewById(R.id.email);
        username = findViewById(R.id.username);
        date = findViewById(R.id.date);
        sex = findViewById(R.id.sex);
        ImageView image = findViewById(R.id.image);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        names = sharedPreferences.getString("username",null);
        emails = sharedPreferences.getString("email",null);
        dates = sharedPreferences.getString("dob",null);
        sexs = sharedPreferences.getString("sex",null);
        String iamges = sharedPreferences.getString("image",null);

        email.setText(emails);
        username.setText(names);
        date.setText(dates);
        sex.setText(sexs);

        Glide.with(getBaseContext()).load(URL.image + iamges).into(image);
    }
}