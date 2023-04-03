package com.example.shope.bannerAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shope.R;
import com.example.shope.model.Category;
import com.example.shope.onClick.ItemClickListener;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryHolder>{
    List<Category> arrcategory;
    int layout;
    Context context;

    public CategoryAdapter(List<Category> arrcategory, int layout, Context context) {
        this.arrcategory = arrcategory;
        this.layout = layout;
        this.context = context;
    }

    @NonNull
    @Override
    public CategoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(layout, parent, false);
        return new CategoryHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryHolder holder, int position) {
        Category category = arrcategory.get(position);
        Picasso.get().load(category.getImage()).placeholder(R.drawable.dep2).into(holder.image);
        holder.title.setText(category.getName());
        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, category.getName()+"", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrcategory.size();
    }

    class CategoryHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView image;
        TextView title;
        ItemClickListener clickListener;
        public CategoryHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            title = itemView.findViewById(R.id.title);
            itemView.setOnClickListener(this);
        }

        public CategoryHolder(@NonNull View itemView, ItemClickListener clickListener) {
            super(itemView);
            this.clickListener = clickListener;
        }

        @Override
        public void onClick(View v) {
            clickListener.setOnItemClick(v, getAdapterPosition(), false);
        }
    }
}
