package com.mit.library;

import com.google.gson.annotations.SerializedName;

public class Student {
    @SerializedName("name") private String name;
    @SerializedName("prn") private String prn;



    public String getStudentName() {
        return name;
    }

    public String getPrn() {
        return prn;
    }

}
