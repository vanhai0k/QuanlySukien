package com.example.quanlysukien.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.quanlysukien.API.API_Group;
import com.example.quanlysukien.API.URL;
import com.example.quanlysukien.R;
import com.example.quanlysukien.adapter.ChatGroupAdapter;
import com.example.quanlysukien.model.ChatGroup;
import com.example.quanlysukien.model.RecaChatGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Chat_Fragment extends Fragment {

    RecyclerView recyclerView;
    List<ChatGroup> list;
    ChatGroupAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chat_, container, false);

        list = new ArrayList<>();
        recyclerView = view.findViewById(R.id.recyclerviewChat);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(manager);

        callAPIChat();

        return view;
    }

    private void callAPIChat() {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("MyUser", Context.MODE_PRIVATE);
        String id_user = sharedPreferences.getString("_id",null);
        API_Group.apiGroup.getGroupChat(id_user).enqueue(new Callback<List<ChatGroup>>() {
            @Override
            public void onResponse(Call<List<ChatGroup>> call, Response<List<ChatGroup>> response) {
                if (response.isSuccessful()) {
                    list = response.body();
                    adapter = new ChatGroupAdapter(getContext(),list);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }else {
                    Toast.makeText(getContext(), "loiiii", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<ChatGroup>> call, Throwable t) {
                Toast.makeText(getContext(), "Fail", Toast.LENGTH_SHORT).show();
                Log.d("mmmm", "onFailure: "+ t);
            }
        });
    }

    private void callapi(){
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                URL.url + "getGroup", null, new com.android.volley.Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        ChatGroup chatGroup = new ChatGroup();
                        chatGroup.setName(jsonObject.getString("name"));

                        list.add(chatGroup);
                    }
                    adapter = new ChatGroupAdapter(getContext(),list);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                adapter = new ChatGroupAdapter(getContext(),list);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        });
    }
}