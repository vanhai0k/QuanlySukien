package com.example.quanlysukien.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.quanlysukien.API.URL;
import com.example.quanlysukien.R;
import com.example.quanlysukien.model.MessageChat;

import java.util.List;

public class MessageChatUserAdapter extends RecyclerView.Adapter<MessageChatUserAdapter.ViewHolder>{

    Context context;
    List<MessageChat> list;

    public MessageChatUserAdapter(Context context, List<MessageChat> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.itean_chat, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MessageChat chat = list.get(position);

        holder.time.setText(chat.getTime());

        if (chat.getMessage().equals("")){
            holder.messageTextView.setVisibility(View.GONE);
        }else{
            holder.messageTextView.setText(chat.getMessage());
        }

        if (chat.getImage() == null || chat.getImage().equals("")) {
            holder.imageChat.setVisibility(View.GONE);
        }else{
            Glide.with(context).load(URL.image + chat.getImage()).into(holder.imageChat);
            holder.imageChat.setVisibility(View.VISIBLE);
            Log.d("mmm", "onBindViewHolder: "+ chat.getImage());
        }

        SharedPreferences sharedPreferences = context.getSharedPreferences("MyUser", context.MODE_PRIVATE);
        String idUser = sharedPreferences.getString("_id",null);
        if (chat.getUserId() != null && chat.getUserId().equals(idUser)) {
            holder.linner.setGravity(Gravity.END); // Hiển thị bên phải
            holder.time.setGravity(Gravity.END); // Hiển thị bên phải
            holder.messageTextView.setTextColor(Color.WHITE); // Màu chữ trắng
            holder.messageTextView.setBackgroundColor(Color.BLUE);
            holder.messageTextView.setBackgroundResource(R.drawable.border_chat);
        }else{
            holder.linner.setGravity(Gravity.START); // Hiển thị bên trái
            holder.time.setGravity(Gravity.START); // Hiển thị bên trái
            holder.messageTextView.setTextColor(Color.BLACK);
            holder.image.setVisibility(View.VISIBLE);
            holder.messageTextView.setBackgroundResource(R.drawable.border_chatu);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView messageTextView,time;
        LinearLayout linner;
        ImageView image,imageChat;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            messageTextView = itemView.findViewById(R.id.messageTextView);
            linner = itemView.findViewById(R.id.linner);
            image = itemView.findViewById(R.id.image);
            time = itemView.findViewById(R.id.time);
            imageChat = itemView.findViewById(R.id.imageChat);
        }
    }
}
