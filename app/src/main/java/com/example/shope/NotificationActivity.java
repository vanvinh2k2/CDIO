package com.example.shope;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.example.shope.bannerAdapter.NotificationAdapter;
import com.example.shope.model.Notify;

import java.util.ArrayList;
import java.util.List;

public class NotificationActivity extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView listNotify;
    NotificationAdapter adapter;
    List<Notify> arrNotify;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        anhXa();
        getToolBar();
        getNotify();
    }

    private void getNotify() {
        arrNotify.add(new Notify("hh", "San pham ko co nua","Chim Yến Nhỏ Không Biết Trân Quý Đồ Ăn - Bị Yến Mẹ Dạy Cho Bài Học Nhớ Đời…"));
        arrNotify.add(new Notify("hh", "San pham ko co nua","Chim Yến Nhỏ Không Biết Trân Quý Đồ Ăn - Bị Yến Mẹ Dạy Cho Bài Học Nhớ Đời…"));
        arrNotify.add(new Notify("hh", "San pham ko co nua","Chim Yến Nhỏ Không Biết Trân Quý Đồ Ăn - Bị Yến Mẹ Dạy Cho Bài Học Nhớ Đời…"));
        arrNotify.add(new Notify("hh", "San pham ko co nua","Chim Yến Nhỏ Không Biết Trân Quý Đồ Ăn - Bị Yến Mẹ Dạy Cho Bài Học Nhớ Đời…"));
        adapter = new NotificationAdapter(R.layout.item_notification, NotificationActivity.this, arrNotify);
        listNotify.setAdapter(adapter);
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
        arrNotify = new ArrayList<>();
        listNotify = findViewById(R.id.listNotify);
        toolbar = findViewById(R.id.toolbar);
    }
}