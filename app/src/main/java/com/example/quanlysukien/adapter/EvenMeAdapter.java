package com.example.quanlysukien.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.quanlysukien.API.URL;
import com.example.quanlysukien.QuanLyEvent.Add_GroupEvent;
import com.example.quanlysukien.QuanLyEvent.Update_Event_Activity;
import com.example.quanlysukien.R;
import com.example.quanlysukien.model.EventChennal;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class EvenMeAdapter extends RecyclerView.Adapter<EvenMeAdapter.EventMeHolder>{
    List<EventChennal> list;
    Context context;

    public EvenMeAdapter(List<EventChennal> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public EventMeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.iteam_evenme, parent,false);
        return new EventMeHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventMeHolder holder, int position) {
        EventChennal event = list.get(position);

        String eventsName = event.getEventName();

// Check if the length of the product name is greater than 30 characters
        if (eventsName.length() > 40) {
            // If yes, truncate the string to the first 30 characters and append "..."
            eventsName = eventsName.substring(0, 40) + "...";
        }

        holder.eventName.setText(eventsName);

        holder.startDate.setText(event.getCreatedAt());
        holder.endDate.setText(event.getUpdatedAt());
        holder.startTime.setText(event.getStartTime());
        holder.endTime.setText(event.getEndTime());
        holder.location.setText(event.getLocation());

        String imageUrl = URL.image + event.getImage();
        Glide.with(context).load(imageUrl).into(holder.image);

        holder.tv_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Update_Event_Activity.class);

//                SharedPreferences sharedPreferences = context.getSharedPreferences("event", context.MODE_PRIVATE);
//                SharedPreferences.Editor editor = sharedPreferences.edit();
//                editor.putString("id_event", event.getId());
//                editor.apply();

                intent.putExtra("id_event", event.getId());
                intent.putExtra("nameEvent", event.getEventName());
                intent.putExtra("limit", event.getLimit());
                intent.putExtra("timeStart", event.getStartTime());
                intent.putExtra("timeEnd", event.getEndTime());
                intent.putExtra("dateStart", event.getCreatedAt());
                intent.putExtra("dateEnd", event.getUpdatedAt());
                intent.putExtra("location", event.getLocation());
                intent.putExtra("description", event.getDescription());
                intent.putExtra("image", event.getImage());
                intent.putExtra("user_id", event.getUser_id());

                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                URL.url + "getGroup", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String idevent = jsonObject.getString("eventID");

                        if (event.getId().equals(idevent)){
                            holder.tv_addgroup.setVisibility(View.GONE);
                        }else{
                            holder.tv_addgroup.setVisibility(View.VISIBLE);
                            holder.tv_addgroup.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(context, Add_GroupEvent.class);
                                    intent.putExtra("id_event", event.getId());
                                    intent.putExtra("nameEvent", event.getEventName());
                                    intent.putExtra("image", event.getImage());
                                    intent.putExtra("user_id", event.getUser_id());

                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    context.startActivity(intent);
                                }
                            });
                        }
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

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class EventMeHolder extends RecyclerView.ViewHolder {
        TextView eventName,startDate,endDate,startTime,endTime,location,tv_update,tv_addgroup;
        ImageView image;
        public EventMeHolder(@NonNull View itemView) {
            super(itemView);

            eventName = itemView.findViewById(R.id.eventName);
            startDate = itemView.findViewById(R.id.startDate);
            endDate = itemView.findViewById(R.id.dateEnd);
            startTime = itemView.findViewById(R.id.startTime);
            endTime = itemView.findViewById(R.id.endTime);
            image = itemView.findViewById(R.id.image);
            location = itemView.findViewById(R.id.location);
            tv_update = itemView.findViewById(R.id.tv_update);
            tv_addgroup = itemView.findViewById(R.id.tv_addgroup);
        }
    }
}
