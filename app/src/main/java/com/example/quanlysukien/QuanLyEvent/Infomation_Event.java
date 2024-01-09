package com.example.quanlysukien.QuanLyEvent;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.example.quanlysukien.API.API_Group;
import com.example.quanlysukien.API.URL;
import com.example.quanlysukien.QuanLyUserEvent.Count_User_Event;
import com.example.quanlysukien.R;
import com.example.quanlysukien.adapter.UserCountEvent;
import com.example.quanlysukien.model.Group;
import com.example.quanlysukien.model.JoinEventRequest;
import com.example.quanlysukien.model.UserEvent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Infomation_Event extends AppCompatActivity {

    TextView nameEvent,endTime,startTime,startDate,endDate,location,decription,limit,senduser;
    ImageView image;
    Button join_event,chat_event,addGroup;
    String id_event,it_image,it_title;
    RecyclerView recyclerview_count;
    List<UserEvent> list;
    UserCountEvent adapter;
    TextView countUE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infomation_event);

        anhXaView();
        }


    private void anhXaView() {
        image = findViewById(R.id.image);
        join_event = findViewById(R.id.join_event);
        startDate = findViewById(R.id.startDate);
        endDate = findViewById(R.id.dateEnd);
        endTime = findViewById(R.id.endTime);
        startTime = findViewById(R.id.startTime);
        nameEvent = findViewById(R.id.eventName);
        location = findViewById(R.id.location);
        decription = findViewById(R.id.description);
        limit = findViewById(R.id.limit);
        senduser = findViewById(R.id.senduser);
        chat_event = findViewById(R.id.chat_event);
        countUE = findViewById(R.id.countUE);
        addGroup = findViewById(R.id.addGroup);
        recyclerview_count = findViewById(R.id.recyclerview_count);
         TextView back = findViewById(R.id.back);

         list = new ArrayList<>();
        LinearLayoutManager manager = new LinearLayoutManager(getBaseContext());
        recyclerview_count.setLayoutManager(manager);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        Intent intent = getIntent();

         it_title = intent.getStringExtra("nameEvent");
        String it_limit = intent.getStringExtra("limit");
        String it_timeStart = intent.getStringExtra("timeStart");
        String it_timeEnd = intent.getStringExtra("timeEnd");
        String it_dateStart = intent.getStringExtra("dateStart");
        String it_dateEnd = intent.getStringExtra("dateEnd");
        String it_location = intent.getStringExtra("location");
        String it_description = intent.getStringExtra("description");
        it_image = intent.getStringExtra("image");
        id_event = intent.getStringExtra("id_event");

        startTime.setText(it_timeStart);
        endTime.setText(it_timeEnd);
        startDate.setText(it_dateStart);
        endDate.setText(it_dateEnd);
        nameEvent.setText(it_title);
        limit.setText(it_limit);
        decription.setText(it_description);
        location.setText(it_location);


        Glide.with(getBaseContext()).load(URL.image + it_image).into(image);

        senduser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), Count_User_Event.class);

                intent.putExtra("event_id", id_event);

                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        callapiUserCountEvent();
        chat_event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), Chat_Event.class);

                intent.putExtra("event_id", id_event);
                intent.putExtra("event_title", it_title);
                intent.putExtra("event_image", it_image);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        join_event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                joinEvent();
            }
        });
        click();
        callAPIGroup();
        addGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                joinGroup(v);
            }
        });

    }
    private String groupID;
    private void callAPIGroup() {
        RequestQueue requestQueue = Volley.newRequestQueue(getBaseContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                URL.url + "getGroup", null, new com.android.volley.Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    boolean foundGroup = false;
                    SharedPreferences sharedPreferencesGroups = getSharedPreferences("group", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editorGroup = sharedPreferencesGroups.edit();;
                    JSONArray jsonArray = response.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        String eventID = jsonObject.getString("eventID");
                        groupID = jsonObject.getString("_id");
                        if (eventID.equals(id_event)){
                            foundGroup = true;  // Nếu tìm thấy nhóm, đặt biến foundGroup thành true
                            editorGroup.putString("_id", groupID);
                            Toast.makeText(Infomation_Event.this, "idgr: "+ groupID, Toast.LENGTH_SHORT).show();
                            break;  // Dừng vòng lặp vì đã tìm thấy một nhóm
                        }
                        editorGroup.apply();
                    }
                    if (foundGroup) {
                        addGroup.setVisibility(View.VISIBLE);
                    } else {
                        addGroup.setVisibility(View.GONE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Infomation_Event.this, "Fail", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonObjectRequest);
    }

    private void joinGroup(View v) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(v.getRootView().getContext());

        // Sử dụng LayoutInflater để inflate layout vào View
        View view = LayoutInflater.from(getBaseContext()).inflate(R.layout.dialog_addgroup, null);
        alertDialogBuilder.setView(view);

        alertDialogBuilder.setPositiveButton("Join", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                EditText ed_status = view.findViewById(R.id.ed_status);
                String content = ed_status.getText().toString().trim();
                SharedPreferences sharedPreferences = getSharedPreferences("MyUser", Context.MODE_PRIVATE);
                String id_user = sharedPreferences.getString("_id",null);

                Group group = new Group();
                group.setUserID(id_user);
                group.setGroupID(groupID);
                String status = "1";
                group.setStatus(status);
                group.setContent(content);

                API_Group.apiGroup.joinGroup(group).enqueue(new Callback<Group>() {
                    @Override
                    public void onResponse(Call<Group> call, Response<Group> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(Infomation_Event.this, "Request to join has been sent", Toast.LENGTH_SHORT).show();
                        }else{
                            if (response.code() == 400){
                                Toast.makeText(Infomation_Event.this, "You have sent a request to join!!!", Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(Infomation_Event.this, "That bai", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<Group> call, Throwable t) {
                        Toast.makeText(Infomation_Event.this, "Fail", Toast.LENGTH_SHORT).show();
                    }
                });


            }
        });
        alertDialogBuilder.setNegativeButton("Done", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        // Hiển thị AlertDialog
        alertDialog.show();
    }

    private void click() {
        RequestQueue requestQueue = Volley.newRequestQueue(getBaseContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                URL.url + "event/" + id_event, null, new com.android.volley.Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    int participantCount = response.getInt("participantCount");
                    countUE.setText(participantCount+"");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonObjectRequest);
    }


    private void callapiUserCountEvent() {

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
                Toast.makeText(Infomation_Event.this, "Fail", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonObjectRequest);
    }

    private void joinEvent() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyUser", Context.MODE_PRIVATE);
        String id_user = sharedPreferences.getString("_id",null);
        String status = "1";

        JoinEventRequest joinEventRequest = new JoinEventRequest(id_event,id_user,status);
        API.api.joinEvent(joinEventRequest).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    Toast.makeText(Infomation_Event.this, "Participate in success", Toast.LENGTH_SHORT).show();
                }else {
                    if (response.code() == 400){
                        Toast.makeText(Infomation_Event.this, "The user has joined the event", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(Infomation_Event.this, "Not Found", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }
}