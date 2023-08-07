package com.example.shope;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.shope.bannerAdapter.AddressAdapter;
import com.example.shope.bannerAdapter.AddressDefautAdapter;
import com.example.shope.model.Address;
import com.example.shope.retrofit.ApiBanHang;
import com.example.shope.retrofit.RetrofitClient;
import com.example.shope.utils.Constant;
import com.example.shope.utils.ReferenceManager;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class DefautAddressActivity extends AppCompatActivity {
    AddressDefautAdapter adapter;
    RecyclerView listAddress;
    ApiBanHang apiBanHang;
    Toolbar toolbar;
    Button confirm;
    ReferenceManager manager;
    public String idAddress = "";
    CompositeDisposable disposable = new CompositeDisposable();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_defaut_address);
        anhXa();
        getToolBar();
        getAddress();
    }

    private void getAddress() {
        adapter = new AddressDefautAdapter(R.layout.item_address_defaut, DefautAddressActivity.this, Constant.listAddress);
        listAddress.setAdapter(adapter);
        String idUser = manager.getString("_id");
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(idAddress.compareTo("") == 0){
                    Toast.makeText(DefautAddressActivity.this, "Vui lòng chọn địa chỉ mặc định", Toast.LENGTH_SHORT).show();
                }else{
                    disposable.add(apiBanHang.setDefaultAddress(idUser, idAddress, "")
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(
                                    resultModel -> {
                                        if(resultModel.isSuccess()){
                                            Toast.makeText(DefautAddressActivity.this, "Thành công", Toast.LENGTH_SHORT).show();
                                            finish();
                                        }
                                    },
                                    throwable -> {
                                        Toast.makeText(DefautAddressActivity.this, throwable.getMessage()+"", Toast.LENGTH_SHORT).show();
                                    }
                            ));
                }
            }
        });

    }

    private void anhXa() {
        toolbar = findViewById(R.id.toolbar);
        listAddress = findViewById(R.id.listAddress);
        confirm = findViewById(R.id.xacnhan);
        manager = new ReferenceManager(DefautAddressActivity.this);
        apiBanHang = RetrofitClient.getInstance(Constant.BASE_URL).create(ApiBanHang.class);
    }

    private void getToolBar() {
        setSupportActionBar(toolbar);
        if(getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_back);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
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