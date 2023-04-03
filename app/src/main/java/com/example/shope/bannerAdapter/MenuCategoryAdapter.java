package com.example.shope.bannerAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shope.R;
import com.example.shope.model.Category;
import com.example.shope.model.MenuCategory;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MenuCategoryAdapter extends BaseAdapter {
    List<MenuCategory> arrmenu;
    int layout;
    Context context;

    public MenuCategoryAdapter(List<MenuCategory> arrmenu, int layout, Context context) {
        this.arrmenu = arrmenu;
        this.layout = layout;
        this.context = context;
    }

    @Override
    public int getCount() {
        return arrmenu.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
    class Holder{
        ImageView image;
        TextView title;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder;
        if(convertView == null){
            holder = new Holder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout, null);
            holder.image = convertView.findViewById(R.id.image);
            holder.title = convertView.findViewById(R.id.title);
            convertView.setTag(holder);
        }else{
            holder = (Holder) convertView.getTag();
        }

        MenuCategory menuCategory = arrmenu.get(position);
        Picasso.get()
                .load(menuCategory.getImage())
                .placeholder(R.drawable.user)
                .into(holder.image);
        holder.title.setText(menuCategory.getName());

        return convertView;
    }
}
