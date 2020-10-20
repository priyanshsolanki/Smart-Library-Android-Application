package com.mit.library.ui.slideshow;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.mit.library.Adapter;
import com.mit.library.Adapter1;
import com.mit.library.ApiClient;
import com.mit.library.ApiInterface;
import com.mit.library.ApiInterface1;
import com.mit.library.ApiInterrface2;
import com.mit.library.Book;
import com.mit.library.ProfileActivity;
import com.mit.library.R;
import com.mit.library.RecordData;
import com.mit.library.SharedPrefManager;
import com.mit.library.Student;

import java.util.List;

public class SlideshowFragment extends Fragment {

    private SlideshowViewModel slideshowViewModel;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private List<RecordData> books;
    private List<Student> students;

    private Adapter1 adapter;

    private ApiInterrface2 apiInterface2;
    private ApiInterface1 apiInterface1;
    ProgressBar progressBar;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        slideshowViewModel =
                ViewModelProviders.of(this).get(SlideshowViewModel.class);
        View root = inflater.inflate(R.layout.fragment_slideshow, container, false);
        progressBar = root.findViewById(R.id.prograss);
        recyclerView = root.findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        Adapter1.prn_tmp=SharedPrefManager.getInstance(getContext()).getUserPrn();
        fetchBooks(Adapter1.prn_tmp);
        return root;
    }

    public void fetchBooks(String key) {
        if (key.contains("admin")) {
            apiInterface2 = ApiClient.getApiClient().create(ApiInterrface2.class);

            Call<List<Student>> call = apiInterface2.getStudent();
            call.enqueue(new Callback<List<Student>>() {
                @Override
                public void onResponse(Call<List<Student>> call, Response<List<Student>> response) {
                    progressBar.setVisibility(View.GONE);
                    students = response.body();
                    adapter = new Adapter1(books, getContext(),students);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    }
                @Override
                public void onFailure(Call<List<Student>> call, Throwable t) {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(getContext(), "Error\n" + t.toString(), Toast.LENGTH_LONG).show();
                }
            });
        }
        else {
            apiInterface1 = ApiClient.getApiClient().create(ApiInterface1.class);
            Call<List<RecordData>> call = apiInterface1.getRecord(key);
            call.enqueue(new Callback<List<RecordData>>() {
                @Override
                public void onResponse(Call<List<RecordData>> call, Response<List<RecordData>> response) {
                    progressBar.setVisibility(View.GONE);
                    books = response.body();
                    adapter = new Adapter1(books, getContext(),students);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }

                @Override
                public void onFailure(Call<List<RecordData>> call, Throwable t) {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(getContext(), "Error\n" + t.toString(), Toast.LENGTH_LONG).show();
                }
            });
        }
    }
}
