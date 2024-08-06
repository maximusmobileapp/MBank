package com.maximus.maximusbank.Model;

import android.net.Uri;

import java.io.Serializable;

public class Contact implements Serializable {
    private long id;
    private String name;
    private String number;
    private String photo;
    private int amount;
    private  Uri photoUri;

    public Contact(long id, String name, String number, String photo, int amount) {
        this.id = id;
        this.name = name;
        this.number = number;
        this.photo = photo;
        this.amount = amount;
    }

    public Contact(String name, Uri photoUri) {
        this.name = name;
        this.photoUri = photoUri;
    }

    public Uri getPhotoUri() {
        return photoUri;
    }

    public void setPhotoUri(Uri photoUri) {
        this.photoUri = photoUri;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
