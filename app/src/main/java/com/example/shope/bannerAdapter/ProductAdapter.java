package com.example.shope.bannerAdapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shope.DetailProductActivity;
import com.example.shope.R;
import com.example.shope.model.Product;
import com.example.shope.onClick.ItemClickListener;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductHolder>{
    List<Product> arrproduct;
    int layout;
    Context context;

    public ProductAdapter(List<Product> arrproduct, int layout, Context context) {
        this.arrproduct = arrproduct;
        this.layout = layout;
        this.context = context;
    }

    @NonNull
    @Override
    public ProductHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(layout, parent, false);
        return new ProductHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductHolder holder, int position) {
        Product product = arrproduct.get(position);
        holder.content.setText(product.getName());
        holder.soled.setText("Đã bán: "+product.getSold());
        holder.rate.setText(product.getRating()+"");
        Picasso.get().load(product.getImagePreview())
                        .placeholder(R.drawable.dep2)
                        .into(holder.productimg);
        DecimalFormat format = new DecimalFormat("###,###,###");
        holder.price.setText("đ "+format.format(product.getPrice().get$numberDecimal()));
        holder.productimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailProductActivity.class);
                intent.putExtra("data", product);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrproduct.size();
    }

    class ProductHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView productimg;
        TextView sale, price, soled, content, rate;
        ItemClickListener clickListener;
        public ProductHolder(@NonNull View itemView) {
            super(itemView);
            productimg = itemView.findViewById(R.id.image);
            rate = itemView.findViewById(R.id.rate);
            //sale = itemView.findViewById(R.id.sale);
            price = itemView.findViewById(R.id.gia);
            soled = itemView.findViewById(R.id.ban);
            content = itemView.findViewById(R.id.descrip);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            clickListener.setOnItemClick(v, getAdapterPosition(), false);
        }
    }
}
