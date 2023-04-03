package com.example.shope;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.shope.bannerAdapter.DistrictAdapter;
import com.example.shope.bannerAdapter.ProvinceAdapter;
import com.example.shope.bannerAdapter.WardAdapter;
import com.example.shope.model.District;
import com.example.shope.model.Province;
import com.example.shope.model.Ward;
import com.example.shope.retrofit.ApiAddress;
import com.example.shope.retrofit.ApiBanHang;
import com.example.shope.retrofit.RetrofitAddress;
import com.example.shope.retrofit.RetrofitClient;
import com.example.shope.utils.Constant;
import com.example.shope.utils.ReferenceManager;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class AddressActivity extends AppCompatActivity {
    EditText name, phone, address;
    Spinner district, province, ward;
    Button confirm;
    Toolbar toolbar;
    ApiBanHang apiBanHang;
    ApiAddress apiAddress;
    CompositeDisposable disposable = new CompositeDisposable();
    ReferenceManager manager;
    ProvinceAdapter provinceAdapter;
    DistrictAdapter districtAdapter;
    WardAdapter wardAdapter;
    public String districttxt = "", provincetxt = "", wardtxt = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);
        anhXa();
        getTool();
        getProvince();
        addAddress();
        //getWard();
    }

    public void getWard(String id) {
        if(id.compareTo("0")==0){
            List<Ward> arrlist = new ArrayList<>();
            arrlist.add(new Ward("0","0", "Chọn Xã/Phường", null));
            wardAdapter = new WardAdapter(AddressActivity.this, arrlist, R.layout.address_list);
            ward.setAdapter(wardAdapter);
        }else{
            disposable.add(apiAddress.getWard(id)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            wardModel -> {
                                List<Ward> arrlist = new ArrayList<>();
                                arrlist.add(new Ward("0","0", "Chọn Xã/Phường", null));
                                arrlist.addAll(wardModel.getResults());
                                wardAdapter = new WardAdapter(AddressActivity.this, arrlist, R.layout.address_list);
                                ward.setAdapter(wardAdapter);
                            },
                            throwable -> {
                                Toast.makeText(AddressActivity.this, throwable.getMessage()+"", Toast.LENGTH_SHORT).show();
                            }
                    ));
        }
    }

    public void getDistrict(String id) {
        if(id.compareTo("0")==0){
            List<District> arrlist = new ArrayList<>();
            arrlist.add(new District("Chọn Quận/Huyện",null, "0"));
            districtAdapter = new DistrictAdapter(AddressActivity.this, arrlist, R.layout.address_list);
            district.setAdapter(districtAdapter);
        }else{
            disposable.add(apiAddress.getDistrict(id)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            districtModel -> {
                                List<District> arrlist = new ArrayList<>();
                                arrlist.add(new District("Chọn Quận/Huyện",null, "0"));
                                arrlist.addAll(districtModel.getResults());
                                districtAdapter = new DistrictAdapter(AddressActivity.this, arrlist, R.layout.address_list);
                                district.setAdapter(districtAdapter);
                            },
                            throwable -> {
                                Toast.makeText(AddressActivity.this, throwable.getMessage()+"", Toast.LENGTH_SHORT).show();
                            }
                    ));
        }

    }

    private void getProvince() {
        disposable.add(apiAddress.getProvince()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        provinceModel -> {
                            List<Province> arrlist = new ArrayList<>();
                            arrlist.add(new Province("0","Chọn Tỉnh/Thành phố", null));
                            arrlist.addAll(provinceModel.getResults());
                            provinceAdapter = new ProvinceAdapter(AddressActivity.this,arrlist , R.layout.address_list);
                            province.setAdapter(provinceAdapter);
                        },
                        throwable -> {
                            Toast.makeText(AddressActivity.this, throwable.getMessage()+"", Toast.LENGTH_SHORT).show();
                        }
                ));
    }

    private void addAddress() {
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name1 = name.getText().toString().trim();
                String phone1 = phone.getText().toString().trim();
                String address1 = address.getText().toString().trim();
                String idUser = manager.getString("_id");
                if(checkInput()){
                    disposable.add(apiBanHang.addAddress(idUser, name1, phone1, provincetxt, districttxt, wardtxt, address1)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(
                                    resultModel -> {
                                        if(resultModel.isSuccess()){
                                            Toast.makeText(AddressActivity.this, resultModel.getMessage(), Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                                        }
                                    },
                                    throwable -> {
                                        Toast.makeText(AddressActivity.this, "Error sever!", Toast.LENGTH_SHORT).show();
                                    }
                            ));
                }
            }
        });
    }

    private void getTool() {
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

    private void anhXa() {
        name = findViewById(R.id.displayName);
        phone = findViewById(R.id.phone);
        district = findViewById(R.id.spinnerDistrict);
        province = findViewById(R.id.spinnerProvince);
        ward = findViewById(R.id.spinnerWard);
        address = findViewById(R.id.address);
        confirm = findViewById(R.id.confirm);
        toolbar = findViewById(R.id.toolbar);
        apiBanHang = RetrofitClient.getInstance(Constant.BASE_URL).create(ApiBanHang.class);
        apiAddress = RetrofitAddress.getInstance(Constant.ADDRESS_URL).create(ApiAddress.class);
        manager = new ReferenceManager(AddressActivity.this);
    }

    boolean checkInput(){
        if(name.getText().toString().trim().isEmpty()){
            text("Vui lòng nhập tên hiển thị!");
            name.requestFocus();
            return false;
        }else if(phone.getText().toString().trim().isEmpty()){
            text("Vui lòng nhập số điện thoại!");
            phone.requestFocus();
            return false;
        }else if(provincetxt.compareTo("Chọn Tỉnh/Thành phố")==0){
            text("Vui lòng chọn Tỉnh/Thành phố!");
            return false;
        }else if(districttxt.compareTo("Chọn Quận/Huyện")==0){
            text("Vui lòng chọn Quận/Huyện!");
            return false;
        }else if(wardtxt.compareTo("Chọn Xã/Phường")==0){
            text("Vui lòng chọn Xã/Phường!");
            return false;
        }else if(address.getText().toString().trim().isEmpty()){
            text("Vui lòng nhập địa chỉ cụ thể!");
            address.requestFocus();
            return false;
        }else return true;
    }

    void text(String v){
        Toast.makeText(this, v+"", Toast.LENGTH_SHORT).show();
    }
}