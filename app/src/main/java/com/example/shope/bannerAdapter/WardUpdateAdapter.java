package com.example.shope.bannerAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.shope.AddressActivity;
import com.example.shope.R;
import com.example.shope.UpdateAddressActivity;
import com.example.shope.model.Ward;

import java.util.List;

public class WardUpdateAdapter extends BaseAdapter {
    UpdateAddressActivity context;
    List<Ward> list;
    int layout;

    public WardUpdateAdapter(UpdateAddressActivity context, List<Ward> list, int layout) {
        this.context = context;
        this.list = list;
        this.layout = layout;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(layout,null);
        TextView seleted = convertView.findViewById(R.id.addresslist);
        Ward category = list.get(position);
        context.wardtxt = category.getWard_name();
        seleted.setText(category.getWard_name());
        return convertView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.address_item,null);
        TextView tvCategory = convertView.findViewById(R.id.itemaddress);
        Ward category = list.get(position);
        tvCategory.setText(category.getWard_name());
        return convertView;
    }
}
