package com.example.shope.bannerAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shope.DetailProductActivity;
import com.example.shope.R;
import com.example.shope.onClick.ItemClickListener;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageHolder>{
    int layout;
    DetailProductActivity context;
    List<String> arrImage;

    public ImageAdapter(int layout, DetailProductActivity context, List<String> arrImage) {
        this.layout = layout;
        this.context = context;
        this.arrImage = arrImage;
    }

    @NonNull
    @Override
    public ImageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(layout, parent, false);
        return new ImageHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageHolder holder, int position) {
        String imgstr = arrImage.get(position);
        Picasso.get().load(imgstr)
                .placeholder(R.drawable.dep2)
                .into(holder.img);
        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Picasso.get().load(imgstr)
                        .placeholder(R.drawable.dep2)
                        .into(context.productimg);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrImage.size();
    }

    class ImageHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView img;
        ItemClickListener clickListener;
        public ImageHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img);
        }

        public ImageHolder(@NonNull View itemView, ItemClickListener clickListener) {
            super(itemView);
            this.clickListener = clickListener;
        }

        @Override
        public void onClick(View v) {
            clickListener.setOnItemClick(v, getAdapterPosition(), false);
        }
    }
}
