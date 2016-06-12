package com.example.nut.sampleretrofit.Model;

import com.google.gson.annotations.Expose;

import org.parceler.Parcel;

/**
 * Created by nut on 12/6/2559.
 */
@Parcel
public class User {
    @Expose
    String name;
    @Expose
    String blog;
    @Expose
    String company;

    public String getName() {
        return name;
    }

    public String getBlog() {
        return blog;
    }

    public String getCompany() {
        return company;
    }
}
