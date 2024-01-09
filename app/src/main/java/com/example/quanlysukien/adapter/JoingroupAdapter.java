package com.example.quanlysukien.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.quanlysukien.API.API_Group;
import com.example.quanlysukien.API.URL;
import com.example.quanlysukien.R;
import com.example.quanlysukien.model.Group;
import com.example.quanlysukien.model.JoinGroup;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JoingroupAdapter extends RecyclerView.Adapter<JoingroupAdapter.ViewHolder>{
    Context context;
    List<JoinGroup> list;

    public JoingroupAdapter(Context context, List<JoinGroup> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.iteam_joingroup, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        JoinGroup joinGroup = list.get(position);

        holder.username.setText(joinGroup.getUserID().getUsername());

        Glide.with(context).load(URL.image + joinGroup.getUserID().getImage()).into(holder.image);
        holder.content.setText(joinGroup.getContent());

        holder.joingroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder =new AlertDialog.Builder(v.getRootView().getContext());
                builder.setTitle("Confirm users " + joinGroup.getUserID().getUsername()+ " join the group");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SharedPreferences sharedPreferences = context.getSharedPreferences("group", Context.MODE_PRIVATE);
                        String idgroup = sharedPreferences.getString("id_group",null);
                        String newuserID = joinGroup.getUserID().getId();
                        String status = "true";
                        String content = joinGroup.getContent();
                        Group joinGroupUpdate = new Group(newuserID,idgroup,status,content);
                        API_Group.apiGroup.updateJoinGroup(joinGroupUpdate).enqueue(new Callback<Group>() {
                            @Override
                            public void onResponse(Call<Group> call, Response<Group> response) {
                                if (response.isSuccessful()){
                                    Toast.makeText(context, "Successful", Toast.LENGTH_SHORT).show();
                                    list.remove(joinGroup);
                                    notifyDataSetChanged();
                                    dialog.dismiss();
                                }
                            }

                            @Override
                            public void onFailure(Call<Group> call, Throwable t) {

                            }
                        });
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView username,content;
        ImageView cancel,joingroup,image;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            username = itemView.findViewById(R.id.username);
            cancel = itemView.findViewById(R.id.cancel);
            joingroup = itemView.findViewById(R.id.joingroup);
            image = itemView.findViewById(R.id.image);
            content = itemView.findViewById(R.id.content);
        }
    }
}
