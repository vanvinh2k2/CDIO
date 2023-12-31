package com.example.shope;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.util.Base64;
import android.util.Log;
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

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class LoginActivity extends AppCompatActivity implements FacebookCallback<LoginResult>{
    TextView register, regoogle;
    EditText emailedt, passwordedt;
    Button loginbtn;
    CheckBox show;
    ImageView eyeimg;
    CompositeDisposable disposable = new CompositeDisposable();
    ApiBanHang apiBanHang;
    ReferenceManager manager;
    LoginButton loginButton;
    GoogleSignInOptions gso;
    GoogleSignInClient gsc;
    CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        anhXa();
        process();
        facebook();
        google();
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

    private void google() {
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc = GoogleSignIn.getClient(this, gso);
        regoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });
    }

    private void signIn() {
        Intent signIntent = gsc.getSignInIntent();
        startActivityForResult(signIntent, Constant.CODE_GOOGLE);
    }
    private void facebook() {
        loginButton = findViewById(R.id.login_button);
        loginButton.setLoginText("Tiếp tục với Facebook");
        loginButton.setLogoutText("Tiếp tục với Facebook");
        loginButton.setReadPermissions(Arrays.asList("email", "public_profile"));
        callbackManager = CallbackManager.Factory.create();
        loginButton.registerCallback(callbackManager, this);
    }
    private void loginAccount() {
        String email1 = emailedt.getText().toString().trim();
        String password1 = passwordedt.getText().toString().trim();
        if(checkInput()){
            checkAccount(email1, password1);
        }
    }

    private void checkAccount(String email1, String password1) {
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
                                try {
                                    manager.putString("email", user.getEmail());
                                }catch(Exception e){
                                    Toast.makeText(this, e.getMessage()+"", Toast.LENGTH_SHORT).show();
                                }
                                manager.putString("avatar", user.getAvatar());
                                manager.putString("isblock", user.getIsBlocked());
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                            }
                            else{
                                text("Email hoặc mật khẩu bị sai");
                            }
                        },
                        throwable -> {
                            text(throwable.getMessage());
                            Log.e("er",throwable.getMessage());
                        }
                ));
    }

    private void anhXa() {
        register = findViewById(R.id.registertext);
        emailedt = findViewById(R.id.email);
        passwordedt = findViewById(R.id.password);
        eyeimg = findViewById(R.id.eyeimg);
        show = findViewById(R.id.showPassword);
        loginbtn = findViewById(R.id.loginaccount);
        regoogle = findViewById(R.id.login_google);
        manager = new ReferenceManager(LoginActivity.this);
        apiBanHang = RetrofitClient.getInstance(Constant.BASE_URL).create(ApiBanHang.class);
    }

    private void registerGoogle(String name1, String email1, String id1, String avatar1) {
        disposable.add(apiBanHang.loginGoogle(avatar1, name1, email1, id1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        userModel -> {
                            if(userModel.isSuccess()){
                                Toast.makeText(this, "Bạn đã đăng nhập với Google", Toast.LENGTH_SHORT).show();
                                User user = (User) userModel.getData();
                                manager.putString("_id", user.get_id());
                                manager.putString("token", userModel.getAccessToken());
                                manager.putString("username", user.getDisplayName());
                                manager.putString("email", user.getEmail());
                                manager.putString("avatar", user.getAvatar());
                                manager.putString("isblock", user.getIsBlocked());
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                            }
                        },
                        throwable -> {
                            text(throwable.getMessage());
                        }
                ));
    }

    private void registerFacebook(String name1, String id1, String avatar1) {
        disposable.add(apiBanHang.loginFacebook(avatar1, name1, id1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        userModel -> {
                            if(userModel.isSuccess()){
                                Toast.makeText(this, "Bạn đã đăng nhập với Facebook", Toast.LENGTH_SHORT).show();
                                User user = (User) userModel.getData();
                                manager.putString("_id", user.get_id());
                                manager.putString("token", userModel.getAccessToken());
                                manager.putString("username", user.getDisplayName());
                                manager.putString("email", "Hiện chưa có");
                                manager.putString("avatar", user.getAvatar());
                                manager.putString("isblock", user.getIsBlocked());
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                            }
                        },
                        throwable -> {
                            text(throwable.getMessage());
                        }
                ));
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
    private void result() {
        GraphRequest graphRequest = GraphRequest.newMeRequest(
                AccessToken.getCurrentAccessToken(),
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        String person, avatar, id;
                        try {
                            person = object.getString("name");
                            //email1 = object.getString("email");
                            id = object.getString("id");
                            avatar = object.getJSONObject("picture").getJSONObject("data").getString("url");
                            Log.e("kq_fb","Name: "+person+"\tIdFacebook: "+id+"\t Avatar: "+avatar);
                            registerFacebook(person, id, avatar);
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        } catch (Exception ex) {
                            Log.e("kq", ex.getMessage());
                        }
                    }
                });
        Bundle parame = new Bundle();
        parame.putString("fields", "id, name, email, picture");
        graphRequest.setParameters(parame);
        graphRequest.executeAsync();
    }
    @Override
    protected void onDestroy() {
        disposable.clear();
        super.onDestroy();
    }

    @Override
    public void onSuccess(LoginResult loginResult) {
        result();
    }

    @Override
    public void onCancel() {

    }

    @Override
    public void onError(FacebookException error) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == Constant.CODE_GOOGLE){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                task.getResult(ApiException.class);
                GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
                if(acct!=null){
                    String person = acct.getDisplayName();
                    String email = acct.getEmail();
                    String id = acct.getId();
                    String photoUrl = String.valueOf(acct.getPhotoUrl());
                    Log.e("kq_google","Name: "+person+"\tEmail: "+email+"\tIdGoogle: "+id+"\t Avatar: "+photoUrl);
                    registerGoogle(person, email, id, photoUrl);
                }
            } catch (ApiException e) {
                throw new RuntimeException(e);
            }
        }else{
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }

    }
}