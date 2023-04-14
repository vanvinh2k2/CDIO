package com.example.shope;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.shope.bannerAdapter.ProductAdapter;
import com.example.shope.model.Product;
import com.example.shope.retrofit.ApiBanHang;
import com.example.shope.retrofit.RetrofitClient;
import com.example.shope.utils.Constant;
import com.example.shope.utils.ReferenceManager;
import com.nex3z.notificationbadge.NotificationBadge;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class CategoryProductActivity extends AppCompatActivity {
    Toolbar toolbar;
    ApiBanHang apiBanHang;
    List<Product> arrProduct;
    ProductAdapter productAdapter;
    RecyclerView listProduct;
    NotificationBadge tb;
    CompositeDisposable disposable = new CompositeDisposable();
    ReferenceManager manager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_product);
        anhXa();
        getTool();
        getProduct();
    }

    private void getProduct() {
        disposable.add(apiBanHang.getProduct()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        productModel -> {
                            if(productModel.isSuccess()){
                                arrProduct = productModel.getData();
                                productAdapter = new ProductAdapter(arrProduct, R.layout.item_product, CategoryProductActivity.this);
                                listProduct.setAdapter(productAdapter);
                            }
                        },
                        throwable -> {
                            Toast.makeText(this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                ));
    }
    private void getTool() {
        setSupportActionBar(toolbar);
        if(getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_back);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                }
            });
        }
    }
    private void anhXa() {
        arrProduct = new ArrayList<>();
        manager = new ReferenceManager(CategoryProductActivity.this);
        toolbar = findViewById(R.id.toolbar);
        listProduct = findViewById(R.id.listproduct);
        apiBanHang = RetrofitClient.getInstance(Constant.BASE_URL).create(ApiBanHang.class);
        tb = findViewById(R.id.soluong);
    }
    @Override
    protected void onResume() {
        disposable.add(apiBanHang.getCart(manager.getString("_id"))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        cartModel -> {
                            if(cartModel.isSuccess()){
                                Constant.allProduct = cartModel.getData();
                                if(Constant.allProduct.size()!=0)
                                    tb.setText(String.valueOf(Constant.allProduct.size()));
                            }

                        },
                        throwable -> {
                            Toast.makeText(this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                ));
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        disposable.clear();
        super.onDestroy();
    }
}