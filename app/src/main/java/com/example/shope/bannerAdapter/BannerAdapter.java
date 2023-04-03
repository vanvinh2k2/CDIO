package com.example.shope.bannerAdapter;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shope.R;
import com.example.shope.model.BannerCategory;

import java.util.List;

public class BannerAdapter extends RecyclerView.Adapter<BannerAdapter.BannerHolder>{
    List<BannerCategory> arrbanner;
    int layout;
    Context context;

    public BannerAdapter(List<BannerCategory> arrbanner, int layout, Context context) {
        this.arrbanner = arrbanner;
        this.layout = layout;
        this.context = context;
    }

    @NonNull
    @Override
    public BannerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(layout, parent, false);
        return new BannerHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BannerHolder holder, int position) {
        BannerCategory banner = arrbanner.get(position);
        holder.image.setImageResource(banner.getImage());
        holder.descrip.setText(banner.getDescription());
        holder.title.setText(banner.getTitle());
    }

    @Override
    public int getItemCount() {
        return arrbanner.size();
    }

    class BannerHolder extends RecyclerView.ViewHolder{
        ImageView image;
        TextView title, descrip;
        public BannerHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            title = itemView.findViewById(R.id.title);
            descrip = itemView.findViewById(R.id.descrip);
        }
    }
}
