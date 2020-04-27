package com.daksh.dialogwithrecyclerview;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PropertiesData {


    @SerializedName("location")
    @Expose
    private String location;
    @SerializedName("image")
    @Expose
    private String image;

    public PropertiesData(String location, String image) {
        this.location = location;
        this.image = image;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}