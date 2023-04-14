package com.example.shope;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.shope.retrofit.ApiBanHang;
import com.example.shope.retrofit.RetrofitClient;
import com.example.shope.utils.Constant;
import com.example.shope.utils.ReferenceManager;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class OrderStatusActivity extends AppCompatActivity {
    Button home, order;
    CompositeDisposable disposable = new CompositeDisposable();
    ApiBanHang apiBanHang;
    ReferenceManager manager;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_status);
        anhXa();
        getTool();
        loadCart();
        process();
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

    private void loadCart() {
        for(int i=0;i<Constant.listProduct.size();i++){
            deleteProduct(Constant.listProduct.get(i).get_id());
        }
        Constant.listProduct.clear();
    }
    public void deleteProduct(String cartItemId){
        String user1 = manager.getString("_id");
        disposable.add(apiBanHang.deleteItemCart(user1, cartItemId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        resultModel -> {
                            if(resultModel.isSuccess()){
                                //Toast.makeText(this, "Đã xóa thành công", Toast.LENGTH_SHORT).show();
                            }
                        },
                        throwable -> {
                            Toast.makeText(this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                ));
    }

    private void process() {
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });
        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), OrderActivity.class));
            }
        });
    }

    private void anhXa() {
        home = findViewById(R.id.home);
        order = findViewById(R.id.order);
        manager = new ReferenceManager(OrderStatusActivity.this);
        toolbar = findViewById(R.id.toolbar);
        apiBanHang = RetrofitClient.getInstance(Constant.BASE_URL).create(ApiBanHang.class);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        super.onBackPressed();
    }
}