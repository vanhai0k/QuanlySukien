package com.example.quanlysukien.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.quanlysukien.API.URL;
import com.example.quanlysukien.GiaoDien.Login_Activity;
import com.example.quanlysukien.R;
import com.example.quanlysukien.UserFunctions.TKMK_Activity;

import org.json.JSONException;
import org.json.JSONObject;


public class Setting_Fragment extends Fragment {

    TextView countUE,limit,countPlace;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_setting_, container, false);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyUser", Context.MODE_PRIVATE);

        TextView loguot = view.findViewById(R.id.logout);
        LinearLayout startTKMK =view.findViewById(R.id.startTKMK);
        TextView username = view.findViewById(R.id.username);
        ImageView image = view.findViewById(R.id.image);
        countUE = view.findViewById(R.id.countUE);
        limit = view.findViewById(R.id.limit);
        countPlace = view.findViewById(R.id.countPlace);

        String iamges = sharedPreferences.getString("image",null);
        String names = sharedPreferences.getString("username",null);
        username.setText(names);
        Glide.with(getActivity()).load(URL.image + iamges).into(image);

        startTKMK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), TKMK_Activity.class));
            }
        });

        loguot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), Login_Activity.class));
            }
        });
        click();
        clickCountJoineventuser();
        clickCountAdmin();
        return view;
    }
    private void click() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyUser", Context.MODE_PRIVATE);
        String user_id = sharedPreferences.getString("_id",null);
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                URL.url + "eventCountUser/" + user_id, null, new com.android.volley.Response.Listener<JSONObject>() {
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
    private void clickCountAdmin() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyUser", Context.MODE_PRIVATE);
        String user_id = sharedPreferences.getString("_id",null);
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                URL.url + "eventStartAdmin/" + user_id, null, new com.android.volley.Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    int participantCount = response.getInt("ongoingEventsCount");
                    countPlace.setText(participantCount+"");
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
    private void clickCountJoineventuser() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyUser", Context.MODE_PRIVATE);
        String user_id = sharedPreferences.getString("_id",null);
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                URL.url + "countJoinevent/" + user_id, null, new com.android.volley.Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    int participantCount = response.getInt("participantCount");
                    limit.setText(participantCount+"");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Fail", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonObjectRequest);
    }

    @Override
    public void onResume() {
        super.onResume();
        click();
        clickCountJoineventuser();
        clickCountAdmin();
    }
}