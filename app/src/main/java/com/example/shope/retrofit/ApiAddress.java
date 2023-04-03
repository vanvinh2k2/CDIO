package com.example.shope.retrofit;

import com.example.shope.model.DistrictModel;
import com.example.shope.model.ProvinceModel;
import com.example.shope.model.Ward;
import com.example.shope.model.WardModel;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiAddress {
    @GET("province/")
    Observable<ProvinceModel> getProvince();

    @GET("province/district/{id}")
    Observable<DistrictModel> getDistrict(
            @Path("id") String id
    );

    @GET("province/ward/{id}")
    Observable<WardModel> getWard(
            @Path("id") String id
    );
}
