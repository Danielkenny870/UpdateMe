package com.example.updateme;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.updateme.model.NewsApiResponse;
import com.example.updateme.model.NewsHeadlines;

import java.util.List;

public class MainActivity extends AppCompatActivity implements SelectListener, View.OnClickListener{

    //category Search
    private SearchView searchView;
    private Button b1, b2, b3, b4, b5, b6, b7;
    private CustomAdapter adapter;
    RecyclerView recyclerView;

    //For the Progress Bar
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Instantiating the Category widgets
        b1 = findViewById(R .id.b1);   b1.setOnClickListener(this);
        b2 = findViewById(R.id.b2);    b2.setOnClickListener(this);
        b3 = findViewById(R.id.b3);    b3.setOnClickListener(this);
        b4 = findViewById(R.id.b4);    b4.setOnClickListener(this);
        b5 = findViewById(R.id.b5);    b5.setOnClickListener(this);
        b6 = findViewById(R.id.b6);    b6.setOnClickListener(this);
        b7 = findViewById(R.id.b7);    b7.setOnClickListener(this);

        //Search widget
        searchView = findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                dialog.setTitle("Fetching News Articles of " + query);
                dialog.show();
                RequestManager manager = new RequestManager(MainActivity.this);
                manager.getNewsHeadlines(listener, "general", null);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });


        //Progress Dialog
        dialog = new ProgressDialog(this);
        dialog.setTitle("Fetching news Article...");
        dialog.show();

        RequestManager manager = new RequestManager(this);
        manager.getNewsHeadlines(listener, "general", null);   //parsing the object of OnFetchDataListener
    }

    private final OnFetchDataListener<NewsApiResponse> listener = new OnFetchDataListener<NewsApiResponse>() {
        @Override
        public void onFetchData(List<NewsHeadlines> list, String message) {

            if(list.isEmpty()){
                Toast.makeText(MainActivity.this, "No Data Found!!!", Toast.LENGTH_SHORT).show();
            }else{
                showNews(list);

                //progress Dialog
                dialog.dismiss();
            }

        }

        @Override
        public void onError(String message) {
             //If the Api returns an Error
        Toast.makeText(MainActivity.this, "An Error Occurred!!!", Toast.LENGTH_SHORT).show();
        }
    };

    private void showNews(List<NewsHeadlines> list) {
        recyclerView = findViewById(R.id.recycler_main);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        adapter =new CustomAdapter(this, list, this);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void OnNewsClicked(NewsHeadlines headlines) {
        startActivity(new Intent(MainActivity.this, DetailsActivity.class)
                .putExtra("data", headlines));

    }

    @Override
    public void onClick(View v) {
        Button button = (Button) v;
        String category = button.getText().toString();
        dialog.setTitle("Fetching news article of " + category);
        dialog.show();
        RequestManager manager = new RequestManager(this);
        manager.getNewsHeadlines(listener, "general", null);

    }
}