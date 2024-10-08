package com.towhid.androidappanimation2.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.towhid.androidappanimation2.R;
import com.towhid.androidappanimation2.model.NotificationModel;

import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.viewHolder> {

    private List<NotificationModel> notificationList;
    private Context context;

    public NotificationAdapter(List<NotificationModel> notificationList, Context context) {
        this.notificationList = notificationList;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.notification_card, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        NotificationModel notification = notificationList.get(position);
        holder.textDate.setText(notification.getDate());
        holder.textNews.setText(notification.getNews());
    }

    @Override
    public int getItemCount() {
        return notificationList.size();
    }

    public static class viewHolder extends RecyclerView.ViewHolder {
        TextView textDate;
        TextView textNews;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            textDate = itemView.findViewById(R.id.noticeDate);
            textNews = itemView.findViewById(R.id.noticeNews);
        }
    }
}
