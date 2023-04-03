package com.example.shope;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.shope.bannerAdapter.CartAdapter;
import com.example.shope.model.Cart;
import com.example.shope.retrofit.ApiBanHang;
import com.example.shope.retrofit.RetrofitClient;
import com.example.shope.utils.Constant;
import com.example.shope.utils.ReferenceManager;

import java.util.ArrayList;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class HidenActivity extends AppCompatActivity {
    CompositeDisposable disposable = new CompositeDisposable();
    ApiBanHang apiBanHang;
    ReferenceManager manager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hiden);
        anhXa();
        getCart();
    }
    private void getCart() {
        String user1 = manager.getString("_id");
        disposable.add(apiBanHang.getCart(user1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        cartModel -> {
                            if(cartModel.isSuccess()){
                                Constant.allProduct = cartModel.getData().get(0).getCartItemIds();
                                Intent intent =new Intent(getApplicationContext(), CartActivity.class);
                                startActivity(intent);
                            }

                        },
                        throwable -> {
                            Toast.makeText(this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                ));

    }
    private void anhXa() {
        manager = new ReferenceManager(HidenActivity.this);
        apiBanHang = RetrofitClient.getInstance(Constant.BASE_URL).create(ApiBanHang.class);
    }
}