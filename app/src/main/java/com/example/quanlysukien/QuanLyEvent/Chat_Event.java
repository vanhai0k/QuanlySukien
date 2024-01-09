package com.example.quanlysukien.QuanLyEvent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.quanlysukien.API.API;
import com.example.quanlysukien.API.URL;
import com.example.quanlysukien.R;
import com.example.quanlysukien.adapter.ChatAdapter;
import com.example.quanlysukien.model.ChatModel;
import com.example.quanlysukien.model.ReceChat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Chat_Event extends AppCompatActivity {

    ImageView back, imageEvent;
    TextView titleEvent;
    RecyclerView reclerviewChat;
    List<ChatModel> list;
    ChatAdapter adapter;
    String user_event;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_event);

        back = findViewById(R.id.back);
        reclerviewChat = findViewById(R.id.reclerviewChat);
        list = new ArrayList<>();
        LinearLayoutManager manager = new LinearLayoutManager(getBaseContext());
        reclerviewChat.setLayoutManager(manager);

        anhxa();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        chatData();
    }



    private void anhxa() {
        imageEvent = findViewById(R.id.imageEvent);
        titleEvent = findViewById(R.id.titleEvent);

        Intent intent = getIntent();
        user_event = intent.getStringExtra("event_id");
        String it_image = intent.getStringExtra("event_image");
        Glide.with(getBaseContext()).load(URL.image + it_image).into(imageEvent);
        titleEvent.setText(intent.getStringExtra("event_title"));
    }
    private void chatData(){
        RequestQueue requestQueue = Volley.newRequestQueue(getBaseContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                URL.url + "chatMess?user_event=" + user_event, null, new com.android.volley.Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        ChatModel chatModel = new ChatModel();
                        chatModel.setId(jsonObject.getString("_id"));
                        chatModel.setContent(jsonObject.getString("content"));
                        chatModel.setUser_event(jsonObject.getString("user_event"));
                        chatModel.setUser_id(jsonObject.getString("user_id"));
                        chatModel.setImage(jsonObject.getString("image"));

                        list.add(chatModel);
                    }
                    adapter = new ChatAdapter(list,getBaseContext());
                    reclerviewChat.setAdapter(adapter);
                    adapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Chat_Event.this, "Fail", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonObjectRequest);
    }
}