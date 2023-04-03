package com.example.shope;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.widget.NestedScrollView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.example.shope.bannerAdapter.BannerAdapter;
import com.example.shope.bannerAdapter.CategoryAdapter;
import com.example.shope.bannerAdapter.MenuCategoryAdapter;
import com.example.shope.bannerAdapter.ProductAdapter;
import com.example.shope.model.BannerCategory;
import com.example.shope.model.Category;
import com.example.shope.model.MenuCategory;
import com.example.shope.model.Product;
import com.example.shope.retrofit.ApiBanHang;
import com.example.shope.retrofit.RetrofitClient;
import com.example.shope.utils.Constant;
import com.example.shope.utils.ReferenceManager;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersAdapter;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersDecoration;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    protected long backPress = 0;
    ImageView image, cart, imgProfile;
    TextView nameProfile;
    DrawerLayout draw;
    NavigationView navigation;
    ReferenceManager manager;
    BannerAdapter bannerAdapter;
    CategoryAdapter categoryAdapter;
    ProductAdapter productAdapter;
    MenuCategoryAdapter menuCategoryAdapter;
    List<BannerCategory> arrBanner;
    List<Category> arrCategory;
    List<MenuCategory> arrMenu;
    CompositeDisposable disposable = new CompositeDisposable();
    ApiBanHang apiBanHang;
    List<Product> arrProduct;
    ViewFlipper flipper;
    ListView listMenu;
    NestedScrollView nestedScroll;
    RecyclerView banner, category, product, category2;
    //StickyRecyclerHeadersDecoration headersDecor = new StickyRecyclerHeadersDecoration((StickyRecyclerHeadersAdapter) categoryAdapter);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        anhXa();
        getToolBar();
        actionViewFlipper();
        process();
        getListBanner();
        getCategory();
        getProduct();
        getMenuCatagory();
        setupStickyFooter();
    }

    private void getMenuCatagory() {
        arrMenu.add(new MenuCategory("https://img.icons8.com/color/256/user.png","My acount"));
        arrMenu.add(new MenuCategory("https://img.icons8.com/color/1x/appointment-reminders--v1.png","Notification"));
        arrMenu.add(new MenuCategory("https://img.icons8.com/color/256/purchase-order.png","Purchase order"));
        arrMenu.add(new MenuCategory("https://img.icons8.com/color/256/headset.png","Help"));
        arrMenu.add(new MenuCategory("https://img.icons8.com/color/256/logout-rounded-left.png","Logout"));
        menuCategoryAdapter = new MenuCategoryAdapter(arrMenu, R.layout.item_category, MainActivity.this);
        listMenu.setAdapter(menuCategoryAdapter);

        listMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0){
                    startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                }
                if(position == 1){
                    startActivity(new Intent(getApplicationContext(), NotificationActivity.class));
                }
                if(position == 2){

                }
                if(position == 3){
                    startActivity(new Intent(getApplicationContext(), HelpActivity.class));
                }
                if(position == 4){
                    manager.clear();
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                }
            }
        });
    }

    private void setupStickyFooter() {
        // Set visibility of the sticky footer layout initially
        // Set the sticky header layout
        View stickyHeader = findViewById(R.id.listcategory2);
        final View nonStickyHeader = findViewById(R.id.listcategory);
        final int nonStickyHeaderHeight = nonStickyHeader.getHeight();

        // Set the height of the sticky header to match the non-sticky header
        ViewGroup.LayoutParams stickyHeaderParams = stickyHeader.getLayoutParams();
        stickyHeaderParams.height = nonStickyHeaderHeight;
        stickyHeader.setLayoutParams(stickyHeaderParams);

        // Update the position of the sticky header on scroll change
        nestedScroll.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY >= nonStickyHeaderHeight) {
                    stickyHeader.setVisibility(View.VISIBLE);
                } else {
                    stickyHeader.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    private void getProduct() {
        disposable.add(apiBanHang.getProduct()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        productModel -> {
                            if(productModel.isSuccess()){
                                arrProduct = productModel.getData();
                                productAdapter = new ProductAdapter(arrProduct, R.layout.item_product, MainActivity.this);
                                product.setAdapter(productAdapter);
                            }
                        },
                        throwable -> {
                            Toast.makeText(this, "Error PRO!", Toast.LENGTH_SHORT).show();
                        }
                ));
    }

    private void getCategory() {
        disposable.add(apiBanHang.getCategory()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        categoryModel -> {
                            if(categoryModel.isSuccess()){
                                arrCategory = categoryModel.getData();
                                categoryAdapter = new CategoryAdapter(arrCategory, R.layout.item_category2, MainActivity.this);
                                category.setAdapter(categoryAdapter);
                                //category2.setAdapter(categoryAdapter);
                            }
                        },
                        throwable -> {
                            Toast.makeText(this, throwable.getMessage()+"", Toast.LENGTH_SHORT).show();
                        }
                ));
    }

    private void getListBanner() {
        arrBanner.add(new BannerCategory(R.drawable.icon2,"Miễn phí vận chuyển", "Tiết kiệm đến 25̀%" ));
        arrBanner.add(new BannerCategory(R.drawable.icon5,"Ưu đãi hàng ngày", "Tiết kiệm đến 25̀%" ));
        arrBanner.add(new BannerCategory(R.drawable.icon3,"Hỗ trợ 24/24", "Mua sắm với một chuyên gia" ));
        arrBanner.add(new BannerCategory(R.drawable.icon1,"Giá cả phải chăng", "Nhận giá trực tiếp từ nhà máy" ));
        arrBanner.add(new BannerCategory(R.drawable.icon4,"Thanh toán an toàn", "Thanh toán được bảo vệ 100̀%" ));
        bannerAdapter = new BannerAdapter(arrBanner, R.layout.item_banner, MainActivity.this);
        banner.setAdapter(bannerAdapter);
    }
    private void process() {
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
            }
        });
        Picasso.get().load(manager.getString("avatar"))
                .placeholder(R.drawable.user)
                .into(image);
        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), HidenActivity.class));
            }
        });
        Picasso.get().load(manager.getString("avatar"))
                .placeholder(R.drawable.user)
                .into(imgProfile);
        nameProfile.setText(manager.getString("username"));
    }

    private void actionViewFlipper() {
        List<Integer> quangCao = new ArrayList<>();
        quangCao.add(R.drawable.gt3);
        quangCao.add(R.drawable.gt5);
        quangCao.add(R.drawable.gt1);
        quangCao.add(R.drawable.gt2);
        for(int i=0;i<quangCao.size();i++){
            ImageView anh = new ImageView(MainActivity.this);
            anh.setImageResource(quangCao.get(i));
            anh.setScaleType(ImageView.ScaleType.FIT_XY);
            flipper.addView(anh);
        }
        flipper.setFlipInterval(5000);
        flipper.setAutoStart(true);
        Animation animationIn = AnimationUtils.loadAnimation(MainActivity.this,R.anim.slide_in_right);
        Animation animationout = AnimationUtils.loadAnimation(MainActivity.this,R.anim.slide_out_right);
        flipper.setInAnimation(animationIn);
        flipper.setOutAnimation(animationout);
    }

    private void getToolBar() {
        setSupportActionBar(toolbar);
        if(getSupportActionBar()!=null){
            toolbar.setNavigationIcon(R.drawable.ic_baseline_menu);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    draw.openDrawer(GravityCompat.START);
                }
            });
        }
    }

    private void anhXa() {
        arrBanner = new ArrayList<>();
        arrCategory = new ArrayList<>();
        arrProduct = new ArrayList<>();
        arrMenu = new ArrayList<>();
        manager = new ReferenceManager(MainActivity.this);
        toolbar = findViewById(R.id.toolbar);
        image = findViewById(R.id.profile_image);
        draw = findViewById(R.id.drawer);
        cart = findViewById(R.id.cart);
        flipper = findViewById(R.id.viewflipper);
        banner = findViewById(R.id.listbanner);
        category = findViewById(R.id.listcategory);
        product = findViewById(R.id.listproduct);
        category2 = findViewById(R.id.listcategory2);
        nestedScroll = findViewById(R.id.nestedScrollView);
        listMenu = findViewById(R.id.listmenu);
        apiBanHang = RetrofitClient.getInstance(Constant.BASE_URL).create(ApiBanHang.class);
        nameProfile = findViewById(R.id.nameprofile);
        imgProfile = findViewById(R.id.imageprofile);
    }

    @Override
    public void onBackPressed() {
        if(backPress+2000>System.currentTimeMillis()){
            super.onBackPressed();
            return;
        }
        else Toast.makeText(this, "Nhấn lần nữa để thoát", Toast.LENGTH_SHORT).show();
        backPress = System.currentTimeMillis();
    }
}