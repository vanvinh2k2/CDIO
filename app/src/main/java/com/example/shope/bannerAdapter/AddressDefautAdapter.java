package com.example.shope.bannerAdapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shope.DefautAddressActivity;
import com.example.shope.R;
import com.example.shope.model.Address;

import java.util.List;

public class AddressDefautAdapter extends RecyclerView.Adapter<AddressDefautAdapter.AddressDefautHolder>{
    int layout;
    DefautAddressActivity context;
    List<Address> arrAddress;
    int selectedItemPosition = -1;

    public AddressDefautAdapter(int layout, DefautAddressActivity context, List<Address> arrAddress) {
        this.layout = layout;
        this.context = context;
        this.arrAddress = arrAddress;
    }

    @NonNull
    @Override
    public AddressDefautHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(layout, parent, false);
        return new AddressDefautHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AddressDefautHolder holder, int position) {
        Address address = arrAddress.get(position);
        holder.name.setText(address.getDisplayName());
        holder.phone.setText(address.getPhone());
        holder.addressDetail.setText(address.getExactAddress());
        holder.address.setText(address.getWard()+", "+address.getDistrict()+", "+address.getProvince());
        if (position == selectedItemPosition) {
            holder.check.setChecked(true);
            context.idAddress = address.get_id();
        } else {
            holder.check.setChecked(false);
        }
    }

    @Override
    public int getItemCount() {
        return arrAddress.size();
    }

    class AddressDefautHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView name, phone, addressDetail, address;
        CheckBox check;
        public AddressDefautHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            phone = itemView.findViewById(R.id.phone);
            address = itemView.findViewById(R.id.address);
            addressDetail = itemView.findViewById(R.id.addressDetail);
            check = itemView.findViewById(R.id.check);
            check.setOnClickListener(this);
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
