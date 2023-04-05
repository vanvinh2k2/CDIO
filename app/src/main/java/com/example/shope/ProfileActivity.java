package com.example.shope;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shope.bannerAdapter.AddressAdapter;
import com.example.shope.model.Address;
import com.example.shope.retrofit.ApiBanHang;
import com.example.shope.retrofit.RetrofitClient;
import com.example.shope.utils.Constant;
import com.example.shope.utils.ReferenceManager;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ProfileActivity extends AppCompatActivity {
    ImageView profileimg;
    TextView nametxt, emailtxt, changePass, address, addAdress, phonetxt, addressNull;
    ReferenceManager manager;
    Toolbar toolbar;
    RecyclerView listAddress;
    List<Address> arrAddress;
    AddressAdapter adapter;
    ApiBanHang apiBanHang;
    CompositeDisposable disposable = new CompositeDisposable();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        anhXa();
        getToolBar();
        loadProfile();
        process();
        getListAddress();
    }

    public void deleteAddress(String idAddress){
        String idUser = manager.getString("_id");
        disposable.add(apiBanHang.deleteAddress(idUser, idAddress)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        resultModel -> {
                            if(resultModel.isSuccess()){
                                Toast.makeText(this, resultModel.getMessage()+"", Toast.LENGTH_SHORT).show();
                                getListAddress();
                            }
                        },
                        throwable -> {
                            getListAddress();
                        }
                ));
    }
    private void getListAddress() {
        String idUser = manager.getString("_id");
        disposable.add(apiBanHang.getListAddress(idUser)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        addressModel -> {
                            if(addressModel.isSuccess()){
                                arrAddress = addressModel.getData();
                                phonetxt.setText("Phone: "+arrAddress.get(0).getPhone());
                                adapter = new AddressAdapter(R.layout.item_address, ProfileActivity.this, arrAddress);
                                listAddress.setAdapter(adapter);
                            }
                        },
                        throwable -> {
                            addressNull.setVisibility(View.VISIBLE);
                            listAddress.setVisibility(View.GONE);
                        }
                ));
    }

    private void process() {
        changePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ChangePasswordActivity.class));
            }
        });
        addAdress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), AddressActivity.class));
            }
        });
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

    private void loadProfile() {
        Picasso.get()
                .load(manager.getString("avatar"))
                .placeholder(R.drawable.user)
                .into(profileimg);
        nametxt.setText(manager.getString("username"));
        emailtxt.setText("Email: "+manager.getString("email"));
    }

    private void anhXa() {
        manager = new ReferenceManager(ProfileActivity.this);
        profileimg = findViewById(R.id.profile_image);
        nametxt = findViewById(R.id.name);
        emailtxt = findViewById(R.id.email);
        changePass = findViewById(R.id.changePassword);
        address = findViewById(R.id.address1);
        toolbar = findViewById(R.id.toolbar);
        addAdress = findViewById(R.id.addAdress);
        arrAddress = new ArrayList<>();
        listAddress = findViewById(R.id.listAddress);
        phonetxt = findViewById(R.id.phone);
        addressNull = findViewById(R.id.addressnull);
        apiBanHang = RetrofitClient.getInstance(Constant.BASE_URL).create(ApiBanHang.class);
    }

    @Override
    protected void onDestroy() {
        disposable.clear();
        super.onDestroy();
    }
}