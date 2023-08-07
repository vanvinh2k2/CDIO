package com.example.shope;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
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
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class RegisterActivity extends AppCompatActivity /*implements FacebookCallback<LoginResult>*/ {
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

        process();
    }

    private void process() {
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
                }
            }
        });
    }

    private void registerAccount() {
        String name1 = nameedt.getText().toString().trim();
        String email1 = emailedt.getText().toString().trim();
        String password1 = passwordedt.getText().toString().trim();
        if(checkInput()){
            register(name1, email1, password1);
        }

    }

    private void register(String name1, String email1, String password1) {
        disposable.add(apiBanHang.registerUser(name1, email1, password1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        resultModel -> {
                            if(resultModel.isSuccess()){
                                text("Tạo tài khoản thành công");
                                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                            }else{
                                LoginManager.getInstance().logOut();
                                text("Tài khoản đã tồn tại!");
                            }
                        },
                        throwable -> {
                            text(throwable.getMessage());
                        }
                ));
    }

    private void anhXa() {
        login = findViewById(R.id.textlogin);
        nameedt = findViewById(R.id.name);
        emailedt = findViewById(R.id.email);
        createbtn = findViewById(R.id.create);
        passwordedt = findViewById(R.id.password);
        //regoogle = findViewById(R.id.login_google);
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