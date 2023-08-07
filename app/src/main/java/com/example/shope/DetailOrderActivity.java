package com.example.shope;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shope.model.GetOrder;
import com.example.shope.utils.Constant;
import com.example.shope.utils.ReferenceManager;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;

public class DetailOrderActivity extends AppCompatActivity {
    Toolbar toolbar;
    ImageView shippingimg, productimg;
    TextView shippingtxt, nameUser, phone, address, payment,
        nameProduct, nameshope, time, category, statusOrder, statusPay,
        quantity, sumPrice, priceShipping;
    TextView cancle;
    GetOrder order;
    ReferenceManager manager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_order);
        anhXa();
        getData();
        getTool();
    }

    private void getTool() {
        setSupportActionBar(toolbar);
        if(getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_back);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Constant.listProduct.clear();
                    startActivity(new Intent(getApplicationContext(), OrderActivity.class));
                }
            });
        }
    }

    private void getData() {
        Intent intent = getIntent();
        order = (GetOrder) intent.getSerializableExtra("data");
        //Toast.makeText(this, +"", Toast.LENGTH_SHORT).show();
        Picasso.get().load(manager.getString("imageDelivery")).into(shippingimg);
        Picasso.get().load(order.getProductId().getImagePreview()).into(productimg);
        shippingtxt.setText(order.getDeliveryId().getName());
        nameUser.setText("Tên: "+order.getShippingAddressId().getDisplayName());
        phone.setText("SDT: "+order.getShippingAddressId().getPhone());
        address.setText("Địa chỉ: "+order.getShippingAddressId().getExactAddress()+", "+
                order.getShippingAddressId().getWard()+", "+
                order.getShippingAddressId().getDistrict()+", "+
                order.getShippingAddressId().getProvince());
        if(order.getPaymentMethod().compareTo("COD")==0){
            payment.setText("Phương thức Thanh Toán: Thanh toán khi nhận hàng");
            statusPay.setText("Thanh toán: Chưa thanh toán");
        }else {
            statusPay.setText("Thanh toán: Đã thanh toán");
            payment.setText("Phương thức Thanh Toán: "+order.getPaymentMethod());
        }
        nameProduct.setText(order.getProductId().getName());
        nameshope.setText(order.getStoreId().getName());
        if(order.getOptionStyle().getOption1().compareTo("A")==0 || order.getOptionStyle().getOption1().compareTo("")==0){
            category.setVisibility(View.GONE);
        }else {
            category.setVisibility(View.VISIBLE);
            category.setText("Phân loại: "+order.getOptionStyle().getOption1()+" - "+order.getOptionStyle().getOption2());
        }

        statusOrder.setText("Trạng thái đơn hàng: Chờ thanh toán"/*+order.getStatus()*/);
        quantity.setText("x"+order.getQuantity());
        time.setText( order.getCreatedAt().substring(0,10));
        DecimalFormat format = new DecimalFormat("###,###,###");
        sumPrice.setText(format.format(order.getTotalPrice()+order.getShippingPrice())+"đ");
        priceShipping.setText(format.format(order.getShippingPrice())+"đ");
    }
    private void anhXa() {
        toolbar = findViewById(R.id.toolbar);
        shippingimg = findViewById(R.id.image);
        productimg = findViewById(R.id.anhSp);
        shippingtxt = findViewById(R.id.shipping);
        nameUser = findViewById(R.id.name);
        phone = findViewById(R.id.phone);
        address = findViewById(R.id.addressDetail);
        payment = findViewById(R.id.categorypayment);
        nameProduct = findViewById(R.id.tenSp);
        nameshope = findViewById(R.id.shop);
        time = findViewById(R.id.time);
        category = findViewById(R.id.category);
        statusOrder = findViewById(R.id.statusOrder);
        statusPay = findViewById(R.id.statusPay);
        quantity = findViewById(R.id.quantity);
        sumPrice = findViewById(R.id.priceSum);
        priceShipping = findViewById(R.id.priceship);
        cancle = findViewById(R.id.huy);
        manager = new ReferenceManager(DetailOrderActivity.this);
    }
}