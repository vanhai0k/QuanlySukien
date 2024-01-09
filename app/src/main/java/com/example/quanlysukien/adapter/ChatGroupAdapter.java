package com.example.quanlysukien.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.example.quanlysukien.model.ChatGroup;

import java.util.List;

public class ChatGroupAdapter extends RecyclerView.Adapter<ChatGroupAdapter.ViewHolder>{

    Context context;
    List<ChatGroup> list;

    public ChatGroupAdapter(Context context, List<ChatGroup> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.iteam_chat_group, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ChatGroup chatGroup = list.get(position);

        holder.name.setText(chatGroup.getName());
        String imageUrl = URL.image + chatGroup.getImage();
        Glide.with(context).load(imageUrl).into(holder.image);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, com.example.quanlysukien.QuanLyEvent.ChatGroup.class);

                intent.putExtra("id_group", chatGroup.getId());
                intent.putExtra("image", chatGroup.getImage());
                intent.putExtra("nameGroup", chatGroup.getName());

                SharedPreferences sharedPreferences = context.getSharedPreferences("group", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("id_group", chatGroup.getId());
                editor.apply();

                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        ImageView image;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            image = itemView.findViewById(R.id.image);
        }
    }
}
