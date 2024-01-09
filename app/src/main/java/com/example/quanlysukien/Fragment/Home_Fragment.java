package com.example.quanlysukien.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlysukien.API.API;
import com.example.quanlysukien.R;
import com.example.quanlysukien.adapter.EventAdapter;
import com.example.quanlysukien.model.EventChennal;
import com.example.quanlysukien.model.ReceEvent;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Home_Fragment extends Fragment {

    RecyclerView recyclerview;
    EventAdapter adapter;
    List<EventChennal> list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home_, container, false);

        recyclerview = view.findViewById(R.id.recyclerview);
        list = new ArrayList<>();

        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        recyclerview.setLayoutManager(manager);

        getDataEvent();

        return view;
    }
    private void getDataEvent() {
        API.api.getData().enqueue(new Callback<ReceEvent>() {
            @Override
            public void onResponse(Call<ReceEvent> call, Response<ReceEvent> response) {
                if (response.isSuccessful()) {
                    list = response.body().getData();
                    adapter = new EventAdapter(list, getContext());
                    recyclerview.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }else{
                    Toast.makeText(getActivity(), "Fail not", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ReceEvent> call, Throwable t) {
                Toast.makeText(getActivity(), "Fail" + t, Toast.LENGTH_SHORT).show();
                Log.d("ggg", "onFailure: " + t);
            }
        });
    }
}