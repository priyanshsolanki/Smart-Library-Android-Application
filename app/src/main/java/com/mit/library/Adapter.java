package com.mit.library;

import android.content.Context;
import android.text.style.AlignmentSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;


public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> {

    private List<Book> books;
    private Context context;
    private RecyclerViewClickListener listener;

    public Adapter(List<Book> books, Context context,RecyclerViewClickListener listener) {
        this.books = books;
        this.context = context;
        this.listener=listener;
    }

    public interface RecyclerViewClickListener{
        void onClick(View v,int pos);
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.name.setText(books.get(position).getBookName());
        holder.author.setText("Author :"+books.get(position).getAuthor());
        holder.qty.setText("Quantity :"+String.valueOf(books.get(position).getQty()));
    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView name,author,qty;

        public MyViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.book_name);
            author = itemView.findViewById(R.id.author);
            qty=itemView.findViewById(R.id.qty);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(listener!=null)
            listener.onClick(v,getAdapterPosition());
        }
    }
}