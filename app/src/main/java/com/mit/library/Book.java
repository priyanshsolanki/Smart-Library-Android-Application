package com.mit.library;

import com.google.gson.annotations.SerializedName;

public class Book {
    @SerializedName("qty") private int qty;
    @SerializedName("name") private String bookName;
    @SerializedName("author") private String author;
    @SerializedName("book_id") private int bookId;


    public int getQty() {
        return qty;
    }

    public String getBookName() {
        return bookName;
    }

    public String getAuthor() {
        return author;
    }
    public int getBookId() {
        return bookId;
    }
}
