package com.mit.library;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterrface2 {
    @GET("getStudent.php")
    Call<List<Student>> getStudent();
}
