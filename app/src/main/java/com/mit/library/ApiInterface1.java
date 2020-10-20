package com.mit.library;

import com.mit.library.Book;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface1 {
        @GET("getRecord.php")
        Call<List<RecordData>> getRecord(@Query("key") String keyword );
    }

