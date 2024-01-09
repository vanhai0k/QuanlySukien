package com.example.quanlysukien.QuanLyEvent;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.quanlysukien.API.API_Group;
import com.example.quanlysukien.API.URL;
import com.example.quanlysukien.R;
import com.example.quanlysukien.Tab_Chat_Activity;
import com.example.quanlysukien.model.MemberModel;

import java.net.URI;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Infomation_Group_Activity extends AppCompatActivity {

    TextView nameGroup;
    ImageView imageGroup,back;
    LinearLayout membersUser,logoutGroup;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infomation_group);

        nameGroup =  findViewById(R.id.nameGroup);
        imageGroup =  findViewById(R.id.imageGroup);
        back =  findViewById(R.id.back);
        logoutGroup =  findViewById(R.id.logoutGroup);
        membersUser =  findViewById(R.id.membersUser);
        membersUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getBaseContext(),UserGroup_Activity.class);

                startActivity(intent);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        intent = getIntent();
        String image = intent.getStringExtra("image");
        String nameGroups = intent.getStringExtra("nameGroup");

        nameGroup.setText(nameGroups);
        Glide.with(getBaseContext()).load(URL.image + image).into(imageGroup);

        logoutGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogLogout(v);
            }
        });
    }

    private void showDialogLogout(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(v.getRootView().getContext());
        builder.setTitle("Do you want to leave the group?");
        builder.setPositiveButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SharedPreferences sharedPreferences = getSharedPreferences("MyUser", Context.MODE_PRIVATE);
                String idUser = sharedPreferences.getString("_id",null);
                API_Group.apiGroup.logoutGroup(idUser).enqueue(new Callback<MemberModel>() {
                    @Override
                    public void onResponse(Call<MemberModel> call, Response<MemberModel> response) {
                        if (response.isSuccessful()){
                            Toast.makeText(Infomation_Group_Activity.this, "Leave the group Successful", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getBaseContext(), Tab_Chat_Activity.class));
                        }
                    }

                    @Override
                    public void onFailure(Call<MemberModel> call, Throwable t) {

                    }
                });
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}