package com.example.shope.bannerAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shope.R;
import com.example.shope.model.Notify;
import com.squareup.picasso.Picasso;

import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationHolder>{

    int layout;
    Context context;
    List<Notify> arrNotify;

    public NotificationAdapter(int layout, Context context, List<Notify> arrNotify) {
        this.layout = layout;
        this.context = context;
        this.arrNotify = arrNotify;
    }

    @NonNull
    @Override
    public NotificationHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(layout, parent, false);
        return new NotificationHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationHolder holder, int position) {
        Notify notify = arrNotify.get(position);
        holder.title.setText(notify.getTitle());
        holder.description.setText(notify.getContent());
        Picasso.get().load(notify.getImage())
                .placeholder(R.drawable.dep2)
                .into(holder.img);
    }

    @Override
    public int getItemCount() {
        return arrNotify.size();
    }

    class NotificationHolder extends RecyclerView.ViewHolder{
        TextView title, description;
        ImageView img;
        public NotificationHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            description = itemView.findViewById(R.id.descrip);
            img = itemView.findViewById(R.id.image);
        }
    }
}
