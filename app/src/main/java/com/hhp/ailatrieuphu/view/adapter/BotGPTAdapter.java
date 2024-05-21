package com.hhp.ailatrieuphu.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hhp.ailatrieuphu.R;
import com.hhp.ailatrieuphu.database.entity.Message;

import java.util.ArrayList;
import java.util.List;

public class BotGPTAdapter extends RecyclerView.Adapter<BotGPTAdapter.BotGPTHolder> {
    private Context context;
    private List<Message> listMessage = new ArrayList<>();

    public BotGPTAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public BotGPTHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_message, parent, false);
        return new BotGPTHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BotGPTHolder holder, int position) {
        Message message = listMessage.get(position);
        if(message.isReq()){
            holder.tvRequest.setVisibility(View.VISIBLE);
            holder.trResponse.setVisibility(View.GONE);
            holder.tvRequest.setText(message.getText());
        } else {
            holder.tvRequest.setVisibility(View.GONE);
            holder.trResponse.setVisibility(View.VISIBLE);
            holder.tvResponse.setText(message.getText());
        }
    }

    @Override
    public int getItemCount() {
        return listMessage.size();
    }

    public void sendMessage(Message message) {
        int position = getItemCount();
        listMessage.add(message);
        notifyItemRangeInserted(position, listMessage.size());
    }

    public void removeLastMessage() {
        int position = getItemCount() - 1;
        listMessage.remove(position);
        notifyItemRemoved(position);

    }

    public static class BotGPTHolder extends RecyclerView.ViewHolder{
        public TableRow trResponse;
        public TextView tvResponse, tvRequest;
        public ImageView ivCaller;
        public BotGPTHolder(@NonNull View itemView) {
            super(itemView);
            trResponse = itemView.findViewById(R.id.trResponse);
            tvResponse = itemView.findViewById(R.id.tvResponse);
            tvRequest = itemView.findViewById(R.id.tvRequest);
            ivCaller = itemView.findViewById(R.id.ivCaller);
        }
    }
}
