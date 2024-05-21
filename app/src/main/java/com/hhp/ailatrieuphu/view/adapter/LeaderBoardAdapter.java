package com.hhp.ailatrieuphu.view.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.hhp.ailatrieuphu.R;
import com.hhp.ailatrieuphu.database.entity.Message;
import com.hhp.ailatrieuphu.database.entity.User;

import java.util.ArrayList;
import java.util.List;

public class LeaderBoardAdapter extends RecyclerView.Adapter<LeaderBoardAdapter.BotGPTHolder> {
    private static final String TAG = LeaderBoardAdapter.class.getName();
    private Context context;

    private List<User> listUser;

    public LeaderBoardAdapter(Context context, List<User> listUser) {
        this.listUser = listUser;
        this.context = context;
    }

    public void addUser(User user) {
        int position = getItemCount();
        listUser.add(0, user);
        notifyItemRangeInserted(position,listUser.size());
    }

    @NonNull
    @Override
    public BotGPTHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_user_score, parent, false);
        return new BotGPTHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BotGPTHolder holder, int position) {
        User user = listUser.get(position);
        holder.tvStt.setText(String.valueOf(position + 1));
        holder.tvName.setText(user.getName());
        holder.tvName.setSelected(true);
        holder.tvScore.setText(String.valueOf(user.getBestScore()));
        if(user.getAvatar() != null){
            Glide.with(context).load(user.getAvatar()).centerCrop().into(holder.ivAvatar);
        }
    }

    @Override
    public int getItemCount() {
        return listUser.size();
    }

    public void removeUser() {
        int position = getItemCount() - 1;
        listUser.remove(position);
        notifyItemRemoved(position);
    }

    public static class BotGPTHolder extends RecyclerView.ViewHolder{
        public TextView tvName, tvScore, tvStt;
        public ImageView ivAvatar;
        public BotGPTHolder(@NonNull View itemView) {
            super(itemView);
            tvStt = itemView.findViewById(R.id.tvStt);
            tvName = itemView.findViewById(R.id.tvUsername);
            tvScore = itemView.findViewById(R.id.tvScore);
            ivAvatar = itemView.findViewById(R.id.ivAvatar);
        }
    }
}
