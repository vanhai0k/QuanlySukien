package com.example.quanlysukien.Fragment_Group;

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

import com.example.quanlysukien.API.API;
import com.example.quanlysukien.API.API_Group;
import com.example.quanlysukien.R;
import com.example.quanlysukien.adapter.JoingroupAdapter;
import com.example.quanlysukien.model.JoinGroup;
import com.example.quanlysukien.model.ReceJoinGroup;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Confirmation_Fragment extends Fragment {

    List<JoinGroup> list;
    JoingroupAdapter adapter;
    RecyclerView recyclerview;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_confirmation_, container, false);

        recyclerview = view.findViewById(R.id.recyclerview);
        list = new ArrayList<>();
        recyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));

        callAPIGroup();
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("group", Context.MODE_PRIVATE);

        return view;
    }

    private void callAPIGroup() {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("group", Context.MODE_PRIVATE);
        String idgroup = sharedPreferences.getString("id_group",null);

        API_Group.apiGroup.getJoinGroup(idgroup).enqueue(new Callback<ReceJoinGroup>() {
            @Override
            public void onResponse(Call<ReceJoinGroup> call, Response<ReceJoinGroup> response) {
                if (response.isSuccessful()){
                    list = response.body().getData();
                    adapter = new JoingroupAdapter(getContext(),list);
                    recyclerview.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<ReceJoinGroup> call, Throwable t) {
                Toast.makeText(getContext(), "Fail", Toast.LENGTH_SHORT).show();
                Log.d("lll", "onFailure: "+ t);
            }
        });
    }
}