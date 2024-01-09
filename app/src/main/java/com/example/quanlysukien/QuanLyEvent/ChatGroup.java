package com.example.quanlysukien.QuanLyEvent;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.TextWatcher;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.quanlysukien.API.API;
import com.example.quanlysukien.API.API_Group;
import com.example.quanlysukien.API.URL;
import com.example.quanlysukien.R;
import com.example.quanlysukien.RealPathUtil;
import com.example.quanlysukien.adapter.MessageChatUserAdapter;
import com.example.quanlysukien.model.MessageChat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;

public class ChatGroup extends AppCompatActivity {

    ImageView back,imageLike,imageMessage,imageEvent;
    TextView contentMessage,titleEvent;
    RecyclerView recyclerview;
    List<MessageChat> list;
    MessageChatUserAdapter adapter;
    LinearLayout infoGroup;
    private static final int MY_REQEST_CODE = 10;
    Uri mUri;
    Intent intent;
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
//                            imageMessage.setImageBitmap(bitmap);

                            // Chèn ảnh vào EditText
                            SpannableString spannableString = new SpannableString(" ");
                            spannableString.setSpan(new ImageSpan(bitmap), 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            contentMessage.append(spannableString);
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
        setContentView(R.layout.activity_chat_group);


        anhxaView();
    }

    private void anhxaView() {
        back = findViewById(R.id.back);
        imageLike = findViewById(R.id.imageLike);
        recyclerview = findViewById(R.id.recyclerview);
        imageMessage = findViewById(R.id.imageMessage);
        contentMessage = findViewById(R.id.contentMessage);
        imageEvent = findViewById(R.id.imageEvent);
        titleEvent = findViewById(R.id.titleEvent);
        infoGroup = findViewById(R.id.infoGroup);

        intent = getIntent();
        String imageIM = intent.getStringExtra("image");
        titleEvent.setText(intent.getStringExtra("nameGroup"));

        String nameGR = intent.getStringExtra("nameGroup");

        infoGroup.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("UnsafeIntentLaunch")
            @Override
            public void onClick(View v) {
                intent = new Intent(getBaseContext(),Infomation_Group_Activity.class);

                intent.putExtra("nameGroup",nameGR);
                intent.putExtra("image",imageIM);
                startActivity(intent);
            }
        });

        Glide.with(getBaseContext()).load(URL.image + imageIM).into(imageEvent);

        imageMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chonanh();
            }
        });

        contentMessage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
// Không cần làm gì khi văn bản thay đổi
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
// Không cần làm gì khi văn bản thay đổi
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    // Thay đổi ảnh của imageLike thành ảnh khác
                    imageLike.setImageResource(R.drawable.sendmessage);
                    imageLike.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            postMessage();
                        }
                    });
                } else {
                    // Nếu không có nội dung, sử dụng ảnh mặc định
                    imageLike.setImageResource(R.drawable.like);
                    imageLike.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            postLike();
                        }
                    });
                }
            }
        });
        list= new ArrayList<>();
        LinearLayoutManager manager = new LinearLayoutManager(getBaseContext());
        recyclerview.setLayoutManager(manager);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        contentMessage.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                contentMessage.getViewTreeObserver().removeOnPreDrawListener(this);

                // Thêm lắng nghe sự kiện mở bàn phím
                contentMessage.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        if (hasFocus) {
                            // Bàn phím đã mở, cuộn RecyclerView đến vị trí cuối cùng
                            scrollToBottom();
                        }
                    }
                });
                return true;
            }
        });
        callAPIMessage();

    }

    private void postLike() {
        Toast.makeText(ChatGroup.this, "like", Toast.LENGTH_SHORT).show();
    }

    private void postMessage() {
        SharedPreferences sharedPreferences = getBaseContext().getSharedPreferences("MyUser", Context.MODE_PRIVATE);
        String id_user = sharedPreferences.getString("_id", null);
        Intent intent = getIntent();
        String id_group = intent.getStringExtra("id_group");
        String contentMessages = contentMessage.getText().toString().trim();

        RequestBody requestContent = RequestBody.create(MediaType.parse("multipart/form-data"), contentMessages);
        assert id_user != null;
        RequestBody requestIDU = RequestBody.create(MediaType.parse("multipart/form-data"), id_user);
        assert id_group != null;
        RequestBody requestChatId = RequestBody.create(MediaType.parse("multipart/form-data"), id_group);

        MultipartBody.Part mPart = null;

        if (mUri != null && mUri.getScheme() != null) {
            String strReaPart = RealPathUtil.getRealPath(getBaseContext(), mUri);
            File image = new File(strReaPart);

            if (!image.exists()) {
                Toast.makeText(getBaseContext(), "Image file not found", Toast.LENGTH_SHORT).show();
                return;
            }

            RequestBody requestBodyFile = RequestBody.create(MediaType.parse("image*"), image);
            mPart = MultipartBody.Part.createFormData("image", image.getName(), requestBodyFile);
        }

        if (contentMessages.isEmpty() && mPart == null) {
            Toast.makeText(getBaseContext(), "Content or image is required", Toast.LENGTH_SHORT).show();
            return;
        }

        API_Group.apiGroup.message(requestChatId, requestIDU, requestContent, mPart).enqueue(new Callback<MessageChat>() {
            @Override
            public void onResponse(Call<MessageChat> call, retrofit2.Response<MessageChat> response) {
                if (response.isSuccessful()) {
                    contentMessage.setText("");
                    if (adapter != null) {
                        adapter.notifyDataSetChanged();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Error: " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MessageChat> call, Throwable t) {
                Toast.makeText(ChatGroup.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("gggg", "onFailure: " + t.getMessage());
            }
        });
    }



    private void scrollToBottom() {
        // Đợi một chút để đảm bảo rằng RecyclerView đã cập nhật dữ liệu
        recyclerview.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (adapter != null) {
                    // Cuộn RecyclerView đến vị trí cuối cùng
                    recyclerview.scrollToPosition(adapter.getItemCount() - 1);
                }
            }
        }, 200); // Đợi 200ms (có thể cần điều chỉnh)
    }
    private void callAPIMessage() {
        Intent intent = getIntent();
        String id_group = intent.getStringExtra("id_group");
        RequestQueue requestQueue = Volley.newRequestQueue(getBaseContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                URL.url + "getGroupMessage?_id="+ id_group, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        JSONArray jsonArrayChat = jsonObject.getJSONArray("chatHistory");

                        for (int j = 0; j < jsonArrayChat.length(); j++) {
                            JSONObject jsonObjectChat = jsonArrayChat.getJSONObject(j);
                            MessageChat messageChat = new MessageChat();

                            messageChat.setMessage(jsonObjectChat.getString("message"));
                            messageChat.setUserId(jsonObjectChat.getString("userId"));
                            messageChat.setImage(jsonObjectChat.getString("image"));
                            messageChat.setTime(jsonObjectChat.getString("time"));

                            list.add(messageChat);
                        }
                        adapter = new MessageChatUserAdapter(getBaseContext(),list);
                        recyclerview.setAdapter(adapter);
                        // Cập nhật Adapter
                        adapter.notifyDataSetChanged();

                        // Cuộn RecyclerView xuống dưới cùng
                        int itemCount = adapter.getItemCount();

                        if (itemCount > 0) {
                            // Scroll to the last item if there is data
                            recyclerview.smoothScrollToPosition(itemCount - 1);
                        } else {
                            // Handle the case when there is no data, e.g., show a message to the user
                            // Alternatively, you might want to scroll to a different position or perform a different action
                            Log.d("llll", "No data available to scroll to.");
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonObjectRequest);
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