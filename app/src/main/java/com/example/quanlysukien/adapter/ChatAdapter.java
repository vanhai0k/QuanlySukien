package com.example.quanlysukien.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Message;
import android.text.Layout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlysukien.QuanLyEvent.Chat_Event;
import com.example.quanlysukien.R;
import com.example.quanlysukien.model.ChatModel;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder>{

    List<ChatModel> list;
    Context context;

    public ChatAdapter(List<ChatModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itean_chat,
                parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ChatModel message = list.get(position);

        holder.messageTextView.setText(message.getContent());

        SharedPreferences sharedPreferences = context.getSharedPreferences("MyUser", context.MODE_PRIVATE);
        String idUser = sharedPreferences.getString("_id",null);
        if (message.getUser_id().equals(idUser)){
            holder.linner.setGravity(Gravity.END); // Hiển thị bên phải
            holder.messageTextView.setTextColor(Color.WHITE); // Màu chữ trắng
            holder.messageTextView.setBackgroundColor(Color.BLUE);
            holder.messageTextView.setBackgroundResource(R.drawable.border_chat);
        }else{
            holder.linner.setGravity(Gravity.START); // Hiển thị bên trái
            holder.messageTextView.setTextColor(Color.BLACK);
            holder.image.setVisibility(View.VISIBLE);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "id: " + message.getUser_id(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView messageTextView;
        LinearLayout linner;
        ImageView image;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            messageTextView = itemView.findViewById(R.id.messageTextView);
            linner = itemView.findViewById(R.id.linner);
            image = itemView.findViewById(R.id.image);
        }
    }
}
