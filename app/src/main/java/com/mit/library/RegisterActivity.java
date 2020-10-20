package com.mit.library;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    private EditText edtPassword,edtName,edtPrn;
    private Button btnReg;
  //  String message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        edtName=findViewById(R.id.edtName);
        edtPassword=findViewById(R.id.edtPass);
        edtPrn=findViewById(R.id.edtPrn);
        btnReg=findViewById(R.id.regBtn);
        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edtName.getText().toString().equals("")||edtPassword.getText().toString().equals("")||edtPrn.getText().toString().equals(""))
                    Toast.makeText(RegisterActivity.this, "Required fields are missing", Toast.LENGTH_LONG).show();
                else
                    sendData();
            }
        });
    }
       private  void sendData() {
           final String name = edtName.getText().toString().trim();
           final String prn = edtPrn.getText().toString().trim();
           final String password = edtPassword.getText().toString().trim();


           StringRequest stringRequest = new StringRequest(Request.Method.POST,
                   Constants.URL_REGISTER,
                   new Response.Listener<String>() {
                       @Override
                       public void onResponse(String response) {
                           JSONObject jsonObject=null;
                           try {
                               jsonObject = new JSONObject(response);
                               Toast.makeText(RegisterActivity.this, jsonObject.getString("message"), Toast.LENGTH_LONG).show();
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
                   params.put("name", name);
                   params.put("prn", prn);
                   params.put("password", password);
                   return params;
               }
           };


           RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }
}
