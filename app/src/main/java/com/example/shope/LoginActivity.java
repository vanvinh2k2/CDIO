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

import com.example.shope.model.User;
import com.example.shope.retrofit.ApiBanHang;
import com.example.shope.retrofit.RetrofitClient;
import com.example.shope.utils.Constant;
import com.example.shope.utils.ReferenceManager;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class LoginActivity extends AppCompatActivity {
    TextView register;
    EditText emailedt, passwordedt;
    Button loginbtn;
    CheckBox show;
    ImageView eyeimg;
    CompositeDisposable disposable = new CompositeDisposable();
    ApiBanHang apiBanHang;
    ReferenceManager manager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        anhXa();
        process();
    }

    private void process() {
        if(manager.getString("username")!=null){
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
        eyeimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (show.isChecked()) {
                    show.setChecked(false);
                    eyeimg.setImageResource(R.drawable.eye);
                    passwordedt.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                } else {
                    show.setChecked(true);
                    eyeimg.setImageResource(R.drawable.hidden);
                    passwordedt.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
            }
        });
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginAccount();
            }
        });
    }

    private void loginAccount() {
        String email1 = emailedt.getText().toString().trim();
        String password1 = passwordedt.getText().toString().trim();
        if(checkInput()){
            disposable.add(apiBanHang.loginUser(email1, password1)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            userModel -> {
                                if(userModel.isSuccess()){
                                    User user = (User) userModel.getData();
                                    manager.putString("_id", user.get_id());
                                    manager.putString("token", userModel.getAccessToken());
                                    manager.putString("username", user.getUsername());
                                    manager.putString("email", user.getEmail());
                                    manager.putString("avatar", user.getAvatar());
                                    manager.putString("isblock", user.getIsBlocked());
                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                }
                                else{
                                    text("This user is not find!");
                                }
                            },
                            throwable -> {
                                text("This user is not find!");
                            }
                    ));
        }
    }

    private void anhXa() {
        register = findViewById(R.id.registertext);
        emailedt = findViewById(R.id.email);
        passwordedt = findViewById(R.id.password);
        eyeimg = findViewById(R.id.eyeimg);
        show = findViewById(R.id.showPassword);
        loginbtn = findViewById(R.id.loginaccount);
        manager = new ReferenceManager(LoginActivity.this);
        apiBanHang = RetrofitClient.getInstance(Constant.BASE_URL).create(ApiBanHang.class);
    }

    @Override
    protected void onDestroy() {
        disposable.clear();
        super.onDestroy();
    }

    boolean checkInput(){
        if(emailedt.getText().toString().trim().isEmpty()){
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