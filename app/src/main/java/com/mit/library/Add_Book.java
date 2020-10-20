package com.mit.library;

import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Add_Book extends Fragment {

    private AddBookViewModel mViewModel;
    private EditText edtBookName,edtBookQty,edtBookAuthor;
    private Button btnAddBook;
    public static Add_Book newInstance() {
        return new Add_Book();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.add__book_fragment, container, false);
        edtBookAuthor=root.findViewById(R.id.edtAuthorName);
        edtBookName=root.findViewById(R.id.edtBookName);
        edtBookQty=root.findViewById(R.id.edtBookQty);

        btnAddBook=root.findViewById(R.id.btnAddBook);

        btnAddBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edtBookAuthor.getText().toString().equals("")||edtBookName.getText().toString().equals("")||edtBookQty.getText().toString().equals("")){
                    Toast.makeText(getContext(),"Enter All Details!",Toast.LENGTH_SHORT).show();
                }
                else{
                    sendData(edtBookName.getText().toString(),edtBookAuthor.getText().toString(),edtBookQty.getText().toString());
                }
            }
        });

        return root;
    }
    private  void sendData(final String bookName, final String bookAuthor, final String bookQty) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Constants.URL_AddBook,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject jsonObject=null;
                        try {
                            jsonObject = new JSONObject(response);
                            Toast.makeText(getContext(), jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                            Intent intent=new Intent(getContext(), NavActivity.class);
                            startActivity(intent);
                            getActivity().finish();
                        } catch (JSONException e) {
                            Toast.makeText(getContext(),e.getMessage() , Toast.LENGTH_LONG).show();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("name",bookName);
                params.put("author", bookAuthor);
                params.put("qty", bookQty);
                return params;
            }
        };


        RequestHandler.getInstance(getContext()).addToRequestQueue(stringRequest);
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(AddBookViewModel.class);
        // TODO: Use the ViewModel
    }

}
