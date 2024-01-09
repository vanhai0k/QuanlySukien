package com.example.quanlysukien.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlysukien.API.API;
import com.example.quanlysukien.R;
import com.example.quanlysukien.adapter.EvenMeAdapter;
import com.example.quanlysukien.adapter.EventAdapter;
import com.example.quanlysukien.model.EventChennal;
import com.example.quanlysukien.model.ReceEvent;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventMe_Fragment extends Fragment {

    RecyclerView recyclerview;
    List<EventChennal> list;
    EvenMeAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_event_me_, container, false);

        recyclerview = view.findViewById(R.id.recyclerview);
        list = new ArrayList<>();
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        recyclerview.setLayoutManager(manager);

//        calAPiData();
        Spinner spinnerFilter = view.findViewById(R.id.spinnerTimeRange);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                getActivity(),
                R.array.filter_options, // Define an array in your strings.xml file with "Tuần" and "Tháng"
                android.R.layout.simple_spinner_item
        );

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        spinnerFilter.setAdapter(adapter);

        // Set the listener for item selections
        spinnerFilter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // Handle the selected item (e.g., filter events based on selected option)
                String selectedOption = parentView.getItemAtPosition(position).toString();
                // Gọi API tương ứng dựa trên lựa chọn
                if ("ALL".equals(selectedOption)){
                    calAPiData();
                }
                else if ("Week".equals(selectedOption)) {
                    // Gọi API cho Tuần
                    callWeekApi();
                } else if ("Month".equals(selectedOption)) {
                    // Gọi API cho Tháng
                    callMonthApi();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Do nothing here
            }
        });

        return view;
    }
    private void callWeekApi() {
        // TODO: Gọi API cho Tuần
        // Ví dụ:
        Toast.makeText(getActivity(), "Gọi API cho Tuần", Toast.LENGTH_SHORT).show();
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyUser", Context.MODE_PRIVATE);
        String userId = sharedPreferences.getString("_id",null);
        API.api.getEventweek(userId).enqueue(new Callback<List<EventChennal>>() {
            @Override
            public void onResponse(Call<List<EventChennal>> call, Response<List<EventChennal>> response) {
                if (response.isSuccessful()) {
                    list = response.body();
                    adapter = new EvenMeAdapter(list, getContext());
                    recyclerview.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<EventChennal>> call, Throwable t) {
                Toast.makeText(getActivity(), "Fail", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void callMonthApi() {
        // TODO: Gọi API cho Tháng
        // Ví dụ:
        Toast.makeText(getActivity(), "Gọi API cho Tháng", Toast.LENGTH_SHORT).show();
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyUser", Context.MODE_PRIVATE);
        String userId = sharedPreferences.getString("_id",null);
        API.api.getEventmonth(userId).enqueue(new Callback<List<EventChennal>>() {
            @Override
            public void onResponse(Call<List<EventChennal>> call, Response<List<EventChennal>> response) {
                if (response.isSuccessful()) {
                    list = response.body();
                    adapter = new EvenMeAdapter(list, getContext());
                    recyclerview.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<EventChennal>> call, Throwable t) {
                Toast.makeText(getActivity(), "Fail", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void calAPiData() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyUser", Context.MODE_PRIVATE);
        String userId = sharedPreferences.getString("_id",null);
        API.api.getDataMe(userId).enqueue(new Callback<ReceEvent>() {
            @Override
            public void onResponse(Call<ReceEvent> call, Response<ReceEvent> response) {
                if (response.isSuccessful()) {
                    list = response.body().getData();
                    adapter = new EvenMeAdapter(list, getContext());
                    recyclerview.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<ReceEvent> call, Throwable t) {

            }
        });
    }
}