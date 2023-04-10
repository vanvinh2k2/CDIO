package com.example.shope;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shope.utils.Constant;

public class CategoryThanhToanActivity extends AppCompatActivity {
    Toolbar toolbar;
    TextView tructiep;
    ImageView paypal;
    Button confirm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_thanh_toan);
        anhXa();
        getToolBar();
        getChoicePay();
    }

    private void getChoicePay() {
        Drawable drawable1 = getDrawable(R.drawable.edit_pay);
        Drawable drawable2 = getDrawable(R.drawable.edt_pay2);
        if(Constant.CODE_PAYMENT == 1){
            paypal.setBackground(drawable2);
        }else{
            tructiep.setBackground(drawable2);
        }
        tructiep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paypal.setBackground(drawable1);
                tructiep.setBackground(drawable2);
                Constant.CODE_PAYMENT = 0;
            }
        });
        paypal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paypal.setBackground(drawable2);
                tructiep.setBackground(drawable1);
                Constant.CODE_PAYMENT = 1;
            }
        });
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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
    private void anhXa() {
        toolbar = findViewById(R.id.toolbar);
        tructiep = findViewById(R.id.tructiep);
        paypal = findViewById(R.id.paypal);
        confirm = findViewById(R.id.confirm);
    }
}