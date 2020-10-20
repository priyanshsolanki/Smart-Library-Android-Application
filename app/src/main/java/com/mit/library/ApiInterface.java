package com.mit.library;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface ApiInterface {

    @GET("getBook.php")
    Call<List<Book>> getBook(@Query("key") String keyword );
}