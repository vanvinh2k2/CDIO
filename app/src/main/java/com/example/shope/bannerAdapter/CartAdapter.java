package com.example.shope.bannerAdapter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shope.CartActivity;
import com.example.shope.R;
import com.example.shope.event.SumEvent;
import com.example.shope.model.Cart;
import com.example.shope.onClick.ItemChoiceListener;
import com.example.shope.utils.Constant;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;

import java.text.DecimalFormat;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartHolder>{
    List<Cart> arrcart;
    int layout;
    CartActivity context;

    public CartAdapter(List<Cart> arrcart, int layout, CartActivity context) {
        this.arrcart = arrcart;
        this.layout = layout;
        this.context = context;
    }

    @NonNull
    @Override
    public CartHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(layout, parent, false);
        return new CartHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartHolder holder, int position) {
        int vt = position;
        Cart cart = arrcart.get(position);
        Picasso.get().load(cart.getProductId().getImagePreview())
                .placeholder(R.drawable.dep2)
                .into(holder.img);
        holder.name.setText(cart.getProductId().getName());
        if(cart.getOptionStyle().getOption1().compareTo("A")==0) holder.category.setVisibility(View.GONE);
        else holder.category.setText("Loại: "+cart.getOptionStyle().getOption1()+" - "+cart.getOptionStyle().getOption2());
        holder.quantity.setText(cart.getQuantity()+"");
        DecimalFormat format = new DecimalFormat("###,###,###");
        holder.price.setText("Giá: "+format.format(cart.getPrice())+"đ");
        holder.check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    Constant.listProduct.add(cart);
                    EventBus.getDefault().postSticky(new SumEvent());
                }else{
                    for(int i = 0;i<Constant.listProduct.size();i++){
                        if(Constant.listProduct.get(i).get_id().compareTo(cart.get_id())==0){
                            Constant.listProduct.remove(i);
                            EventBus.getDefault().postSticky(new SumEvent());
                        }
                    }
                }
            }
        });
        holder.setChoiceListener(new ItemChoiceListener() {
            @Override
            public void setOnItemClick(View view, int pos, int Click) {
                if(Click == 2){
                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getRootView().getContext());
                    builder.setTitle("Thông báo");
                    builder.setIcon(R.drawable.baseline_notification);
                    builder.setMessage("Bạn muốn xóa sản phẩm này khỏi giỏ hàng?");
                    builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String keyId = Constant.allProduct.get(pos).get_id();
                            context.deleteProduct(keyId);
                            Constant.allProduct.remove(pos);
                            for (int i = 0; i < Constant.listProduct.size(); i++) {
                                if (Constant.listProduct.get(i).get_id().compareTo(keyId) == 0) {
                                    Constant.listProduct.remove(i);
                                }
                            }
                            context.cartAdapter.notifyDataSetChanged();
                            EventBus.getDefault().postSticky(new SumEvent());
                            if(Constant.allProduct.size() == 0) context.cartNull.setVisibility(View.VISIBLE);
                        }
                    });
                    builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    builder.show();
                }

                else{
                    int sl = Integer.parseInt(holder.quantity.getText().toString());
                    if (holder.check.isChecked()) {
                        if (Click == 1) {
                            if (sl > 1) {
                                sl--;
                                for (int i = 0; i < Constant.listProduct.size(); i++) {
                                    if (Constant.listProduct.get(i).get_id().compareTo(Constant.allProduct.get(pos).get_id()) == 0) {
                                        Constant.listProduct.get(i).setQuantity(sl);
                                    }
                                }
                                Constant.allProduct.get(vt).setQuantity(sl);
                                EventBus.getDefault().postSticky(new SumEvent());
                            }
                        } else{
                            sl++;
                            for (int i = 0; i < Constant.listProduct.size(); i++) {
                                if (Constant.listProduct.get(i).get_id().compareTo(Constant.allProduct.get(pos).get_id()) == 0) {
                                    Constant.listProduct.get(i).setQuantity(sl);
                                }
                            }
                            Constant.allProduct.get(vt).setQuantity(++sl);
                            EventBus.getDefault().postSticky(new SumEvent());
                        }
                        holder.quantity.setText(Constant.allProduct.get(pos).getQuantity() + "");

                    }
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return arrcart.size();
    }

    class CartHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView img;
        TextView name, category, price, quantity, giam, tang;
        Button delete;
        CheckBox check;
        ItemChoiceListener clickListener;

        public CartHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.anhSp);
            name = itemView.findViewById(R.id.tenSp);
            category = itemView.findViewById(R.id.loaiSp);
            price = itemView.findViewById(R.id.giaSp);
            giam = itemView.findViewById(R.id.giam);
            tang = itemView.findViewById(R.id.tang);
            quantity = itemView.findViewById(R.id.quantity);
            delete = itemView.findViewById(R.id.xoa);
            check = itemView.findViewById(R.id.check);
            giam.setOnClickListener(this);
            tang.setOnClickListener(this);
            delete.setOnClickListener(this);
        }

        public void setChoiceListener(ItemChoiceListener clickListener) {
            this.clickListener = clickListener;
        }

        @Override
        public void onClick(View v) {
            if(v == tang) clickListener.setOnItemClick(v, getAdapterPosition(), 0);
            if(v==giam) clickListener.setOnItemClick(v, getAdapterPosition(), 1);
            if(v==delete) clickListener.setOnItemClick(v, getAdapterPosition(), 2);
        }
    }
}
