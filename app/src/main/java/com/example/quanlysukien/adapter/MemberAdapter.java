package com.example.quanlysukien.adapter;

import android.content.Context;
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
import com.example.quanlysukien.model.UserMember;

import java.util.List;

public class MemberAdapter extends RecyclerView.Adapter<MemberAdapter.ViewHolder>{
    Context context;
    List<UserMember> list;

    public MemberAdapter(Context context, List<UserMember> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.iteam_user_member,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        UserMember member = list.get(position);
        holder.tv_username.setText(member.getUsername());

        Glide.with(context).load(URL.image + member.getImage()).into(holder.im_image);
        SharedPreferences sharedPreferences = context.getSharedPreferences("MyUser", Context.MODE_PRIVATE);
        String id = sharedPreferences.getString("_id",null);
        String user_id = member.getAdmin();
        if (user_id.equals(id)){
            holder.kickUser.setVisibility(View.VISIBLE);
        }else{
            holder.kickUser.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView im_image,kickUser;
        TextView tv_username,role;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            im_image = itemView.findViewById(R.id.image);
            tv_username = itemView.findViewById(R.id.username);
            role = itemView.findViewById(R.id.role);
            kickUser = itemView.findViewById(R.id.kickUser);
        }
    }
}
