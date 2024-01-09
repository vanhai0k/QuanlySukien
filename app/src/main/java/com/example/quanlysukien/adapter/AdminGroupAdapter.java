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
import com.example.quanlysukien.model.UserMember;

import java.util.List;

public class AdminGroupAdapter extends RecyclerView.Adapter<AdminGroupAdapter.ViewHolder>{

    Context context;
    List<UserMember> list;

    public AdminGroupAdapter(Context context, List<UserMember> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.iteam_admingroup,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        UserMember member = list.get(position);
        holder.tv_username.setText(member.getUsername());

        Glide.with(context).load(URL.image + member.getImage()).into(holder.im_image);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView im_image;
        TextView tv_username,role;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            im_image = itemView.findViewById(R.id.image);
            tv_username = itemView.findViewById(R.id.username);
            role = itemView.findViewById(R.id.role);
        }
    }
}
