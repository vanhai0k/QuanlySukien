package com.example.quanlysukien.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.quanlysukien.API.URL;
import com.example.quanlysukien.R;
import com.example.quanlysukien.model.UserEvent;

import java.util.List;

public class UserCountEvent extends RecyclerView.Adapter<UserCountEvent.ViewHolder>{

    List<UserEvent> list;
    Context context;

    public UserCountEvent(List<UserEvent> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.iteam_user_count,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        UserEvent userEvent = list.get(position);

        holder.tv_username.setText(userEvent.getUsername());
        holder.tv_email.setText(userEvent.getEmail());

        Glide.with(context).load(URL.image + userEvent.getImage()).into(holder.im_image);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView im_image;
        TextView tv_username,tv_email;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            im_image = itemView.findViewById(R.id.im_image);
            tv_username = itemView.findViewById(R.id.tv_username);
            tv_email = itemView.findViewById(R.id.tv_email);
        }
    }
}
