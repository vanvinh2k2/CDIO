package com.example.shope;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shope.bannerAdapter.ImageAdapter;
import com.example.shope.bannerAdapter.OptionAdapter;
import com.example.shope.bannerAdapter.ProductAdapter;
import com.example.shope.bannerAdapter.StyleAdapter;
import com.example.shope.model.Optioned;
import com.example.shope.model.Product;
import com.example.shope.retrofit.ApiBanHang;
import com.example.shope.retrofit.RetrofitClient;
import com.example.shope.utils.Constant;
import com.example.shope.utils.ReferenceManager;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class DetailProductActivity extends AppCompatActivity {
    public ImageView productimg;
    Toolbar toolbar;
    LinearLayout categoryMainStyle, categoryMainSize;
    RecyclerView listProductimg, optionSize, optionColor, listProductOfShope;
    TextView nameShope, nameProduct, ranktxt, toggleButton,
            evalutetxt, soldtxt, warehousetxt, pricetxt,
            tangtxt, giamtxt, quantitytxt, descriptxt;
    Button addCart;
    Product product;
    ImageAdapter imageAdapter;
    OptionAdapter adapterColor;
    ProductAdapter productAdapter;
    StyleAdapter adapterSize;
    List<String> arrsize, arrcolor;
    int kt = 0;
    public String getSize = "A", getStyle = "A", getProductId = "";
    String getStoreId = "";

    public int getQuantity = 1;
    ReferenceManager manager;
    CompositeDisposable disposable = new CompositeDisposable();
    ApiBanHang apiBanHang;
    long price = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_product);
        anhXa();
        getTool();
        getData();
        getStyle();
        addCart();
        getQuantity();
        getProductOfShop();
    }

    private void getProductOfShop() {
        disposable.add(apiBanHang.getProductOfShop(getStoreId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        productModel -> {
                            if(productModel.isSuccess()){
                                productAdapter = new ProductAdapter(productModel.getData(), R.layout.item_product, DetailProductActivity.this);
                                listProductOfShope.setAdapter(productAdapter);
                            }
                        },
                        throwable -> {
                            Toast.makeText(this, "Error PRO!", Toast.LENGTH_SHORT).show();
                        }
                ));
    }

    private void getQuantity() {
        quantitytxt.setText(getQuantity+"");
        tangtxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getQuantity ++;
                quantitytxt.setText(getQuantity+"");
            }
        });
        giamtxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getQuantity <= 1){}
                else getQuantity--;
                quantitytxt.setText(getQuantity+"");
            }
        });
    }

    private void addCart() {
        addCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Optioned optioned = new Optioned();
                optioned.setOption1(getStyle);
                optioned.setOption2(getSize);
                String quantity1 = quantitytxt.getText().toString();
                String idUser = manager.getString("_id");
                Log.e("er",idUser+", "+getProductId+", "+getStoreId+", "+ new Gson().toJson(optioned));
                disposable.add(apiBanHang.addCart(idUser, new Gson().toJson(optioned).toString(), getProductId, getStoreId, quantity1, price)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                resultModel -> {
                                    Snackbar.make(v, resultModel.getMessage(),2000)
                                            .setActionTextColor(Color.GREEN)
                                            .show();
                                },
                                throwable -> {
                                    Toast.makeText(DetailProductActivity.this, throwable.getMessage()+"", Toast.LENGTH_SHORT).show();
                                }
                        ));
            }
        });
    }

    private void getSize() {
        arrsize = new ArrayList<>();
        for(int i=0;i<product.getOptionStyles().get(0).getOptionStylesItem().size(); i++){
            arrsize.add(product.getOptionStyles().get(0).getOptionStylesItem().get(i).getName());
        }
        if(arrsize.size() == 0){
            categoryMainSize.setVisibility(View.GONE);
        }else {
            adapterSize = new StyleAdapter(R.layout.item_option, DetailProductActivity.this, arrsize);
            optionSize.setAdapter(adapterSize);
        }
    }

    private void getStyle() {
        arrcolor = new ArrayList<>();
        for(int i=0;i<product.getOptionStyles().size();i++){
            arrcolor.add(product.getOptionStyles().get(i).getName());
        }
        if(arrcolor.size() == 0){
            categoryMainStyle.setVisibility(View.GONE);
            categoryMainSize.setVisibility(View.GONE);
        }else {
            adapterColor = new OptionAdapter(R.layout.item_option, DetailProductActivity.this, arrcolor);
            optionColor.setAdapter(adapterColor);
            getSize();
        }
    }

    private void getData() {
        Intent intent = getIntent();
        List<String> imgDetailProduct;
        product = (Product) intent.getSerializableExtra("data");
        imgDetailProduct = product.getListImages();
        getStoreId = product.getStoreId().get_id();
        price = product.getPrice().get$numberDecimal();
        getProductId = product.get_id();
        Picasso.get().load(product.getImagePreview())
                .placeholder(R.drawable.dep2)
                .into(productimg);
        nameProduct.setText(product.getName());
        quantitytxt.setText("0");
        warehousetxt.setText("Tồn kho: "+product.getTotalQuantity()+"");
        soldtxt.setText(product.getSold()+" Đã bán");
        ranktxt.setText(product.getRating()+" Xếp hạng");
        descriptxt.setText(Html.fromHtml(product.getDescription()));
        DecimalFormat format = new DecimalFormat("###,###,###");
        pricetxt.setText(format.format(product.getPrice().get$numberDecimal())+"đ");
        nameShope.setText(product.getStoreId().getName());
        getDetailProduct(imgDetailProduct);
        toggleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (descriptxt.getMaxLines() == 5) {
                    // Nếu đang ở chế độ thu gọn, chuyển sang chế độ xem đầy đủ
                    descriptxt.setMaxLines(Integer.MAX_VALUE);
                    toggleButton.setText("Thu gọn");
                } else {
                    // Nếu đang ở chế độ xem đầy đủ, chuyển sang chế độ thu gọn
                    descriptxt.setMaxLines(5);
                    toggleButton.setText("Đọc thêm");
                }
            }
        });
    }

    void getDetailProduct(List<String> imgDetailProduct){
        imageAdapter = new ImageAdapter(R.layout.item_productimg,DetailProductActivity.this,imgDetailProduct);
        listProductimg.setAdapter(imageAdapter);
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
        productimg = findViewById(R.id.productimg);
        listProductimg = findViewById(R.id.listProductimg);
        listProductOfShope = findViewById(R.id.listproductother);
        nameShope = findViewById(R.id.shop);
        nameProduct = findViewById(R.id.name);
        ranktxt = findViewById(R.id.rank);
        evalutetxt = findViewById(R.id.danhgia);
        soldtxt = findViewById(R.id.sold);
        warehousetxt = findViewById(R.id.warehouse);
        pricetxt = findViewById(R.id.price);
        tangtxt = findViewById(R.id.tang);
        giamtxt = findViewById(R.id.giam);
        quantitytxt = findViewById(R.id.quantity);
        descriptxt = findViewById(R.id.descrip);
        addCart = findViewById(R.id.addcart);
        toolbar = findViewById(R.id.toolbar);
        optionColor = findViewById(R.id.listColor);
        optionSize = findViewById(R.id.listSize);
        categoryMainStyle = findViewById(R.id.categorymain1);
        categoryMainSize = findViewById(R.id.categorymain2);
        toggleButton = findViewById(R.id.toggleButton);
        manager = new ReferenceManager(DetailProductActivity.this);
        apiBanHang = RetrofitClient.getInstance(Constant.BASE_URL).create(ApiBanHang.class);
    }
}