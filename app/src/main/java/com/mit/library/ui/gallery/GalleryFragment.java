package com.mit.library.ui.gallery;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.mit.library.Adapter;
import com.mit.library.ApiClient;
import com.mit.library.ApiInterface;
import com.mit.library.Book;
import com.mit.library.IssueActivity;
import com.mit.library.ProfileActivity;
import com.mit.library.R;
import com.mit.library.SharedPrefManager;

import java.util.List;

public class GalleryFragment extends Fragment {

    private GalleryViewModel galleryViewModel;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private List<Book> books;
    private Adapter adapter;
    private ApiInterface apiInterface;
    private ProgressBar progressBar;

    private  Adapter.RecyclerViewClickListener listener;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                ViewModelProviders.of(this).get(GalleryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);

        progressBar = root.findViewById(R.id.prograss);
        recyclerView = root.findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        fetchBooks( "");

        setHasOptionsMenu(true);

        return root;


    }
    public void fetchBooks(String key){

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        Call<List<Book>> call = apiInterface.getBook(key);
        call.enqueue(new Callback<List<Book>>() {
            @Override
            public void onResponse(Call<List<Book>> call, Response<List<Book>> response) {
                progressBar.setVisibility(View.GONE);
                books = response.body();
                setOnClickListener();
                adapter = new Adapter(books, getContext(),listener);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Book>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getContext(), "Error\n"+t.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }
    private void setOnClickListener() {
        listener=new Adapter.RecyclerViewClickListener() {
            @Override
            public void onClick(View v, int pos) {
                int qty=books.get(pos).getQty();

                if(qty>0||SharedPrefManager.getInstance(getContext()).getUserPrn().contains("admin"))
                {
                    Intent intent=new Intent(getContext(), IssueActivity.class);
                    intent.putExtra("book_name",books.get(pos).getBookName());
                    intent.putExtra("book_id",String.valueOf(books.get(pos).getBookId()));
                    intent.putExtra("prn", SharedPrefManager.getInstance(getContext()).getUserPrn());
                    startActivity(intent);
                    getActivity().finish();
                }
                else
                    Toast.makeText(getContext(),"You Cannot Issue !\nBook Unavailable ",Toast.LENGTH_SHORT).show();


                    }
        };
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.nav1, menu);
        super.onCreateOptionsMenu(menu,inflater);
        SearchManager searchManager=(SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView=(SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        searchView.setIconified(false);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                fetchBooks(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

}
