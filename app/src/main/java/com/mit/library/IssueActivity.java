package com.mit.library;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.mit.library.ui.gallery.GalleryFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class IssueActivity extends AppCompatActivity {
   private Button btnIssue,btnUpdateQty;
   private EditText edtUpdateQty;
    private ProgressBar progressBar;
    private TextView txtBookName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issue);
        Intent x=getIntent();
        final String book_id=x.getStringExtra("book_id");
        final String prn=x.getStringExtra("prn");
        final String name=x.getStringExtra("book_name");
        btnUpdateQty = findViewById(R.id.btnUpdateQty);
        edtUpdateQty = findViewById(R.id.edtQty);
        btnIssue = findViewById(R.id.btnIssue);
        txtBookName=findViewById(R.id.txtBookName);
        progressBar = findViewById(R.id.prograss);

        txtBookName.setText(name);
        if(prn.contains("admin")) {
            btnIssue.setVisibility(View.GONE);
            btnUpdateQty.setVisibility(View.VISIBLE);
            edtUpdateQty.setVisibility(View.VISIBLE);
            btnUpdateQty.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String tmp=edtUpdateQty.getText().toString();
                    if(!tmp.equals(""))
                        updateQty(book_id,edtUpdateQty.getText().toString());
                    else
                        Toast.makeText(IssueActivity.this,"Enter Quantity",Toast.LENGTH_SHORT).show();

                }
            });
        }
        else {
            edtUpdateQty.setVisibility(View.GONE);
            btnUpdateQty.setVisibility(View.GONE);
            btnIssue.setVisibility(View.VISIBLE);

            btnIssue.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    progressBar.setVisibility(View.VISIBLE);
                    issueBook(book_id, prn);
                }
            });
        }
    }
    private  void issueBook(final String book_id, final String prn) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Constants.URL_Issue,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject jsonObject=null;
                        try {
                            progressBar.setVisibility(View.GONE);
                            jsonObject = new JSONObject(response);
                            Toast.makeText(IssueActivity.this, jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                            Intent intent=new Intent(IssueActivity.this, NavActivity.class);
                            startActivity(intent);
                            finish();
                        } catch (JSONException e) {
                            Toast.makeText(getApplicationContext(),e.getMessage() , Toast.LENGTH_LONG).show();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("book_id", book_id);
                params.put("prn", prn);
                return params;
            }
        };


        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }

    private  void updateQty(final String book_id,final String qty) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Constants.URL_UpdateQty,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject jsonObject=null;
                        try {
                            progressBar.setVisibility(View.GONE);
                            jsonObject = new JSONObject(response);
                            Toast.makeText(IssueActivity.this, jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                            Intent intent=new Intent(IssueActivity.this, NavActivity.class);
                            startActivity(intent);
                            finish();
                        } catch (JSONException e) {
                            Toast.makeText(getApplicationContext(),e.getMessage() , Toast.LENGTH_LONG).show();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("book_id", book_id);
                params.put("qty", qty);
                return params;
            }
        };


        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }
}
