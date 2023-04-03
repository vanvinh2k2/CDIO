package com.example.shope;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shope.retrofit.ApiBanHang;
import com.example.shope.retrofit.RetrofitClient;
import com.example.shope.utils.Constant;
import com.example.shope.utils.ReferenceManager;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class RegisterActivity extends AppCompatActivity {
    TextView login;
    CompositeDisposable disposable = new CompositeDisposable();
    ApiBanHang apiBanHang;
    CheckBox show;
    ImageView eyeimg;
    EditText nameedt, emailedt, passwordedt;
    Button createbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        anhXa();
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }
        });
        createbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerAccount();
            }
        });
        eyeimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (show.isChecked()) {
                    show.setChecked(false);
                    eyeimg.setImageResource(R.drawable.eye);
                    passwordedt.setInputType(InputType.TYPE_CLASS_TEXT);
                } else {
                    show.setChecked(true);
                    eyeimg.setImageResource(R.drawable.hidden);
                    passwordedt.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD | InputType.TYPE_CLASS_TEXT);
                    /*Typeface typeface = null;
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                        typeface = getResources().getFont(R.font.alatsi);
                    }
                    passwordedt.setTypeface(typeface);*/
                }
            }
        });
    }

    private void registerAccount() {
        String name1 = nameedt.getText().toString().trim();
        String email1 = emailedt.getText().toString().trim();
        String password1 = passwordedt.getText().toString().trim();
        if(checkInput()){
            disposable.add(apiBanHang.registerUser(name1, email1, password1)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            resultModel -> {
                                if(resultModel.isSuccess()){
                                    text(resultModel.getMessage());
                                    startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                                }
                            },
                            throwable -> {
                                text("Error connnect!");
                            }
                    ));
        }

    }

    private void anhXa() {
        login = findViewById(R.id.textlogin);
        nameedt = findViewById(R.id.name);
        emailedt = findViewById(R.id.email);
        createbtn = findViewById(R.id.create);
        passwordedt = findViewById(R.id.password);
        eyeimg = findViewById(R.id.eyeimg);
        show = findViewById(R.id.showPassword);
        apiBanHang = RetrofitClient.getInstance(Constant.BASE_URL).create(ApiBanHang.class);
    }

    @Override
    protected void onDestroy() {
        disposable.clear();
        super.onDestroy();
    }

    boolean checkInput(){
        if(nameedt.getText().toString().trim().isEmpty()){
            text("Vui lòng nhập tên!");
            nameedt.requestFocus();
            return false;
        }else if(emailedt.getText().toString().trim().isEmpty()){
            text("Vui lòng nhập Email!");
            emailedt.requestFocus();
            return false;
        }else if(passwordedt.getText().toString().trim().isEmpty()){
            text("Vui lòng nhập mật khẩu!");
            passwordedt.requestFocus();
            return false;
        }else return true;
    }
    void text(String v){
        Toast.makeText(this, v+"", Toast.LENGTH_SHORT).show();
    }
}