package com.example.shope.bannerAdapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shope.ProfileActivity;
import com.example.shope.R;
import com.example.shope.UpdateAddressActivity;
import com.example.shope.model.Address;
import com.example.shope.onClick.ItemClickListener;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.AddressHolder>{
    int layout;
    ProfileActivity context;
    List<Address> arrAddress;

    public AddressAdapter(int layout, ProfileActivity context, List<Address> arrAddress) {
        this.layout = layout;
        this.context = context;
        this.arrAddress = arrAddress;
    }

    @NonNull
    @Override
    public AddressHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(layout, parent, false);
        return new AddressHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AddressHolder holder, int position) {
        Address address = arrAddress.get(position);
        holder.name.setText(address.getDisplayName());
        holder.phone.setText(address.getPhone());
        holder.addressDetail.setText(address.getExactAddress());
        holder.address.setText(address.getWard()+", "+address.getDistrict()+", "+address.getProvince());
        holder.update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UpdateAddressActivity.class);
                intent.putExtra("data", address);
                context.startActivity(intent);
            }
        });
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "Bạn muốn xóa địa chỉ này?",3000)
                                .setAction("Có", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        context.deleteAddress(address.get_id());
                                    }
                                }).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrAddress.size();
    }

    class AddressHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView name, phone, addressDetail, address, update, delete;
        ItemClickListener clickListener;
        public AddressHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            phone = itemView.findViewById(R.id.phone);
            address = itemView.findViewById(R.id.address);
            addressDetail = itemView.findViewById(R.id.addressDetail);
            update = itemView.findViewById(R.id.update);
            delete = itemView.findViewById(R.id.delete);
            itemView.setOnClickListener(this);
        }

        public AddressHolder(@NonNull View itemView, ItemClickListener clickListener) {
            super(itemView);
            this.clickListener = clickListener;
        }

        @Override
        public void onClick(View v) {
            clickListener.setOnItemClick(v, getAdapterPosition(), false);
        }
    }
}
