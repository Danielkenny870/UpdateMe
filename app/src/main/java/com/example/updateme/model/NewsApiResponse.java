package com.example.updateme.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class NewsApiResponse implements Serializable {
    String status;
    int totalResults;

    //creating the List of the Article
    ArrayList<NewsHeadlines> articles;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public List<NewsHeadlines> getArticles() {
        return articles;
    }

    public void setArticles(ArrayList<NewsHeadlines> articles) {
        this.articles = articles;
    }
}
