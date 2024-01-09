package com.example.quanlysukien.QuanLyEvent;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quanlysukien.API.API;
import com.example.quanlysukien.API.API_Group;
import com.example.quanlysukien.R;
import com.example.quanlysukien.RealPathUtil;
import com.example.quanlysukien.Tab_EventMe_Activity;
import com.example.quanlysukien.model.EventChennal;
import com.example.quanlysukien.model.GroupChatModel;
import com.example.quanlysukien.model.MessageChat;
import com.google.gson.Gson;
import com.google.gson.JsonArray;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Add_GroupEvent extends AppCompatActivity {

    TextView cancel,save,nameevent;
    EditText nameGroup;
    ImageView im_image;

    private static final int MY_REQEST_CODE = 10;
    Uri mUri;
    private ActivityResultLauncher<Intent> mActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK){
                        Intent data = result.getData();
                        if (data == null){
                            return;
                        }
                        // lay 1 anh
                        Uri uri = data.getData();
                        mUri = uri;
                        try {
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getBaseContext().getContentResolver(), uri);
                            im_image.setImageBitmap(bitmap);

                        }catch (IOException e){
                            e.fillInStackTrace();
                        }
                    }

                }
            }
    );
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_group_event);


        cancel = findViewById(R.id.cancel);
        save = findViewById(R.id.save);
        nameevent = findViewById(R.id.nameevent);
        nameGroup = findViewById(R.id.nameGroup);
        im_image = findViewById(R.id.im_image);
        Intent intent = getIntent();
        nameevent.setText(intent.getStringExtra("nameEvent"));

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        im_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chonanh();
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postDataMulter();
            }
        });

    }
    private void postDataMulter() {
        String nameevents = nameGroup.getText().toString().trim();

        SharedPreferences sharedPreferences = getSharedPreferences("MyUser", Context.MODE_PRIVATE);
        String admin = sharedPreferences.getString("_id",null);

        Intent intent = getIntent();
        String eventID = intent.getStringExtra("id_event");
        nameevent.setText(intent.getStringExtra("nameEvent"));

//        List<GroupChatModel.NumberUser> numbers = new ArrayList<>();
//        String participantModelsJson = new Gson().toJson(numbers);


//        List<GroupChatModel.ChatHistirys> chatHistory = new ArrayList<>();
//        String participantChatHistory = new Gson().toJson(chatHistory);

        // Tạo một mảng rỗng
        JsonArray emptyArray = new JsonArray();

// Chuyển đổi mảng rỗng thành chuỗi JSON
        String participantChatHistory = new Gson().toJson(emptyArray);

        RequestBody requestnameEvent = RequestBody.create(MediaType.parse("multipart/form-data"), nameevents);
        assert admin != null;
        RequestBody requestAdmin = RequestBody.create(MediaType.parse("multipart/form-data"), admin);
        assert eventID != null;
        RequestBody requestEventID = RequestBody.create(MediaType.parse("multipart/form-data"), eventID);


        RequestBody requestNumbers = RequestBody.create(MediaType.parse("multipart/form-data"), admin);
        RequestBody requestChatHistory = RequestBody.create(MediaType.parse("multipart/form-data"), participantChatHistory);

        String strReaPart = null;
        if (mUri != null && mUri.getScheme() != null) {
            strReaPart = RealPathUtil.getRealPath(getBaseContext(), mUri);
        } else {
            Toast.makeText(getBaseContext(), " Image URI file not found", Toast.LENGTH_SHORT).show();
            return;
        }
        if (strReaPart == null) {
            Toast.makeText(getBaseContext(), " getting image path", Toast.LENGTH_SHORT).show();
            return;
        }
        File image = new File(strReaPart);
        if (!image.exists()) {
            Toast.makeText(getBaseContext(), "Image file not found", Toast.LENGTH_SHORT).show();
            return;
        }
        RequestBody requestBodyFile = RequestBody.create(MediaType.parse("image*"), image);
        try {
            MultipartBody.Part mPart = MultipartBody.Part.createFormData("image", image.getName(), requestBodyFile);
            if (mPart == null) {
                Toast.makeText(getBaseContext(), "Error creating MultipartBody", Toast.LENGTH_SHORT).show();
                return;
            }
            if (nameevents.isEmpty()){
                Toast.makeText(getBaseContext(), "Don't leave it blank Name Event", Toast.LENGTH_SHORT).show();
                return;
            }


            API_Group.apiGroup.addGroup(requestnameEvent,requestAdmin,requestNumbers,
                    requestEventID,requestChatHistory,mPart).enqueue(new Callback<GroupChatModel>() {
                @Override
                public void onResponse(Call<GroupChatModel> call, Response<GroupChatModel> response) {
                    if (response.isSuccessful()){
                        Toast.makeText(getBaseContext(), "Thêm Thành Công", Toast.LENGTH_SHORT).show();
                        nameGroup.setText("");
                        startActivity(new Intent(getBaseContext(), Tab_EventMe_Activity.class));
                    }else{
                        Toast.makeText(getBaseContext(), "loi: "+ response.code(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<GroupChatModel> call, Throwable t) {

                }
            });
        } catch (NullPointerException e) {
            Toast.makeText(getBaseContext(), "Error creating MultipartBody: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void chonanh() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M){
            openGallery();
            return;
        }
        if (getBaseContext().checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
            openGallery();
        }else{
            String [] permisstion = {Manifest.permission.READ_EXTERNAL_STORAGE};
            requestPermissions(permisstion, MY_REQEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_REQEST_CODE){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openGallery();
            }
        }

    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        mActivityResultLauncher.launch(Intent.createChooser(intent, "Select Image"));
    }
}