package com.example.quanlysukien.Home;

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
import com.example.quanlysukien.R;
import com.example.quanlysukien.adapter.EventAdapter;
import com.example.quanlysukien.model.EventChennal;
import com.example.quanlysukien.model.ReceEventStart;
import com.example.quanlysukien.model.ReceUpcomingEvents;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Upcoming_Fragment extends Fragment {

    EventAdapter adapter;
    List<EventChennal> list;
    RecyclerView recyclerview;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_upcoming_, container, false);

        recyclerview = view.findViewById(R.id.recyclerview);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        recyclerview.setLayoutManager(manager);

        getDataEvent();

        return view;
    }

    private void getDataEvent() {
        API.api.getEventChuaStart().enqueue(new Callback<ReceUpcomingEvents>() {
            @Override
            public void onResponse(Call<ReceUpcomingEvents> call, Response<ReceUpcomingEvents> response) {
                if (response.isSuccessful()) {
                    list = response.body().getUpcomingEvents();
                    adapter = new EventAdapter(list, getContext());
                    recyclerview.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }else{
                    Toast.makeText(getActivity(), "Fail not", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ReceUpcomingEvents> call, Throwable t) {
                Toast.makeText(getActivity(), "Fail", Toast.LENGTH_SHORT).show();
                Log.d("mmmmm", "onFailure: "+ t);
            }
        });
    }
}