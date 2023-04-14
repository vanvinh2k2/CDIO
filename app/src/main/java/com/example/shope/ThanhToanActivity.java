package com.example.shope;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shope.bannerAdapter.ProductOrderAdapter;
import com.example.shope.model.Address;
import com.example.shope.model.Delivery;
import com.example.shope.model.SetOrder;
import com.example.shope.retrofit.ApiBanHang;
import com.example.shope.retrofit.RetrofitClient;
import com.example.shope.utils.Constant;
import com.example.shope.utils.ReferenceManager;
import com.facebook.login.LoginManager;
import com.google.gson.Gson;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ThanhToanActivity extends AppCompatActivity {
    TextView addressTitle, name, phone, addressDetail,
            address, thanhToanTitle, cachThanhToan,
            priceProduct, priceShip, priceSum, priceThanhToan;
    View view;
    RecyclerView listProduct;
    Button confirm;
    ApiBanHang apiBanHang;
    Toolbar toolbar;
    ProductOrderAdapter productOrderAdapter;
    ReferenceManager manager;
    CompositeDisposable disposable = new CompositeDisposable();
    static final int CODE = 123;
    long pricePay = 0;
    Delivery delivery;
    public static PayPalConfiguration payPalConfiguration;
    List<SetOrder> orderList;
    String addressId;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thanh_toan);
        anhXa();
        process();
        getToolBar();
        getlistProductOrder();
        getPrice();
    }

    private void getDelivery() {
        disposable.add(apiBanHang.getDelivery()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        deliveryModel -> {
                            delivery = deliveryModel.getData().get(0);
                        },
                        throwable -> {
                            Log.e("er","Error delivery!");
                        }
                ));
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

    private void getPrice() {
        long price = 0;
        for(int i=0;i<Constant.listProduct.size();i++){
            price = price + (Constant.listProduct.get(i)
                    .getPrice() * Constant.listProduct.get(i).getQuantity());
        }
        DecimalFormat format = new DecimalFormat("###,###,###");
        priceProduct.setText(format.format(price)+"đ");
        priceShip.setText(format.format(25000*Constant.listProduct.size())+"đ");
        priceSum.setText(format.format(price+(25000*Constant.listProduct.size()))+"đ");
        pricePay = price+(25000*Constant.listProduct.size());
        priceThanhToan.setText("Tổng thanh toán: "+format.format(pricePay)+"đ");
    }

    private void getlistProductOrder() {
        productOrderAdapter = new ProductOrderAdapter(R.layout.item_order, ThanhToanActivity.this, Constant.listProduct);
        listProduct.setAdapter(productOrderAdapter);
    }

    private void getDefautAddress() {
        String idUser = manager.getString("_id");
        disposable.add(apiBanHang.getDefaultAddress(idUser)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        addressDefautlModel -> {
                            if(addressDefautlModel.isSuccess()){
                                Address address1 = addressDefautlModel.getData();
                                addressId = address1.get_id();
                                phone.setVisibility(View.VISIBLE);
                                addressDetail.setVisibility(View.VISIBLE);
                                address.setVisibility(View.VISIBLE);
                                view.setVisibility(View.VISIBLE);
                                name.setText(address1.getDisplayName());
                                phone.setText(address1.getPhone());
                                addressDetail.setText(address1.getExactAddress());
                                address.setText(address1.getWard()+", "+address1.getDistrict()+", "+address1.getProvince());
                            }
                            else{
                                name.setText("Chưa có địa chỉ giao hàng");
                                phone.setVisibility(View.GONE);
                                addressDetail.setVisibility(View.GONE);
                                address.setVisibility(View.GONE);
                                view.setVisibility(View.GONE);
                            }
                        },
                        throwable -> {
                            Toast.makeText(ThanhToanActivity.this, throwable.getMessage()+"", Toast.LENGTH_SHORT).show();
                        }
                ));
    }

    private void process() {
        payPalConfiguration = new PayPalConfiguration().environment(PayPalConfiguration.ENVIRONMENT_NO_NETWORK)
                .clientId(Constant.YOUR_CLIENT_ID);

        Intent intent = new Intent(this, PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, payPalConfiguration);
        startService(intent);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog = new ProgressDialog(ThanhToanActivity.this);
                progressDialog.setMessage("Đang tải, vui lòng chờ đợi...");
                progressDialog.show();
                if(Constant.CODE_PAYMENT == 0){
                    for(int i=0; i<Constant.listProduct.size(); i++){
                        SetOrder setOrder = new SetOrder(Constant.listProduct.get(i).getStoreId().get_id(),
                                delivery.get_id(), addressId,"COD",
                                Constant.listProduct.get(i).getProductId().get_id(),
                                25000,
                                Constant.listProduct.get(i).getPrice()*Constant.listProduct.get(i).getQuantity(),
                                Constant.listProduct.get(i).getQuantity(),
                                Constant.listProduct.get(i).getOptionStyle());
                        orderList.add(setOrder);
                    }
                    getOrder(new Gson().toJson(orderList));
                    Intent intent = new Intent(getApplicationContext(), OrderStatusActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                } else {
                    getPayPal();
                }
            }
        });
        addressTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ThanhToanActivity.this, ProfileActivity.class));
            }
        });
        thanhToanTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ThanhToanActivity.this, CategoryThanhToanActivity.class));
            }
        });
    }

    private void getPayPal(){
        PayPalPayment payment = new PayPalPayment(new BigDecimal(String.valueOf(Math.round(pricePay/22945))), "USD","Tổng thanh toán", PayPalPayment.PAYMENT_INTENT_SALE);
        Intent intent = new Intent(this, PaymentActivity.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION,payPalConfiguration);
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payment);
        startActivityForResult(intent,CODE);
    }

    private void getOrder(String carts){
        String idUser = manager.getString("_id");
        disposable.add(apiBanHang.addOrder(idUser, carts)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        resultModel -> {
                            Toast.makeText(this, resultModel.getMessage()+"", Toast.LENGTH_SHORT).show();
                        },
                        throwable -> {
                            Log.e("er","Error order!");
                        }
                ));
    }

    private void anhXa() {
        addressTitle = findViewById(R.id.addresstitle);
        name = findViewById(R.id.name);
        phone = findViewById(R.id.phone);
        toolbar = findViewById(R.id.toolbar);
        addressDetail = findViewById(R.id.addressDetail);
        address = findViewById(R.id.address);
        thanhToanTitle = findViewById(R.id.thanhtoantitle);
        cachThanhToan = findViewById(R.id.cachtra);
        priceProduct = findViewById(R.id.priceOrder);
        priceShip = findViewById(R.id.priceship);
        priceSum = findViewById(R.id.priceSum);
        priceThanhToan = findViewById(R.id.thanhtoan);
        listProduct = findViewById(R.id.listProduct);
        confirm = findViewById(R.id.dathang);
        view = findViewById(R.id.view);
        orderList = new ArrayList<>();
        apiBanHang = RetrofitClient.getInstance(Constant.BASE_URL).create(ApiBanHang.class);
        manager = new ReferenceManager(ThanhToanActivity.this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==CODE && resultCode==RESULT_OK){
            PaymentConfirmation paymentConfirmation = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
            if(paymentConfirmation != null){
                try {
                    String pamentdetail = paymentConfirmation.toJSONObject().toString();
                    JSONObject jsonObject = new JSONObject(pamentdetail);
                    //Toast.makeText(this, jsonObject.toString()+"", Toast.LENGTH_SHORT).show();
                    for(int i=0; i<Constant.listProduct.size(); i++){
                        SetOrder setOrder = new SetOrder(Constant.listProduct.get(i).getStoreId().get_id(),
                                delivery.get_id(), addressId,"PayPal",
                                Constant.listProduct.get(i).getProductId().get_id(),
                                25000,
                                Constant.listProduct.get(i).getPrice() * Constant.listProduct.get(i).getQuantity(),
                                Constant.listProduct.get(i).getQuantity(),
                                Constant.listProduct.get(i).getOptionStyle());
                        orderList.add(setOrder);
                    }
                    //Toast.makeText(this, Constant.listProduct.size()+" * "+ new Gson().toJson(orderList), Toast.LENGTH_SHORT).show();
                    getOrder(new Gson().toJson(orderList));
                    Intent intent = new Intent(getApplicationContext(), OrderStatusActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                } catch (JSONException e) {
                    Toast.makeText(this, e.getLocalizedMessage()+"", Toast.LENGTH_SHORT).show();
                }
            } else if (requestCode== Activity.RESULT_CANCELED) {
                Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();

            }
        } else if (requestCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
            Toast.makeText(this, "Invalid!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onResume() {
        getDefautAddress();
        getDelivery();
        if(Constant.CODE_PAYMENT == 1){
            cachThanhToan.setText("Thanh toán bằng PayPal");
        }else{
            cachThanhToan.setText("Thanh toán khi nhận hàng");
        }
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        stopService(new Intent(this, PayPalService.class));
        disposable.clear();
        super.onDestroy();
    }
}