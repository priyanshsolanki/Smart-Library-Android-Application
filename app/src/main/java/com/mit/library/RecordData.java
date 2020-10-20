package com.mit.library;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class RecordData {
    @SerializedName("record_no") private int record_no;
    @SerializedName("book_id") private int book_id;
    @SerializedName("name") private String name;
    @SerializedName("status") private String status;
    @SerializedName("payment_method") private String payment_method;
    @SerializedName("issue_date") private Date issue_date;
    @SerializedName("return_date") private Date return_date;
    @SerializedName("diff") private String diff;


    public int getRecord_no() {
        return record_no;
    }

    public int getBook_id() {
        return book_id;
    }
    public String getDiff() {
        return diff;
    }

    public String getName() {
        return name;
    }

    public String getStatus() {
        return status;
    }

    public String getPayment_method() {
        return payment_method;
    }

    public Date getIssue_date() {
        return issue_date;
    }

    public Date getReturn_date() {
        return return_date;
    }
}
