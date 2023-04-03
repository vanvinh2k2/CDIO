package com.example.shope;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shope.retrofit.ApiBanHang;
import com.example.shope.retrofit.RetrofitClient;
import com.example.shope.utils.Constant;
import com.example.shope.utils.ReferenceManager;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ChangePasswordActivity extends AppCompatActivity {
    Button confirm;
    Toolbar toolbar;
    ApiBanHang apiBanHang;
    CompositeDisposable disposable = new CompositeDisposable();
    EditText currentPassword, newPassword, confirmPassword;
    ReferenceManager manager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        anhXa();
        getTool();
        changePassword();
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

    private void changePassword() {
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String oldPassword1 = currentPassword.getText().toString().trim();
                String newPassword1 = newPassword.getText().toString().trim();
                String rePassword1 = confirmPassword.getText().toString().trim();
                String idUser = manager.getString("_id");
                if(checkInput()){
                    disposable.add(apiBanHang.changePassword(idUser, oldPassword1, newPassword1, rePassword1)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(
                                    resultModel -> {
                                        if(resultModel.isSuccess()){
                                            Toast.makeText(ChangePasswordActivity.this, resultModel.getMessage(), Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(ChangePasswordActivity.this, ProfileActivity.class));
                                        }
                                    },
                                    throwable -> {
                                        Toast.makeText(ChangePasswordActivity.this, "Error sever change password!", Toast.LENGTH_SHORT).show();
                                    }
                            ));
                }
            }
        });

    }

    private void anhXa() {
        confirm = findViewById(R.id.confirm);
        currentPassword = findViewById(R.id.currentpass);
        newPassword = findViewById(R.id.newpass);
        confirmPassword = findViewById(R.id.confirmpass);
        toolbar = findViewById(R.id.toolbar);
        apiBanHang = RetrofitClient.getInstance(Constant.BASE_URL).create(ApiBanHang.class);
        manager = new ReferenceManager(ChangePasswordActivity.this);
    }

    boolean checkInput(){
        if(currentPassword.getText().toString().trim().isEmpty()){
            text("Vui lòng nhập mật khẩu gần đây!");
            currentPassword.requestFocus();
            return false;
        }else if(newPassword.getText().toString().trim().isEmpty()){
            text("Vui lòng nhập mật khẩu mới!");
            newPassword.requestFocus();
            return false;
        }else if(confirmPassword.getText().toString().trim().isEmpty()){
            text("Vui lòng nhập lại mật khẩu mới!");
            confirmPassword.requestFocus();
            return false;
        }else return true;
    }

    void text(String v){
        Toast.makeText(this, v+"", Toast.LENGTH_SHORT).show();
    }

}