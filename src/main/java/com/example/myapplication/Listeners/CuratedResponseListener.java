package com.example.myapplication.Listeners;

import com.example.myapplication.Models.CuratedApiResponse;

public interface CuratedResponseListener {

    void onFetch(CuratedApiResponse response, String message);
    void onError(String message);
}
