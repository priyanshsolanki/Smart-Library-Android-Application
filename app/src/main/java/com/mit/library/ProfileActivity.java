package com.mit.library;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;

import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.List;

public class ProfileActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private List<RecordData> books;
    private Adapter1 adapter;
    private List<Student> students;
    private ApiInterface1 apiInterface1;
    ProgressBar progressBar;
    private String prn;
    private  Adapter.RecyclerViewClickListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Intent i=getIntent();
        prn=i.getStringExtra("prn");
        Adapter1.prn_tmp=prn;
        progressBar = findViewById(R.id.prograss);
        recyclerView = findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        fetchBooks( prn);
    }
    public void fetchBooks(String key){

        apiInterface1 = ApiClient.getApiClient().create(ApiInterface1.class);
        Call<List<RecordData>> call = apiInterface1.getRecord(key);
        call.enqueue(new Callback<List<RecordData>>() {
            @Override
            public void onResponse(Call<List<RecordData>> call, Response<List<RecordData>> response) {
                progressBar.setVisibility(View.GONE);
                books = response.body();
                adapter = new Adapter1(books, ProfileActivity.this, students);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<RecordData>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(ProfileActivity.this, "Error\n" + t.toString(), Toast.LENGTH_LONG).show();
            }
        });

    }
}
