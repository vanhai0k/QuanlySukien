package com.example.quanlysukien.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.quanlysukien.API.URL;
import com.example.quanlysukien.QuanLyEvent.Infomation_Event;
import com.example.quanlysukien.R;
import com.example.quanlysukien.model.EventChennal;

import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventHolder>{
    List<EventChennal> list;
    Context context;
    public EventAdapter(List<EventChennal> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public EventHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.iteam_event, parent,false);
        return new EventHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventHolder holder, int position) {
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

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Infomation_Event.class);

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
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class EventHolder extends RecyclerView.ViewHolder {
        TextView eventName,startDate,endDate,startTime,endTime,location;
        ImageView image;
        public EventHolder(@NonNull View itemView) {
            super(itemView);

            eventName = itemView.findViewById(R.id.eventName);
            startDate = itemView.findViewById(R.id.startDate);
            endDate = itemView.findViewById(R.id.dateEnd);
            startTime = itemView.findViewById(R.id.startTime);
            endTime = itemView.findViewById(R.id.endTime);
            image = itemView.findViewById(R.id.image);
            location = itemView.findViewById(R.id.location);
        }
    }
}
