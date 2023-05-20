package com.example.updateme;

import com.example.updateme.model.NewsHeadlines;

import java.util.List;

public interface OnFetchDataListener<NewsApiResponds>{
    void onFetchData(List<NewsHeadlines> list, String message);
    void onError(String message);
}
