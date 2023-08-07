package com.example.shope.bannerAdapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shope.DetailOrderActivity;
import com.example.shope.ProfileActivity;
import com.example.shope.R;
import com.example.shope.model.Address;
import com.example.shope.model.GetOrder;
import com.example.shope.onClick.ItemClickListener;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderHolder>{
    int layout;
    Context context;
    List<GetOrder> arrOrder;

    public OrderAdapter(int layout, Context context, List<GetOrder> arrOrder) {
        this.layout = layout;
        this.context = context;
        this.arrOrder = arrOrder;
    }

    @NonNull
    @Override
    public OrderHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(layout, parent, false);
        return new OrderHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderHolder holder, int position) {
        GetOrder getOrder = arrOrder.get(position);
        holder.shope.setText(getOrder.getStoreId().getName());
        holder.status.setText("Chờ xác nhận");
        holder.product.setText(getOrder.getProductId().getName());
        DecimalFormat format = new DecimalFormat("###,###,###");
        holder.price.setText("Giá: "+ format.format(getOrder.getTotalPrice()/getOrder.getQuantity())+"đ");
        holder.sumprice.setText("Tổng thanh toán: "+format.format(getOrder.getTotalPrice()+getOrder.getShippingPrice())+"đ");
        holder.quantity.setText("x"+getOrder.getQuantity());
        if(getOrder.getOptionStyle().getOption1().compareTo("A")==0 || getOrder.getOptionStyle().getOption1().compareTo("")==0){
            holder.category.setVisibility(View.GONE);
        }else {
            holder.category.setVisibility(View.VISIBLE);
            holder.category.setText(getOrder.getOptionStyle().getOption1()+" - "+ getOrder.getOptionStyle().getOption2());
        }
        Picasso.get().load(getOrder.getProductId().getImagePreview())
                .placeholder(R.drawable.dep2)
                .into(holder.img);
        holder.setClickListener(new ItemClickListener() {
            @Override
            public void setOnItemClick(View view, int pos, boolean isLongClick) {
                Intent intent = new Intent(context, DetailOrderActivity.class);
                intent.putExtra("data", getOrder);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrOrder.size();
    }

    class OrderHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView shope, status, product, category, price, sumprice, quantity;
        ImageView img;
        ItemClickListener clickListener;
        public OrderHolder(@NonNull View itemView) {
            super(itemView);
            shope = itemView.findViewById(R.id.shop);
            status = itemView.findViewById(R.id.status);
            product = itemView.findViewById(R.id.tenSp);
            category = itemView.findViewById(R.id.loaiSp);
            price = itemView.findViewById(R.id.giaSp);
            quantity = itemView.findViewById(R.id.sl);
            sumprice = itemView.findViewById(R.id.tien);
            img = itemView.findViewById(R.id.anhSp);
            itemView.setOnClickListener(this);
        }

        public void setClickListener(ItemClickListener clickListener) {
            this.clickListener = clickListener;
        }

        @Override
        public void onClick(View v) {
            clickListener.setOnItemClick(v, getAdapterPosition(), false);
        }
    }
}
