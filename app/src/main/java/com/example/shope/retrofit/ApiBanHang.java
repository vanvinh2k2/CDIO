package com.example.shope.retrofit;

import com.example.shope.model.AddressDefautlModel;
import com.example.shope.model.AddressModel;
import com.example.shope.model.CartModel;
import com.example.shope.model.CategoryModel;
import com.example.shope.model.DeliveryModel;
import com.example.shope.model.DistrictModel;
import com.example.shope.model.OrderModel;
import com.example.shope.model.ProductModel;
import com.example.shope.model.ProvinceModel;
import com.example.shope.model.ResultModel;
import com.example.shope.model.UserModel;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiBanHang {
    @POST("auth/register")
    @FormUrlEncoded
    Observable<ResultModel> registerUser(
            @Field("username") String username,
            @Field("email") String email,
            @Field("password") String password
    );

    @POST("auth/login")
    @FormUrlEncoded
    Observable<UserModel> loginUser(
            @Field("email") String email,
            @Field("password") String password
    );

    @GET("category/list-categories")
    Observable<CategoryModel> getCategory();

    @PUT("user/replace-password/{user}/")
    @FormUrlEncoded
    Observable<ResultModel> changePassword(
            @Path("user") String user,
            @Field("currentPassword") String oldPassword,
            @Field("newPassword") String newPassword,
            @Field("confirmPassword") String rePassword
    );

    @POST("address/add-address/{user}/")
    @FormUrlEncoded
    Observable<ResultModel> addAddress(
            @Path("user") String user,
            @Field("displayName") String display,
            @Field("phone") String phone,
            @Field("province") String province,
            @Field("district") String district,
            @Field("ward") String ward,
            @Field("exactAddress") String address
    );

    @GET("address/list-addreeses/{user}/")
    Observable<AddressModel> getListAddress(
            @Path("user") String user
    );

    @DELETE("address/remove-address/{user}/{address}/")
    Observable<ResultModel> deleteAddress(
            @Path("user") String user,
            @Path("address") String address
    );
    @PUT("address/update-address/{user}/{address}/")
    @FormUrlEncoded
    Observable<ResultModel> updateAddress(
            @Path("user") String user,
            @Path("address") String address,
            @Field("displayName") String display,
            @Field("phone") String phone,
            @Field("province") String province,
            @Field("district") String district,
            @Field("ward") String ward,
            @Field("exactAddress") String addressex
    );

    @GET("product/list-products/by-user")
    Observable<ProductModel> getProduct();

    @GET("cart/list-carts/{user}/")
    Observable<CartModel> getCart(
            @Path("user") String user
    );

    @POST("cart/add-cart/{user}/")
    @FormUrlEncoded
    Observable<ResultModel> addCart(
            @Path("user") String user,
            @Field("optionStyle") String optionStyle,
            @Field("productId") String productId,
            @Field("storeId") String storeId,
            @Field("quantity") String quantity,
            @Field("price") long price
    );

    @DELETE("cart/remove-cart/{user}/{itemcart}/")
    Observable<ResultModel> deleteItemCart(
            @Path("user") String user,
            @Path("itemcart") String itemCart
    );

    @GET("product/list-other-products/by-user/{idStore}/")
    Observable<ProductModel> getProductOfShop(
            @Path("idStore") String idStore
    );

    @GET("product/list-hot-products")
    Observable<ProductModel> getProductHot();

    @PUT("address/set-default/{user}/{idAddress}/")
    @FormUrlEncoded
    Observable<ResultModel> setDefaultAddress(
            @Path("user") String user,
            @Path("idAddress") String address,
            @Field("null") String x
    );

    @GET("address/address-default/{user}/")
    Observable<AddressDefautlModel> getDefaultAddress(
            @Path("user") String user
    );

    @GET("delivery/list-deliveries")
    Observable<DeliveryModel> getDelivery();

    @POST("order/create-order/{user}/")
    @FormUrlEncoded
    Observable<ResultModel> addOrder(
            @Path("user") String user,
            @Field("carts") String carts
    );

    @GET("order/get-orders-status/by-user/{user}/")
    Observable<OrderModel> getOrder(
            @Path("user") String user
    );

    @POST("auth/social-login")
    @FormUrlEncoded
    Observable<UserModel> loginGoogle(
            @Field("avatar") String avatar,
            @Field("displayName") String displayName,
            @Field("email") String email,
            @Field("googleId") String googleId
    );

    @POST("auth/social-login")
    @FormUrlEncoded
    Observable<UserModel> loginFacebook(
            @Field("avatar") String avatar,
            @Field("displayName") String displayName,
            @Field("googleId") String googleId
    );
}
