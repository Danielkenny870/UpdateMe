package com.example.updateme;

import android.content.Context;
import android.widget.Toast;

import com.example.updateme.model.NewsApiResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class RequestManager {
    Context context;

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://newsapi.org/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public void getNewsHeadlines(OnFetchDataListener listener, String category, String query){
        CallNewsApi callNewsApi = retrofit.create(CallNewsApi.class);
        Call<NewsApiResponse> call = callNewsApi.callHeadlines(
                "ng",
                category,
                query,
                context.getString(R.string.API_key));

        //Calling the Api from the block
        try{
            call.enqueue(new Callback<NewsApiResponse>() {
                @Override
                public void onResponse(Call<NewsApiResponse> call, Response<NewsApiResponse> response) {

                    if (!response.isSuccessful()){
                        Toast.makeText(context, "Error", Toast.LENGTH_LONG).show();
                    }

                    //calling the Listener that we got from the Parameter also getting news
                    //Article from the Api response and parsing it to the listener
                    listener.onFetchData(response.body().getArticles(), response.message());
                }



                @Override
                public void onFailure(Call<NewsApiResponse> call, Throwable t) {

                    //in this onFailure method we are Parsing only the Message
                    listener.onError("Request Failed");
                }
            });
        }
        //including the Catch methods with Enqueue
        catch (Exception e){

        }

    }

    public RequestManager(Context context) {
        this.context = context;
    }
        //Managing the API calls
    public interface CallNewsApi {

        @GET("top-headlines")
        Call<NewsApiResponse> callHeadlines(

                //creating a query for the Headlines and parsing the exact Value according to the ApI
                @Query("country") String country,
                @Query("category") String category,
                @Query("q") String query,
                @Query("apiKey") String api_key
        );
        }

















}
