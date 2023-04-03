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

public class StyleAdapter extends RecyclerView.Adapter<StyleAdapter.StyleHolder> {
    int layout;
    DetailProductActivity context;
    List<String> arrStyle;
    int selectedItemPosition = -1;

    public StyleAdapter(int layout, DetailProductActivity context, List<String> arrStyle) {
        this.layout = layout;
        this.context = context;
        this.arrStyle = arrStyle;
    }

    @NonNull
    @Override
    public StyleHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(layout, parent, false);
        return new StyleHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StyleHolder holder, int position) {
        String option = arrStyle.get(position);
        holder.style.setText(option);
        if (position == selectedItemPosition) {
            Drawable drawable = context.getDrawable(R.drawable.edit_category2);
            holder.style.setBackground(drawable);
            context.getSize = holder.style.getText().toString();
            //Toast.makeText(context, option+"", Toast.LENGTH_SHORT).show();
        } else {
            Drawable drawable = context.getDrawable(R.drawable.edit_category);
            holder.style.setBackground(drawable);
        }
    }

    @Override
    public int getItemCount() {
        return arrStyle.size();
    }

    class StyleHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView style;
        ItemClickListener clickListener;
        public StyleHolder(@NonNull View itemView) {
            super(itemView);
            style = itemView.findViewById(R.id.style);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            if (clickedPosition != RecyclerView.NO_POSITION) {
                selectedItemPosition = clickedPosition;
                notifyDataSetChanged();
            }
        }
    }
}
