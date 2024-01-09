package com.example.quanlysukien.QuanLyUserEvent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.quanlysukien.API.URL;
import com.example.quanlysukien.QuanLyEvent.Infomation_Event;
import com.example.quanlysukien.R;
import com.example.quanlysukien.adapter.UserCountEvent;
import com.example.quanlysukien.model.UserEvent;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Count_User_Event extends AppCompatActivity {

    List<UserEvent> list;
    UserCountEvent adapter;
    RecyclerView recyclerview_count;
    TextView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_count_user_event);

        recyclerview_count = findViewById(R.id.recyclerview_count);
        back = findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        list = new ArrayList<>();
        LinearLayoutManager manager = new LinearLayoutManager(getBaseContext());
        recyclerview_count.setLayoutManager(manager);

        callapiUserCountEvent();

    }
    private void callapiUserCountEvent() {
        Intent intent = getIntent();
        String id_event = intent.getStringExtra("event_id");
        RequestQueue requestQueue = Volley.newRequestQueue(getBaseContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                URL.url + "postEvent?_id=" + id_event, null, new com.android.volley.Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        JSONArray jsonArrayParti = jsonObject.getJSONArray("participants");
                        for (int j = 0; j < jsonArrayParti.length(); j++) {
                            JSONObject jsonObjectParti = jsonArrayParti.getJSONObject(j);
                            JSONObject userObject = jsonObjectParti.getJSONObject("user_id");

                            UserEvent userEvent = new UserEvent();

                            userEvent.setId(userObject.getString("_id"));
                            userEvent.setUsername(userObject.getString("username"));
                            userEvent.setEmail(userObject.getString("email"));
                            userEvent.setImage(userObject.getString("image"));

                            list.add(userEvent);
                        }
                        adapter = new UserCountEvent(list,getBaseContext());
                        recyclerview_count.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Count_User_Event.this, "Fail", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonObjectRequest);
    }
}