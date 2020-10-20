package com.mit.library;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;



public class Adapter1 extends RecyclerView.Adapter<Adapter1.MyViewHolder> {

    private List<RecordData> books;
    private List<Student> students;
    public static  String prn_tmp;

    private Context context;
    public Adapter1(List<RecordData> books, Context context,List<Student> stud) {
        this.books = books;
        this.context = context;
        this.students=stud;

    }

    public interface RecyclerViewClickListener{
        void onClick(View v,int pos);
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(prn_tmp.contains("admin")){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item2, parent, false);
            return new MyViewHolder(view);
        }
        else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item1, parent, false);
            return new MyViewHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        if(prn_tmp.contains("admin")){
            holder.student_name.setText(students.get(position).getStudentName());
            holder.prn.setText(students.get(position).getPrn());

        }else {

            if(SharedPrefManager.getInstance(context).getUserPrn().contains("admin")&&books.get(position).getStatus().toLowerCase().equals("issued")) {
            holder.btnReturn.setVisibility(View.VISIBLE);
            holder.btnReturn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sendData(books.get(position).getRecord_no(),books.get(position).getBook_id());
                    Intent intent=new Intent(context, NavActivity.class);
                    context.startActivity(intent);
                }
            });}
            else {
                holder.btnReturn.setVisibility(View.GONE);
            }
            holder.name.setText(books.get(position).getName());
            holder.status.setText("Status :" + books.get(position).getStatus());
            holder.payment.setText("Payment Method:" + books.get(position).getPayment_method());
            holder.issueDate.setText("Issue Date:" + String.valueOf(books.get(position).getIssue_date()));
            holder.returnDate.setText("Return Date:" + String.valueOf(books.get(position).getReturn_date()));
            if (books.get(position).getStatus().equals("Returned")) {
                holder.diff.setTextColor(Color.parseColor("#00FF00"));
                holder.diff.setText("You have Returned this Book");
            } else if (Integer.parseInt(books.get(position).getDiff()) <= 0) {
                holder.diff.setText("You have " + (-1 * Integer.parseInt(books.get(position).getDiff())) + " days left to return");
            } else {
                holder.diff.setText("You have to pay penalty for " + (Integer.parseInt(books.get(position).getDiff())) + " days");
            }
        }

    }
    private  void sendData(final int record_no,final int book_id) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Constants.URL_Return,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject jsonObject=null;
                        try {
                            jsonObject = new JSONObject(response);
                            Toast.makeText(context, jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                        } catch (JSONException e) {
                            Toast.makeText(context,e.getMessage() , Toast.LENGTH_LONG).show();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("record_no", String.valueOf(record_no));
                params.put("book_id", String.valueOf(book_id));
                return params;
            }
        };


        RequestHandler.getInstance(context).addToRequestQueue(stringRequest);
    }
    @Override
    public int getItemCount() {
        if (prn_tmp.contains("admin")) {
            if (students != null)
                return students.size();
            else
                return 0;
        } else
            {
            if (books != null) {
                return books.size();
            }
            else {
                return 0;
            }
        }

    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

          TextView name,status,payment,issueDate,returnDate,diff,student_name,prn;
          Button btnReturn;
            public MyViewHolder(View itemView) {
            super(itemView);
                if(prn_tmp.contains("admin")) {

                   student_name=itemView.findViewById(R.id.student_name);
                   prn=itemView.findViewById(R.id.txtPrn);
                }
                else{
                    btnReturn=itemView.findViewById(R.id.btnReturn);
                    name = itemView.findViewById(R.id.book_name);
                    status = itemView.findViewById(R.id.status);
                    payment = itemView.findViewById(R.id.payment);
                    issueDate = itemView.findViewById(R.id.issue_date);
                    returnDate = itemView.findViewById(R.id.return_date);
                    diff = itemView.findViewById(R.id.diff);
                }
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(prn_tmp.contains("admin")) {
                Intent intent=new Intent(context,ProfileActivity.class);
                prn_tmp=students.get(getAdapterPosition()).getPrn();
                intent.putExtra("prn",students.get(getAdapterPosition()).getPrn());
                context.startActivity(intent);
            }
        }
    }
}