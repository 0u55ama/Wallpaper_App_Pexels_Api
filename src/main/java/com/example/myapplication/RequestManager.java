package com.example.myapplication;

import android.content.Context;
import android.widget.Toast;

import com.example.myapplication.Listeners.CuratedResponseListener;
import com.example.myapplication.Listeners.SearchResponseListener;
import com.example.myapplication.Models.CuratedApiResponse;
import com.example.myapplication.Models.SeearchApiResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public class RequestManager {

    Context context;
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://api.pexels.com/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public RequestManager(Context context) {
        this.context = context;
    }

    public void getCuratedWallpapers(CuratedResponseListener listener, String page){

        CallWallpaperList callWallpaperList = retrofit.create(CallWallpaperList.class);
        Call<CuratedApiResponse> call = callWallpaperList.getWallpapers(page,"20");

        call.enqueue(new Callback<CuratedApiResponse>() {
            @Override
            public void onResponse(Call<CuratedApiResponse> call, Response<CuratedApiResponse> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(context,"An Error",Toast.LENGTH_SHORT).show();
                    return;
                }
                listener.onFetch(response.body(), response.message());

            }

            @Override
            public void onFailure(Call<CuratedApiResponse> call, Throwable t) {

                listener.onError(t.getMessage());

            }
        });
    }


    public void searchCuratedWallpapers(SearchResponseListener listener, String page, String query){

        CallWallpaperListSearch callWallpaperListSearch = retrofit.create(CallWallpaperListSearch.class);
        Call<SeearchApiResponse> call = callWallpaperListSearch.searchWallpapers(query,page,"20");

        call.enqueue(new Callback<SeearchApiResponse>() {
            @Override
            public void onResponse(Call<SeearchApiResponse> call, Response<SeearchApiResponse> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(context,"An Error",Toast.LENGTH_SHORT).show();
                    return;
                }
                listener.onFetch(response.body(), response.message());

            }

            @Override
            public void onFailure(Call<SeearchApiResponse> call, Throwable t) {

                listener.onError(t.getMessage());

            }
        });
    }

    private interface CallWallpaperList{

        @Headers({
                "Accept: application/json",
                "Authorization: 563492ad6f91700001000001fb339faaab994f6da5a30c970656bb52"
        })

        @GET("curated/")
        Call<CuratedApiResponse> getWallpapers(
                @Query("page") String page,
                @Query("per_page") String per_page
        );
    }

    private interface CallWallpaperListSearch{

        @Headers({
                "Accept: application/json",
                "Authorization: 563492ad6f91700001000001fb339faaab994f6da5a30c970656bb52"
        })

        @GET("search")
        Call<SeearchApiResponse> searchWallpapers(
                @Query("query") String query,
                @Query("page") String page,
                @Query("per_page") String per_page
        );
    }

}
