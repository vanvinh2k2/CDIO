package com.example.shope;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shope.bannerAdapter.OrderAdapter;
import com.example.shope.model.GetOrder;
import com.example.shope.retrofit.ApiBanHang;
import com.example.shope.retrofit.RetrofitClient;
import com.example.shope.utils.Constant;
import com.example.shope.utils.ReferenceManager;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class OrderActivity extends AppCompatActivity {
    CompositeDisposable disposable = new CompositeDisposable();
    ApiBanHang apiBanHang;
    ReferenceManager manager;
    Toolbar toolbar;
    RecyclerView listOrder;
    List<GetOrder> arrOrder;
    OrderAdapter orderAdapter;
    TextView tt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        anhXa();
        getTool();
        getOrder();
    }

    private void getOrder() {
        String idUser = manager.getString("_id");
        disposable.add(apiBanHang.getOrder(idUser)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        orderModel -> {
                            arrOrder = orderModel.getData();
                            if(arrOrder.size() != 0){
                                tt.setVisibility(View.GONE);
                                listOrder.setVisibility(View.VISIBLE);
                                orderAdapter = new OrderAdapter(R.layout.item_order2, this, arrOrder);
                                listOrder.setAdapter(orderAdapter);
                            }else{
                                listOrder.setVisibility(View.GONE);
                                tt.setVisibility(View.VISIBLE);
                            }
                        },
                        throwable -> {
                            //Toast.makeText(this, throwable.getMessage()+"", Toast.LENGTH_SHORT).show();
                        }
                ));
    }

    private void anhXa() {
        manager = new ReferenceManager(OrderActivity.this);
        toolbar = findViewById(R.id.toolbar);
        listOrder = findViewById(R.id.listOrder);
        arrOrder = new ArrayList<>();
        tt = findViewById(R.id.notify);
        apiBanHang = RetrofitClient.getInstance(Constant.BASE_URL).create(ApiBanHang.class);
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
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                }
            });
        }
    }

    @Override
    protected void onDestroy() {
        disposable.clear();
        super.onDestroy();
    }
}