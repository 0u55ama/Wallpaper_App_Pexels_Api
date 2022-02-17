package com.example.myapplication.Listeners;

import com.example.myapplication.Models.SeearchApiResponse;

public interface SearchResponseListener {

    void  onFetch(SeearchApiResponse response, String message);
    void  onError(String message);
}
