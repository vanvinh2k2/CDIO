package com.example.shope;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shope.bannerAdapter.CartAdapter;
import com.example.shope.event.SumEvent;
import com.example.shope.model.Cart;
import com.example.shope.retrofit.ApiBanHang;
import com.example.shope.retrofit.RetrofitClient;
import com.example.shope.utils.Constant;
import com.example.shope.utils.ReferenceManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class CartActivity extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView listCart;
    public TextView tongtien, cartNull;
    Button mua;
    public CartAdapter cartAdapter;
    List<Cart> arrcart;
    public long sumMoney = 0;
    CompositeDisposable disposable = new CompositeDisposable();
    ApiBanHang apiBanHang;
    ReferenceManager manager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        anhXa();
        getTool();
        getCart();
        SumProduct();
        thanhToan();
    }

    private void thanhToan() {
        mua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Cart> muaSp = new ArrayList<>();
                muaSp = Constant.listProduct;
                if(muaSp.size() == 0){
                    Toast.makeText(CartActivity.this, "Vui lòng chọn sản phẩm!", Toast.LENGTH_SHORT).show();
                }else{
                    //Log.e("er",new Gson().toJson(muaSp).toString());
                    Intent intent =new Intent(CartActivity.this, ThanhToanActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    public void deleteProduct(String cartItemId){
        String user1 = manager.getString("_id");
        disposable.add(apiBanHang.deleteItemCart(user1, cartItemId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        resultModel -> {
                            if(resultModel.isSuccess()){
                                Toast.makeText(this, "Đã xóa thành công", Toast.LENGTH_SHORT).show();
                            }
                        },
                        throwable -> {
                            Toast.makeText(this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                ));
        SumProduct();
    }
    public void getCart() {
        arrcart = Constant.allProduct;
        if(arrcart.size()==0){
            cartNull.setVisibility(View.VISIBLE);
        }
        else {
            cartNull.setVisibility(View.GONE);
            cartAdapter = new CartAdapter(arrcart, R.layout.item_cart, CartActivity.this);
            listCart.setAdapter(cartAdapter);
        }
    }

    void SumProduct(){
        sumMoney = 0;
        for(int i=0;i<Constant.listProduct.size();i++){
            sumMoney = sumMoney + (Constant.listProduct.get(i)
                    .getPrice() * Constant.listProduct.get(i).getQuantity());
        }
        DecimalFormat format = new DecimalFormat("###,###,###");
        tongtien.setText("Tổng thanh toán: "+format.format(sumMoney)+"Đ");
    }

    private void anhXa() {
        arrcart = new ArrayList<>();
        toolbar = findViewById(R.id.toolbar);
        listCart = findViewById(R.id.listCart);
        tongtien = findViewById(R.id.thanhtoan);
        mua = findViewById(R.id.muahang);
        cartNull = findViewById(R.id.cartNull);
        manager = new ReferenceManager(CartActivity.this);
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
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(),MainActivity.class));
        Constant.listProduct.clear();
        super.onBackPressed();
    }
    @Override
    protected void onStart() {
        EventBus.getDefault().register(this);
        super.onStart();
    }

    @Override
    protected void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Subscribe(sticky = true,threadMode = ThreadMode.MAIN)
    public void eventTinh(SumEvent event){
        if(event!=null){
            SumProduct();
        }
    }
    @Override
    protected void onDestroy() {
        disposable.clear();
        super.onDestroy();
    }
}