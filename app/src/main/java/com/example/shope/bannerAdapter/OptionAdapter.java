package com.example.shope.bannerAdapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shope.DetailProductActivity;
import com.example.shope.R;
import com.example.shope.onClick.ItemClickListener;

import java.util.List;

public class OptionAdapter extends RecyclerView.Adapter<OptionAdapter.OptionHolder>{
    int layout;
    DetailProductActivity context;
    List<String> arrOption;
    int selectedItemPosition = -1;

    public OptionAdapter(int layout, DetailProductActivity context, List<String> arrOption) {
        this.layout = layout;
        this.context = context;
        this.arrOption = arrOption;
    }

    @NonNull
    @Override
    public OptionHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(layout, parent, false);
        return new OptionHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OptionHolder holder, int position) {
        String option = arrOption.get(position);
        holder.optiontxt.setText(option);
        /*holder.optiontxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {*/
                if (position == selectedItemPosition) {
                    Drawable drawable = context.getDrawable(R.drawable.edit_category2);
                    holder.optiontxt.setBackground(drawable);
                    context.getStyle = holder.optiontxt.getText().toString();
                    //Toast.makeText(context, option+"", Toast.LENGTH_SHORT).show();
                } else {
                    Drawable drawable = context.getDrawable(R.drawable.edit_category);
                    holder.optiontxt.setBackground(drawable);
                }
            /*}
        });*/
    }

    @Override
    public int getItemCount() {
        return arrOption.size();
    }

    class OptionHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView optiontxt;
        ItemClickListener clickListener;
        public OptionHolder(@NonNull View itemView) {
            super(itemView);
            optiontxt = itemView.findViewById(R.id.style);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            //clickListener.setOnItemClick(v, getAdapterPosition(), false);
            int clickedPosition = getAdapterPosition();
            if (clickedPosition != RecyclerView.NO_POSITION) {
                selectedItemPosition = clickedPosition;
                notifyDataSetChanged();
            }
        }
    }
}
