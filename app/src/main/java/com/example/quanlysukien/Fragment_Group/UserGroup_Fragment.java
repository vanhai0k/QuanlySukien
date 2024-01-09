package com.example.quanlysukien.Fragment_Group;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.quanlysukien.API.URL;
import com.example.quanlysukien.R;
import com.example.quanlysukien.adapter.MemberAdapter;
import com.example.quanlysukien.model.UserMember;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class UserGroup_Fragment extends Fragment {

    List<UserMember> list;
    MemberAdapter adapter;
    RecyclerView recyclerviewmember;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_group_, container, false);

        recyclerviewmember = view.findViewById(R.id.recyclerviewmember);
        recyclerviewmember.setLayoutManager(new LinearLayoutManager(getContext()));
        list = new ArrayList<>();

        callAPI();

        return view;
    }

    private void callAPI() {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("group", Context.MODE_PRIVATE);
        String idgroup = sharedPreferences.getString("id_group",null);
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                URL.url + "getGroupID?_id=" +idgroup, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        JSONArray jsonArrayNumber = jsonObject.getJSONArray("numbers");
                        for (int j = 0; j < jsonArrayNumber.length(); j++) {
                            JSONObject numberObject = jsonArrayNumber.getJSONObject(j);
                            UserMember member = new UserMember();
                            // Extract data from the nested JSON object
                            JSONObject userIDObject = numberObject.getJSONObject("userID");

                            member.setUsername(userIDObject.getString("username"));
                            member.setImage(userIDObject.getString("image"));
                            member.setId(userIDObject.getString("_id"));

                            list.add(member);
                        }
                        adapter = new MemberAdapter(getContext(),list);
                        recyclerviewmember.setAdapter(adapter);

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
}