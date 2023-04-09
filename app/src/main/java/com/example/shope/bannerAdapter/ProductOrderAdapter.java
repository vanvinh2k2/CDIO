package com.example.shope.bannerAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shope.ProfileActivity;
import com.example.shope.R;
import com.example.shope.model.Address;
import com.example.shope.model.Cart;
import com.example.shope.model.Product;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

public class ProductOrderAdapter extends RecyclerView.Adapter<ProductOrderAdapter.ProductOrderHolder>{
    int layout;
    Context context;
    List<Cart> arrProduct;

    public ProductOrderAdapter(int layout, Context context, List<Cart> arrProduct) {
        this.layout = layout;
        this.context = context;
        this.arrProduct = arrProduct;
    }

    @NonNull
    @Override
    public ProductOrderHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(layout, parent, false);
        return new ProductOrderHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductOrderHolder holder, int position) {
        Cart product = arrProduct.get(position);
        //holder.shop.setText(product.getStoreId().getName());
        holder.name.setText(product.getProductId().getName());
        if(product.getOptionStyle().getOption1().compareTo("A")!=0)
            holder.category.setText("Phân loại: "+product.getOptionStyle().getOption1()+", "+product.getOptionStyle().getOption2());
        else holder.category.setVisibility(View.GONE);
        DecimalFormat format = new DecimalFormat("###,###,###");
        holder.price.setText(format.format(product.getPrice())+"đ");
        holder.quantity.setText("x"+product.getQuantity());
        Picasso.get().load(product.getProductId().getImagePreview()).placeholder(R.drawable.dep2).into(holder.img);
    }

    @Override
    public int getItemCount() {
        return arrProduct.size();
    }

    class ProductOrderHolder extends RecyclerView.ViewHolder{
        TextView shop, name, price, quantity, category;
        ImageView img;

        public ProductOrderHolder(@NonNull View itemView) {
            super(itemView);
            //shop = itemView.findViewById(R.id.shop);
            name = itemView.findViewById(R.id.tenSp);
            price = itemView.findViewById(R.id.giaSp);
            quantity = itemView.findViewById(R.id.sl);
            category = itemView.findViewById(R.id.loaiSp);
            img = itemView.findViewById(R.id.anhSp);
        }
    }
}
